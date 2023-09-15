package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.ExpressionBlock;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import lombok.Data;

import java.util.StringTokenizer;

@Data
public class LiteralBlock extends ExpressionBlock {
    private final String content;
    private final Class<?> clazz;
    private Object value;

    public LiteralBlock(String content, Class<?> type) {
        this.content = content;
        this.clazz = type;
    }

    @Override
    public Object execute(ExecutorContext context) {
        if (clazz.equals(TikTokEvent.class)) {
            return getFieldValueByPath(context.getTikTokEvent(), content, true);
        }

        if (value != null) {
            return value;
        }

        if (clazz.equals(Boolean.class)) {
            value = Boolean.parseBoolean(content);
        }
        if (clazz.equals(Number.class)) {
            value = Integer.parseInt(content);
        }
        if (clazz.equals(String.class)) {
            value = content.replaceAll("'", "").replaceAll("\"", "");
        }
        if(clazz.equals(Object.class))
        {
            var consts = context.getConstants();
            if(consts.containsKey(content))
            {
                value = consts.get(content).getValue();
            }
            else
            {
                value = "[CONST NOT FOUND]";
            }
        }

        return value;
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
