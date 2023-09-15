package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.ExpressionBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.SingleExpressionBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.if_blocks.IfBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.if_blocks.IfBodyBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.InterpreterBase;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals.LiteralInterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class IfInterpreter extends InterpreterBase {
    private final LiteralInterpreter literalInterpreter;
    private final BlockInterpreter blockInterpreter;
    private static  Pattern equalityPattern;
    private static  Pattern logicPattern;
    private static  Pattern allOperationsPattern;


    public IfInterpreter(Tokenizer tokenizer, BlockInterpreter blockInterpreter, LiteralInterpreter literalInterpreter) {
        super(tokenizer);
        this.literalInterpreter = literalInterpreter;
        this.blockInterpreter = blockInterpreter;
        allOperationsPattern = Pattern.compile("(not|!=|is|==|>|=>|<|<=|and|or|&&)");
        equalityPattern = Pattern.compile("(not|!=|is|==|>|=>|<|<=)");
        logicPattern = Pattern.compile("(and|or|&&)");
    }

    public ExpressionBlock getCodeBlock() {
        tokenizer.nextOrThrow("if");
        var expression = ExpressionFinder
                .create(tokenizer)
                .then(this::getBinaryExpression)
                .find();

        if (!tokenizer.isLookup("then")) {
            return expression;
        }
        tokenizer.next();
        var thenBlock = blockInterpreter.getCodeBlock("else");
        if (!tokenizer.isLookup("else")) {
            return new IfBodyBlock(expression, thenBlock);
        }
        tokenizer.next();
        var elseBlock = blockInterpreter.getCodeBlock();

        return new IfBodyBlock(expression, thenBlock, elseBlock);
    }

    public ExpressionBlock getBinaryExpression() {
        var leftLiteral = literalInterpreter.getCodeBlock();
        if (!tokenizer.hasNext()) {
            return leftLiteral;
        }

        var operation = tokenizer.next();

        if(operation.equals("then"))
        {
            return new SingleExpressionBlock(leftLiteral);
        }

        if (!allOperationsPattern.matcher(operation).find()) {
            throw new ProfileEngineException("If invalid if operation: " + operation);
        }
        var rightLiteral = literalInterpreter.getCodeBlock();
        return new IfBlock(leftLiteral, operation, rightLiteral);
    }


    public static class ExpressionFinder {
        private final Tokenizer tokenIterator;
        private final List<OperationType> tokenTypes;
        private Supplier<ExpressionBlock> then;

        public ExpressionFinder(Tokenizer tokenIterator) {
            this.tokenIterator = tokenIterator;
            tokenTypes = new ArrayList<>();
        }

        public static ExpressionFinder create(Tokenizer tokenizer) {
            return new ExpressionFinder(tokenizer);
        }

        public ExpressionFinder thenByType(OperationType type) {
            tokenTypes.add(type);
            return this;
        }

        public ExpressionFinder then(Supplier<ExpressionBlock> then) {
            this.then = then;
            return this;
        }

        private ExpressionBlock getGenericExpression(OperationType tokenType, Supplier<ExpressionBlock> next) {
            var left = next.get();
            while (toOperationType(tokenIterator.lookup()) == tokenType) {
                var binaryOperator = tokenIterator.next();
                var right = next.get();

                left = new IfBlock(left, binaryOperator, right);
            }
            return left;
        }

        public ExpressionBlock find() {
            Supplier<ExpressionBlock> f3 = () -> getGenericExpression(OperationType.LOGICAL_OPERATOR, then);
            Supplier<ExpressionBlock> f4 = () -> getGenericExpression(OperationType.EQUALITY_OPREATOR, f3);
            return f4.get();
        }


        private OperationType toOperationType(String value) {

            if (equalityPattern.matcher(value).find()) {
                return OperationType.EQUALITY_OPREATOR;
            }
            if (logicPattern.matcher(value).find()) {
                return OperationType.LOGICAL_OPERATOR;
            }
            return OperationType.UNKNOWN;
        }

        public enum OperationType {
            UNKNOWN,
            EQUALITY_OPREATOR,
            LOGICAL_OPERATOR
        }
    }

}
