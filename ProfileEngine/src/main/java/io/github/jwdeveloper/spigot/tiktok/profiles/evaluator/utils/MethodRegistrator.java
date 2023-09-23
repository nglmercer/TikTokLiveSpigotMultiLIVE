package io.github.jwdeveloper.spigot.tiktok.profiles.evaluator.utils;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.SymlMethod;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.exceptions.SymlEngineException;

import java.lang.reflect.Method;
import java.util.*;

public class MethodRegistrator
{
    public List<MethodDefinition> register(Object instance) {
        var clazz = instance.getClass();
        var methods = getMethods(clazz);
        var result =new ArrayList<MethodDefinition>();
        for (var entry : methods.entrySet())
        {
            var fun = handleMethod(entry.getKey(),entry.getValue(), instance);
            result.add(fun);
        }
        return result;
    }


    private Map<String, List<Method>> getMethods(Class<?> clazz)
    {
        var methods = Arrays.stream(clazz.getMethods()).filter(e -> e.isAnnotationPresent(SymlMethod.class)).toList();
        // Create a map to group methods by their names
        Map<String, List<Method>> groupedMethods = new HashMap<>();

        for (Method method : methods) {
            // Get the method name
            String methodName = method.getName();

            // If the method name is not already in the map, add it with a new empty list
            if (!groupedMethods.containsKey(methodName)) {
                groupedMethods.put(methodName, new ArrayList<>());
            }

            // Add the method to the list associated with its name
            groupedMethods.get(methodName).add(method);
        }
        return groupedMethods;
    }

    private Map<Integer, Method> groupByParameterCount(List<Method> methods)
    {
        Map<Integer,Method> groupedMethods = new HashMap<>();
        for (Method method : methods) {
            var key = method.getParameterCount();
            groupedMethods.put(key, method);
        }
        return groupedMethods;
    }

    public MethodDefinition handleMethod(String name, List<Method> methods, Object instance)
    {
        var methodsMap = groupByParameterCount(methods);

        var declaration = new MethodDefinition(name,(parameters)->
        {
            if(!methodsMap.containsKey(parameters.size()))
            {
                System.out.println("Bad parameters count for function: " + name);
                return null;
            }
            var method = methodsMap.get(parameters.size());
            try {
                Object[] array = parameters.toArray();
                return method.invoke(instance, array);
            } catch (Exception e) {

                throw new SymlEngineException(e);
            }
        }, true);

        return declaration;
    }

}
