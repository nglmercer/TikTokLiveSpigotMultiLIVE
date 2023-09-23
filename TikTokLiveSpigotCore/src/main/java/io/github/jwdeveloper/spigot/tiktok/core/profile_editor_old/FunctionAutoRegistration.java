package io.github.jwdeveloper.spigot.tiktok.core.profile_editor_old;

import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.SymlMethod;

import java.lang.reflect.Method;
import java.util.*;

public class FunctionAutoRegistration {
    public void register(Object instance, TikTokProfilesExecutor profileEditor) {
        var clazz = instance.getClass();
        var methods = getMethods(clazz);
        for (var entry : methods.entrySet())
        {
            handleMethod(entry.getKey(),entry.getValue(), instance, profileEditor);
        }
    }


    private  Map<String, List<Method>> getMethods(Class<?> clazz)
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

    public void handleMethod(String name, List<Method> methods, Object instance, TikTokProfilesExecutor tikTokProfileEditor)
    {

    }


}
