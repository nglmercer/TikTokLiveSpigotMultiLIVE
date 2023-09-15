package io.github.jwdeveloper.spigot.tiktok.profiles.code.blocks.literals;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.code.ExecutorContext;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.ProfileEngineException;

import java.util.Arrays;
import java.util.List;

public class JavaMethodBlock extends JavaFieldBlock {
    private final List<LiteralBlock> parameters;

    public JavaMethodBlock(String fullPath,String clazz, String member, Class<?> type, List<LiteralBlock> parameters)
    {
        super(fullPath,clazz, member, type);
        this.parameters = parameters;
    }

    @Override
    public Object execute(ExecutorContext context)
    {
        var clazzType = getClassType();
        var optional = Arrays.stream(clazzType.getDeclaredMethods()).filter(e -> e.getName().equals(member)).findFirst();

        if (optional.isEmpty()) {
            throw new ProfileEngineException("Java Method of name "+getContent()+"." + member + " not found!");
        }

        var method = optional.get();
        var parametersValues = new Object[parameters.size()];
        var index = 0;
        for(var param : parameters)
        {
            parametersValues[index] = param.execute(context);
            index ++;
        }
        try
        {
            var methodResult = method.invoke(null, parametersValues);
            if(method.getReturnType().equals(void.class))
            {
                return StringUtils.EMPTY;
            }
            return methodResult;
        } catch (Exception e)
        {
            return "CAN'T Invoke method: "+e.getMessage();
        }
    }
}
