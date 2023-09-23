package io.github.jwdeveloper.spigot.tiktok.profiles.ast;

import io.github.jwdeveloper.spigot.tiktok.profiles.ast.statements.blocks.IfBlockStatement;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.EvaluatorContext;
import lombok.Getter;

import java.util.List;

public class ProgramSYML extends Node {
    @Getter
    private final List<Node> body;

    public ProgramSYML(List<Node> body) {
        this.body = body;
    }

    @Override
    public Object execute(EvaluatorContext context) {
        for (var bodyNode : body) {
            var result = bodyNode.execute(context);

            if (bodyNode instanceof IfBlockStatement && result instanceof Boolean boolResult) {
                if (!boolResult)
                {
                    context.setReturnValue(false);
                    break;
                }
            }
            context.setReturnValue(result);
        }

        return context.getReturnValue();
    }
}
