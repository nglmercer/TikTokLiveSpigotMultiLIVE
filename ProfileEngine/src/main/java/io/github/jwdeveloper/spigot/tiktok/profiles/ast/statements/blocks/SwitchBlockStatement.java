package io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.Expression;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.expressions.literals.Literal;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.Statement;
import lombok.Getter;
import lombok.Value;

import java.util.List;


@Getter
public class SwitchBlockStatement extends Statement {
    Expression condition;
    List<SwitchCase> cases;
    SwitchCase caseDefault;

    public SwitchBlockStatement(Expression condition, List<SwitchCase> cases) {
        this.cases = cases;
        this.condition = condition;
    }

    public SwitchBlockStatement(Expression condition, List<SwitchCase> cases, SwitchCase caseDefault) {
        this.condition = condition;
        this.cases = cases;
        this.caseDefault = caseDefault;
    }

    @Override
    public Object execute(EvaluatorContext context)
    {
        var inputValue = condition.execute(context);
        context.setVariable("value",inputValue);
        for (var currentCase : cases)
        {
            var caseKey = currentCase.getCondition().execute(context);
            if(caseKey instanceof Boolean boolCase)
            {
                if(boolCase)
                {
                    context.removeVariable("value");
                    return currentCase.getAction().execute(context);
                }
            }
            if (!caseKey.equals(inputValue)) {
                continue;
            }
            context.removeVariable("value");
            return currentCase.getAction().execute(context);
        }

        if (caseDefault != null)
        {
            context.removeVariable("value");
            return caseDefault.getAction().execute(context);
        }

        return StringUtils.EMPTY;
    }

    @Value
    public static final class SwitchCase {
        Expression condition;

        Statement action;
    }

}
