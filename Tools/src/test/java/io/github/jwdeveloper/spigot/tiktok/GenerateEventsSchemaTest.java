package io.github.jwdeveloper.spigot.tiktok;

import com.google.gson.GsonBuilder;
import io.github.jwdeveloper.ff.core.common.java.ObjectUtility;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.tools.FluentFrameworkTask;
import io.github.jwdeveloper.ff.tools.FluentTaskAction;
import io.github.jwdeveloper.tiktok.events.TikTokEvent;
import io.github.jwdeveloper.tiktok.events.messages.TikTokGiftMessageEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.*;

public class GenerateEventsSchemaTest extends FluentTaskAction {


    @FluentFrameworkTask
    public void invokeTest()
    {
        var outputName = "TikTokEventsSchema.json";
        var outputDirectory = "D:\\Git\\TikTokLiveSpigot\\webeditor\\resources";
        var outputDirectory2 = "D:\\Git\\TikTokLiveSpigot\\vsc-extension\\syml\\out";
        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        var classes = getClasses();


        var classInfoList = new ArrayList<TikTokEventMetaClass>();
        for (var clazz : classes) {
            try {
                var classInfo = handleSingleClass(clazz);
                classInfoList.add(classInfo);
            } catch (StackOverflowError e) {
                System.out.println("Error for class: " + clazz.getName());
                e.printStackTrace();
            }

        }

        var json = gson.toJson(classInfoList);
        FileUtility.save(json, outputDirectory, outputName);
        FileUtility.save(json, outputDirectory2, outputName);
    }


    public static List<Class<? extends TikTokEvent>> getClasses() {
        var reflections = new Reflections("io.github.jwdeveloper.tiktok.events.messages");
        var calsses = reflections.getSubTypesOf(TikTokEvent.class).stream().toList();
        var clas = new ArrayList<Class<? extends TikTokEvent>>(calsses);
        Collections.sort(clas, new ClassNameComparator());
        return clas.stream().toList();
    }


    public static TikTokEventMetaClass handleSingleClass(Class clazz) {
        var fields = getFieldsNames(clazz, "event");
        var name = clazz.getSimpleName()
                .replace("TikTok", StringUtils.EMPTY)
                .replace("Event", StringUtils.EMPTY);
        name = "on" + name;
        if(clazz.equals(TikTokGiftMessageEvent.class))
        {
            name = "onGift";
        }
        return new TikTokEventMetaClass(clazz.getSimpleName(), name, fields);
    }

    public static Map<String, String> getFieldsNames(Class clazz, String prefix) {
        var fields = collectFieldsRecursively(clazz);
        var fieldsMap = new TreeMap<String, String>();
        for (var field : fields) {
            var typeName = field.getType().getSimpleName();
            typeName = StringUtils.capitalize(typeName);

            var name = prefix + "." + field.getName();
            if (!ObjectUtility.isPrimitiveType(field.getType()) && field.getType() != clazz) {
                var subFields = getFieldsNames(field.getType(), name);
                fieldsMap.putAll(subFields);
            } else {
                fieldsMap.put(name, typeName);
            }
        }
        return fieldsMap;
    }

    public static List<Field> collectFieldsRecursively(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        if (clazz == null || clazz.equals(Object.class)) {
            return fields;
        }

        if (clazz.getPackageName().contains("io.github.jwdeveloper.tiktok.messages")) {
            return fields;
        }

        var currentFields = clazz.getDeclaredFields();
        for (Field field : currentFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if(field.getName().contains("this$"))
            {
                continue;
            }
            fields.add(field);
        }

        fields.addAll(collectFieldsRecursively(clazz.getSuperclass()));
        return fields;
    }

    @Override
    public void onFluentPluginBuild(FluentPluginBuilder builder) {

    }


    @Data
    @AllArgsConstructor
    public static class TikTokEventMetaClass {
        private String javaClassName;
        private String name;
        private Map<String, String> fields;
    }

    static class ClassNameComparator implements Comparator<Class<?>> {
        @Override
        public int compare(Class<?> class1, Class<?> class2) {
            return class1.getSimpleName().compareTo(class2.getSimpleName());
        }
    }

}
