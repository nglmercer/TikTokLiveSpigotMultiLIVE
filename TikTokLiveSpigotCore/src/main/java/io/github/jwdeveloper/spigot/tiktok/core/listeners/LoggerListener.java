package io.github.jwdeveloper.spigot.tiktok.core.listeners;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.bukkit.Bukkit;

@Injection
public class LoggerListener extends AbstractFilter {
    private int count = 0;

    @Override
    public Result filter(LogEvent event)
    {
        if(isStopped() || isStopping())
        {
            return Result.NEUTRAL;
        }

        var sender = event.getLoggerName();
        if (!sender.equals("net.minecraft.server.MinecraftServer")) {
            return Result.NEUTRAL;
        }
        if (count <= 0) {
            count = 0;
            return Result.NEUTRAL;
        }

        count--;
        return Result.DENY;
    }

    public void addMessagesToIgnore(int count) {
        this.count += count;
    }

    public void addMessageToIgnore()
    {
        this.addMessagesToIgnore(1);
    }
}