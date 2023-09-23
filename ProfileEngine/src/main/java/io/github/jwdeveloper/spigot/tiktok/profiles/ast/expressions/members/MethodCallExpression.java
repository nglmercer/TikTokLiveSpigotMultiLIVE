package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.members;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import lombok.Value;

import java.util.Arrays;

@Value
public class MethodCallExpression extends Expression {
    public Expression methodName;

    public ParametersStatement parametersStatement;

    @Override
    public Object execute(EvaluatorContext context) {
        var identifierLiteral = (IdentifierLiteral) methodName;
        var method = context.getMethod(identifierLiteral.getName());
        var parameters = parametersStatement.execute(context);
        var result = method.getOnInvoke().apply(parameters);
        return result;
    }


    public Object callJavaMethod(EvaluatorContext context, Object instance) {
        var identifierLiteral = (IdentifierLiteral) methodName;
        var optional = Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(e -> e.getName().equals(identifierLiteral.getValue()))
                .findFirst();
        if (optional.isEmpty()) {
            throw new SymlEngineException("Method of name " + identifierLiteral.getName() + " not found");
        }
        var parameters = parametersStatement.execute(context).toArray();
        try {
            var method = optional.get();
            method.setAccessible(true);
            return method.invoke(instance, parameters);
        } catch (Exception e) {
            throw new SymlEngineException("Method invoke error "+ instance.getClass().getSimpleName() + " " + optional.get().getName(),e);
        }
    }
}
