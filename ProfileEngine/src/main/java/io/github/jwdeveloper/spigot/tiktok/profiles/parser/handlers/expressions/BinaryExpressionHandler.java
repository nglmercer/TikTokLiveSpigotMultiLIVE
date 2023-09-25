package io.github.jwdeveloper.spigot.tiktok.profiles.parser.handlers.expressions;

import io.github.jwdeveloper.spigot.tiktok.profiles.parser.NodeFactory;
import io.github.jwdeveloper.spigot.tiktok.profiles.parser.ParserHandler;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.BinaryExpression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.TokenType;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class BinaryExpressionHandler implements ParserHandler<Expression>
{
    @Override
    public Expression handle(Tokenizer tokenizer, NodeFactory parserFactory, Object... parameters)
    {
        var chainFinder = new ExpressionChain(tokenizer);
        chainFinder.then(()->  parserFactory.createNode(Expression.class, UnaryExpressionHandler.class));
        return chainFinder.find();
    }

    public static class ExpressionChain {
        private final Tokenizer tokenIterator;
        private final List<TokenType> tokenTypes;
        private Supplier<Expression> then;

        public ExpressionChain(Tokenizer tokenIterator) {
            this.tokenIterator = tokenIterator;
            tokenTypes = new ArrayList<>();
        }

        public static BinaryExpressionHandler.ExpressionChain create(Tokenizer tokenizer) {
            return new BinaryExpressionHandler.ExpressionChain(tokenizer);
        }

        public BinaryExpressionHandler.ExpressionChain thenByType(TokenType type) {
            tokenTypes.add(type);
            return this;
        }

        public BinaryExpressionHandler.ExpressionChain then(Supplier<Expression> then) {
            this.then = then;
            return this;
        }

        private Expression getGenericExpression(TokenType tokenType, Supplier<Expression> next) {
            var left = next.get();
            while (tokenIterator.lookupType(tokenType)) {
                var binaryOperator = tokenIterator.next();
                var right = next.get();

                left = new BinaryExpression(left, binaryOperator, right);
            }
            return left;
        }

        public Expression find() {
            Supplier<Expression> f1 = () -> getGenericExpression(TokenType.BINARY_ADDATIVE_OPERATOR, then);
            Supplier<Expression> f2 = () -> getGenericExpression(TokenType.BINARY_MULTIPLICATION_OPERATOR, f1);
            Supplier<Expression> f3 = () -> getGenericExpression(TokenType.LOGICAL_OPERATOR, f2);
            Supplier<Expression> f4 = () -> getGenericExpression(TokenType.EQUALITY_OPERATOR, f3);
            return f4.get();
        }

    }
}
