package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.switch_blocks;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.ExpressionBlock;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class SwitchBlock extends CodeBlock {
    private final ExpressionBlock input;
    private final List<SwitchCase> cases;
    private final Optional<SwitchCase> defaultCase;

    public SwitchBlock(ExpressionBlock input, List<SwitchCase> cases) {
        this.input = input;
        this.cases = cases;
        defaultCase = cases.stream().filter(e -> e.isDefault()).findFirst();
    }

    @Override
    public Object execute(ExecutorContext context) {
        var inputValue = input.execute(context);

        for (var currentCase : cases) {
            if (currentCase.isDefault()) {
                continue;
            }

            var caseKey = currentCase.getKey().execute(context);
            if (!caseKey.equals(inputValue)) {
                continue;
            }
            return currentCase.getBlock().execute(context);
        }

        if (defaultCase.isPresent()) {
            return defaultCase.get().getBlock().execute(context);
        }

        return "SWITCH CASE NOT FOUND FOR: " + inputValue;
    }
}
