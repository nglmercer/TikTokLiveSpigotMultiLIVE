package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.definitions;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.ParametersStatement;
import lombok.Value;

import java.util.List;

@Value
public class MethodDefinitionStatement extends Expression {
    Expression methodName;

    ParametersStatement parameters;

    ProgramSYML body;

    @Override
    public MethodDefinition execute(EvaluatorContext context) {
        var literal = (IdentifierLiteral)methodName;
        var names = parameters.getParametersNames(context);

        return new MethodDefinition(literal.getName(), e -> onFunctionInvoked(e, names, context), false);
    }

    private Object onFunctionInvoked(List<Object> inputParams, List<String> names, EvaluatorContext context) {

        for (var i = 0; i < names.size(); i++) {
            var name = names.get(i);
            var value = inputParams.get(i);
            context.setVariable(name, value);
        }

        var result = body.execute(context);

        for (var i = 0; i < names.size(); i++) {
            var name = names.get(i);
            context.removeVariable(name);
        }
        return result;
    }
}
