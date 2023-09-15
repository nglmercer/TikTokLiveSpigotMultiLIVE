package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals;

import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;

import java.util.Arrays;

public class JavaFieldBlock extends LiteralBlock {
    protected String clazz;
    protected String member;

    public JavaFieldBlock(String fullPath, String clazz, String member, Class<?> type) {
        super(fullPath, type);
        this.clazz = clazz;
        this.member = member;
    }

    @Override
    public Object execute(ExecutorContext context) {
        var clazzType = getClassType();
        var optional = Arrays.stream(clazzType.getDeclaredFields()).filter(e -> e.getName().equals(member)).findFirst();

        if (optional.isEmpty()) {
            throw new ProfileEngineException("Java Field of name "+getContent()+"." + member + " not found!");
        }

        var field = optional.get();

        try
        {
            return field.get(null);
        } catch (Exception e) {
            return "NULL";
        }
    }

    protected Class<?> getClassType() {
        try {
            return  Class.forName(this.getContent());
        } catch (Exception e) {
            throw new ProfileEngineException("Java Class of name " + this.getContent() + " not found!");
        }
    }
}
