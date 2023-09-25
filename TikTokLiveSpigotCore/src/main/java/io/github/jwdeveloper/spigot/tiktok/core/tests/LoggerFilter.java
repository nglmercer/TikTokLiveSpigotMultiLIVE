package io.github.jwdeveloper.spigot.tiktok.core.tests;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.bukkit.Bukkit;

public class LoggerFilter extends AbstractFilter {
    @Override
    public Result filter(LogEvent event)
    {
        var sender = event.getLoggerName();
        if(!sender.equals("net.minecraft.server.MinecraftServer"))
        {
            return Result.NEUTRAL;
        }
        return Result.DENY;
    }
}
