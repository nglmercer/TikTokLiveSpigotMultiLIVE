package io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.code.literals;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.JavaFieldBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.JavaMethodBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals.LiteralBlock;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.InterpreterBase;
import io.github.jwdeveloper.spigot.tiktok.profiles.interpreter.Tokenizer;

import java.util.ArrayList;

public class JavaLiteralInterpeter extends InterpreterBase {
    private final LiteralInterpreter literalInterpreter;

    public JavaLiteralInterpeter(Tokenizer tokenizer, LiteralInterpreter literalInterpreter) {
        super(tokenizer);
        this.literalInterpreter = literalInterpreter;
    }



    public LiteralBlock getCodeBlock() {
        var value = tokenizer.current();

        var split = value.split("\\.");


        var member = split[split.length-1];
        var clazz = split[split.length-2];
        var namespace =value.replace("."+member,StringUtils.EMPTY);
        namespace = namespace.replace("java.",StringUtils.EMPTY);

        if (!tokenizer.next().equals("(")) {
            return new JavaFieldBlock(namespace,clazz,member, JavaFieldBlock.class);
        }
        return getJavaMethod(namespace, clazz, member);
    }

    public LiteralBlock getJavaMethod(String namespace, String clazz, String member) {
        var parameters = new ArrayList<LiteralBlock>();
        do {
            var parameter = literalInterpreter.getCodeBlock();
            parameters.add(parameter);
            if (tokenizer.isLookup(")")) {
                tokenizer.next();
                break;
            }
            tokenizer.next();
        }
        while (tokenizer.isCurrent(","));
        return new JavaMethodBlock(namespace, clazz, member, JavaFieldBlock.class, parameters);
    }
}
