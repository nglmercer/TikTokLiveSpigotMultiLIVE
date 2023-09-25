package io.github.jwdeveloper.spigot.tiktok.core.tests;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.messages.boss_bar.BossBarBuilder;
import io.github.jwdeveloper.ff.core.spigot.messages.boss_bar.SimpleBossBar;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.SymlMethod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.util.Vector;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomMethods {

    private Map<String, File> cache = new HashMap<>();

    @SymlMethod
    public void play(String name) throws Exception {
        if (cache.containsKey(name)) {
            var file = cache.get(name);
            play(file);
            return;
        }

        var soundFile = new File("D:\\MC\\spigot_1.19.4\\plugins\\TikTokLiveSpigot\\sounds\\" + name + ".wav");
        cache.put(name, soundFile);
        play(soundFile);
    }

    private void play(File file) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }


    @SymlMethod
    public void spawn(String entityName, int count)
    {

        FluentApi.tasks().task(() ->
        {
            for(var i =0;i<count;i++)
            {
                var range = 3;
                var random = new Random();
                var entityType = EntityType.valueOf(entityName.toUpperCase());
                var player = Bukkit.getOnlinePlayers().stream().toList().get(0);
                var location = player.getLocation();
                var nexLoc = location.add(random.nextInt(range), 0, random.nextInt(range));
                var entity = location.getWorld().spawnEntity(nexLoc, entityType);

           /* var armorstand = (ArmorStand) location.getWorld().spawnEntity(nexLoc, EntityType.ARMOR_STAND);
            armorstand.setSmall(true);
            armorstand.setVisible(false);
            //armorstand.setCustomName(new MessageBuilder().text(ChatColor.BOLD, ChatColor.GOLD, name).toString());
            armorstand.setCustomNameVisible(true);
            entity.addPassenger(armorstand);*/

                FluentApi.tasks().taskTimer(40,(iteration, task) ->
                {
                    entity.remove();
                });
            }
        });
    }


    public static  int TAPNIECIE;
    private SimpleBossBar simpleBossBar;

    @SymlMethod
    public void smok(int coILE, Object kto)
    {
        if(simpleBossBar == null)
        {
            var builder = new BossBarBuilder();
            builder.setTitle("DOBIJAMY DO SMOKA");
            builder.setColor(BarColor.GREEN);
            builder.setProgress(0);
            simpleBossBar = builder.build();
            var player = Bukkit.getOnlinePlayers().stream().toList().get(0);
            FluentApi.tasks().task(() ->
            {
                simpleBossBar.getBossBar().addPlayer(player);
            });

            FluentApi.events().onEvent(PluginDisableEvent.class,event ->
            {
                simpleBossBar.getBossBar().removePlayer(player);
            });

        }
        TAPNIECIE ++;
        simpleBossBar.setProgress(TAPNIECIE*1f/coILE*1f);
        simpleBossBar.setTitle("TAPNIECIE DO SMOKA ["+TAPNIECIE+"/"+coILE+"]");


        FluentLogger.LOGGER.info("SMOOOOK ",TAPNIECIE);
        if(TAPNIECIE % coILE == 0)
        {
            FluentApi.tasks().task(() ->
            {

                var player = Bukkit.getOnlinePlayers().stream().toList().get(0);
                FluentApi.messages().title().withTitle(ChatColor.GREEN+kto.toString()).withSubTitle("Przyzwał SMOKAAAAA").setFadeOutTicks(40).buildAndSend(player);
                var location = player.getLocation();
                var entity = (EnderDragon)location.getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_DRAGON);
                entity.setPhase(EnderDragon.Phase.CIRCLING);
                entity.setAI(true);

                FluentApi.tasks().taskTimer(10,(iteration, task) ->
                {
                    if(entity.getLocation().distance(location) > 50)
                    {
                        entity.teleport(location);
                    }
                }).run();
            });
            TAPNIECIE = 0;
        }
    }
    @SymlMethod
    public String wepond(String name) {

        var player = Bukkit.getOnlinePlayers().stream().toList().get(0);

        FluentApi.tasks().taskLater(()->
        {
            player.getInventory().setItemInMainHand(null);
        }, 20*15);

        FluentLogger.LOGGER.info("SDAUDPASDUPASDUPASDUPADUPASDSAUDPASUDPASUDPAUSPD");
        return "weaponmechanics give @p "+name;
    }

    @SymlMethod
    public void particleSpin(String name) {
        // spawnParticles(Bukkit.getOnlinePlayers().stream().toList().get(0));

        var random = new Random().nextInt(360);

        spawnAt(random, 1.5f, Bukkit.getOnlinePlayers().stream().toList().get(0));
    }

    @SymlMethod
    public void jump(int howMuch) {
        var player = Bukkit.getOnlinePlayers().stream().toList().get(0);
        Vector velocity = player.getVelocity();
        // Add upward force to make the player jump high
        // The number 2.0 is the upward force you are adding. You can adjust this value as needed.
        velocity.setY(howMuch);
      //  player.setAllowFlight(true);
        // Set the new velocity to the player
        player.setVelocity(velocity);
    }

    @SymlMethod
    public void heal(int howMuch) {
        var player = Bukkit.getOnlinePlayers().stream().toList().get(0);
        double currentHealth = player.getHealth();
        double maxHealth = player.getMaxHealth();

        // Heal the player by 2 health points (1 heart)
        double newHealth = Math.min(currentHealth + howMuch, maxHealth); // Make sure not to exceed max health

        if(player.isDead())
        {
           return;
        }
        player.setHealth(newHealth);
    }

    private void spawnParticles(Player player) {
        Location loc = player.getLocation();
        double radius = 1.5; // You can change the radius
        for (int i = 0; i < 360; i += 15) {
            double angle = i * Math.PI / 180;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            loc.add(x, 1, z); // Adding 1 to the y-coordinate to make it centered at the player's head
            player.getWorld().spawnParticle(Particle.HEART, loc, 1); // You can change the particle type
            loc.subtract(x, 1, z);
        }
    }

    private void spawnAt(int index, double radious, Player player) {
        Location loc = player.getLocation();
        double angle = index * Math.PI / 180;
        double x = radious * Math.cos(angle);
        double z = radious * Math.sin(angle);
        loc.add(x, 1, z); // Adding 1 to the y-coordinate to make it centered at the player's head
        player.getWorld().spawnParticle(Particle.HEART, loc, 1); // You can change the particle type
        loc.subtract(x, 1, z);
    }
}
