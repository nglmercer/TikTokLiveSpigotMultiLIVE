package io.github.jwdeveloper.spigot.tiktok;

import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.tools.FluentFrameworkTask;
import io.github.jwdeveloper.ff.tools.GenerateDescriptionTask;
import io.github.jwdeveloper.spigot.tiktok.core.TikTokLiveSpigotMain;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;

//https://github.com/matiassingers/awesome-readme
//https://github.com/amplication/amplication#readme
public class GenerateDescriptionTest extends GenerateDescriptionTask {


    @FluentFrameworkTask
    public void invoker()
    {
    }


    @Override
    public Class<?> getJarScannerClass() {
        return TikTokLiveSpigotMain.class;
    }

    @Override
    public String getPluginName() {
        return "tiktoklive";
    }

    @Override
    public void onDescriptionConfig(DescriptionDto descriptionDto) {

        descriptionDto.setBannerOptions(bannerOptions ->
        {
            bannerOptions.setDiscordUrl("https://discord.gg/fk3W4e3K");
            bannerOptions.setGithubUrl("https://github.com/jwdeveloper/TikTokLiveSpigot");
            bannerOptions.setSpigotUrl("https://www.spigotmc.org/members/jacekwoln.869774/");
            bannerOptions.setDonationUrl("https://www.buymeacoffee.com/jwdev");
            bannerOptions.setBannerImage("https://raw.githubusercontent.com/jwdeveloper/TikTokLiveSpigot/master/webeditor/resources/banner_small.gif");
        });
        descriptionDto.setPermissionsClass(PermissionsTemplate.class);
        descriptionDto.setPluginMain(TikTokLiveSpigotConfig.class);
        descriptionDto.setPluginName("tiktoklive");
        descriptionDto.setInput("src\\test\\resources\\template.html");
        descriptionDto.setOutput("src\\test\\resources\\output");
    }

    @Override
    public void onFluentPluginBuilding(FluentPluginBuilder builder) {
        TikTokLiveSpigotMain.useFluentFramework(getPluginMock(), builder);
    }




}