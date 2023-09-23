package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.keywords;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import lombok.Value;

@Value
public class DeleteStatement extends KeywordStatement {

    Expression expression;

    @Override
    public Object execute(EvaluatorContext context)
    {
        var identifier = (IdentifierLiteral) expression;
        context.removeVariable(identifier.getName());
        return identifier.getName();
    }
}
