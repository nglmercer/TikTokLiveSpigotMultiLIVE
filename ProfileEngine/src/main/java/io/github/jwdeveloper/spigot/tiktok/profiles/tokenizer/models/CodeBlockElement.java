package io.github.jwdeveloper.spigot.tiktok.profiles.tokenizer.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CodeBlockElement {
    private String blockName;
    private List<String> lines;

    public CodeBlockElement() {
        lines = new ArrayList<>();
    }

    public CodeBlockElement(String blockName) {
        this.blockName = blockName;
        lines = new ArrayList<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }
}
