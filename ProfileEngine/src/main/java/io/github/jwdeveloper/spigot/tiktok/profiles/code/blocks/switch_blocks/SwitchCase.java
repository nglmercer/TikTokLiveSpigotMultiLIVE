package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.switch_blocks;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.CodeBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.LiteralBlock;
import lombok.Value;

@Value
public class SwitchCase {
    boolean isDefault;
    LiteralBlock key;
    CodeBlock block;
}
