package io.github.jwdeveloper.spigot.tiktok;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionDto;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.tools.FluentTaskAction;
import io.github.jwdeveloper.ff.tools.description.spigot.PermissionDocumentationGenerator;
import io.github.jwdeveloper.ff.tools.files.TemplateUtility;
import io.github.jwdeveloper.ff.tools.files.generators.permissions.PermissionGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

public class GeneratePermissionsCodeTest extends FluentTaskAction
{
    @Test
    public void invoker()
    {
        var outputPath ="D:\\Git\\TikTokLiveSpigot\\TikTokLiveSpigotCore\\src\\main\\java\\io\\github\\jwdeveloper\\spigot\\tiktok\\core\\common";
        try {
            PermissionGenerator.generate(PermissionsTemplate.class,outputPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onFluentPluginBuild(FluentPluginBuilder builder) {

        builder.withCustomExtension(builder1 ->
        {
            builder1.onFluentApiEnable(this::updatePluginYml);
        });
    }


    private void updatePluginYml(FluentApiSpigot fluentApiSpigot)
    {
        try {
            var isTemp = false;
            var dto = new PermissionDto(PermissionsTemplate.class, fluentApiSpigot.permission().getPermissions());
            var permissions = new PermissionDocumentationGenerator(dto);
            var content = permissions.generate();


            var template = """
                name: TikTokLiveSpigot
                version: ${project.version}
                main: io.github.jwdeveloper.spigot.tiktok.core.TikTokLiveSpigotMain
                author: JW
                description: TikTok live integration for Minecraft servers
                api-version: 1.17
                {permissions}
                """;
            var values = new HashMap<String, Object>();
            values.put("permissions", content);
            var pluginYml = TemplateUtility.generateTemplate(template, values);
            var path = "D:\\Git\\TikTokLiveSpigot\\TikTokLiveSpigotCore\\src\\main\\resources\\";
            var fileName = isTemp?"plugin_temp.yml":"plugin.yml";
            FileUtility.saveToFile(path, fileName, pluginYml);
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }

    }
}
