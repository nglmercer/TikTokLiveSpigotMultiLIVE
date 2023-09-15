package io.github.jwdeveloper.spigot.tiktok;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.tools.FluentTaskAction;
import io.github.jwdeveloper.ff.tools.files.generators.permissions.PermissionGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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


    }
}
