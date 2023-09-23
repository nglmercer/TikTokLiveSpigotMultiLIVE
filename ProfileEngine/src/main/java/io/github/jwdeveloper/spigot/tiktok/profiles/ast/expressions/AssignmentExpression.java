package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.ArrayDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.SymlArray;
import io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.Token;

public class AssignmentExpression extends BinaryExpression {
    public AssignmentExpression(Expression left, Token operation, Expression right) {
        super(left, operation, right);
    }

    @Override
    public Object execute(EvaluatorContext context) {
        var leftIdentifier = (IdentifierLiteral) left;
        var value = right.execute(context);

        if (value instanceof MethodDefinition functionDeclaration) {
            return context.addMethod(functionDeclaration);
        }
        if (value instanceof ArrayDefinition arrayDefinition) {
            var array = new SymlArray(arrayDefinition.getInitArguments());
            return context.addVariable(leftIdentifier.getName(), array);
        }

        return context.setVariable(leftIdentifier.getName(), value);
    }
}
