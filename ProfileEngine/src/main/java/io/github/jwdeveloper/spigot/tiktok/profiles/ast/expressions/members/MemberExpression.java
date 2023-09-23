package io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.members;


import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.IdentifierLiteral;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;
import lombok.Value;

import java.util.StringTokenizer;

@Value
public class MemberExpression extends Expression {
    boolean computed;

    Expression object;

    Expression property;

    @Override
    public Object execute(EvaluatorContext context)
    {
        Object root = null;
        if(object instanceof MemberExpression memberExpression)
        {
            root = memberExpression.execute(context);
        }
        if(object instanceof IdentifierLiteral identifierLiteral)
        {
           root = context.getVariable(identifierLiteral.getName());
        }


        if(property instanceof MethodCallExpression methodCallExpression)
        {
            return methodCallExpression.callJavaMethod(context, root);
        }

        if(property instanceof IdentifierLiteral identifierLiteral)
        {
            return getFieldValueByPath(root, identifierLiteral.getName(), false);
        }

       throw new SymlEngineException("Unsupported chain operation");
    }

    private Object getFieldValueByPath(Object object, String path, boolean skipFirst) {
        var tokenizer = new StringTokenizer(path, ".");
        var currentObject = object;

        var isSkipped = false;
        while (tokenizer.hasMoreTokens()) {
            if (skipFirst && !isSkipped) {
                tokenizer.nextToken();
                isSkipped = true;
                continue;
            }

            try {
                var currentField = tokenizer.nextToken();
                var field = currentObject.getClass().getDeclaredField(currentField);
                field.setAccessible(true);
                currentObject = field.get(currentObject);

                if (currentObject == null) {
                    return "NULL";
                }
            } catch (Exception e) {
                return "NULL";
            }
        }

        if(currentObject instanceof Number)
        {
            var stringValue = currentObject.toString();
            return Integer.parseInt(stringValue);
        }

        return currentObject;
    }
}
