package io.github.jwdeveloper.spigot.tiktok;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.tools.GenerateDescriptionTask;
import io.github.jwdeveloper.spigot.tiktok.core.commands.MapTest;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class ImageTest extends GenerateDescriptionTask
{
    @Test
    public void start()
    {

    }

    @Override
    public String getPluginName() {
        return "dupa";
    }

    @Override

    public void onDescriptionConfig(DescriptionDto descriptionDto) {

    }

    @Override
    public void onFluentPluginBuilding(FluentPluginBuilder builder) {
        FluentLogger.setLogger("X");
        try {
            var imageUrl = "https://p16-sign-useast2a.tiktokcdn.com/tos-useast2a-avt-0068-euttp/0ceb21189fc09d6b48e95dca7cc7939e~tplv-tiktok-shrink:72:72.webp?x-expires=1694818800&x-signature=YXHeeJ6dpg3bXCNDvto%2B6Eeaek8%3D";
            var result =  MapTest.downloadImage(imageUrl);

            var i =0;
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Not working",e);
        }




    }
}
