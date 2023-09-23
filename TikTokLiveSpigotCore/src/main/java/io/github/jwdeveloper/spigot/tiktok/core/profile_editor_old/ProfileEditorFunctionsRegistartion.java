package io.github.jwdeveloper.spigot.tiktok.core.profile_editor_old;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.TikTokProfilesExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


@Injection
public class ProfileEditorFunctionsRegistartion {
    private final TikTokProfilesExecutor profileEditor;


    public ProfileEditorFunctionsRegistartion(TikTokProfilesExecutor profileEditor) {
        this.profileEditor = profileEditor;
    }

    public void register() {


        for (var p : Bukkit.getOnlinePlayers()) {
            setScoreboard(p.getPlayer());
        }


    }


    int czasZycie =0;

    int maxTime =0;

    public void setScoreboard(Player player) {
        // Create a new scoreboard
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        // Create a new objective. Arguments: Objective name, criteria, display name
        Objective objective = scoreboard.registerNewObjective("test", "dummy", ChatColor.BOLD + "" + ChatColor.GREEN + "======= STATYSTYKI ======");

        // Set where to display the scoreboard/objective (SIDEBAR, PLAYER_LIST, or BELOW_NAME)
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        var ZGODY = objective.getScore(new MessageBuilder().inBrackets("ZGONY", org.bukkit.ChatColor.GREEN).toString()); // Dynamic Content
        ZGODY.setScore(0);

        var czasŻycia = objective.getScore(new MessageBuilder().inBrackets("CZAS Życia", org.bukkit.ChatColor.GREEN).toString()); // Dynamic Content
        czasŻycia.setScore(0);

        var recordTime = objective.getScore(new MessageBuilder().inBrackets("Rekordowy czas Życia", ChatColor.GOLD).toString()); // Dynamic Content
        recordTime.setScore(0);

        var killedMobs = objective.getScore(new MessageBuilder().inBrackets("Zabite Moby", ChatColor.WHITE).toString()); // Dynamic Content
        killedMobs.setScore(0);

        FluentApi.events().onEvent(EntityDeathEvent.class,entityDeathEvent ->
        {
            killedMobs.setScore(killedMobs.getScore()+1);
        });

        FluentApi.events().onEvent(PlayerDeathEvent.class, deathEvent ->
        {
            ZGODY.setScore(ZGODY.getScore() + 1);
            if(czasZycie > maxTime)
            {
                maxTime = czasZycie;

                FluentApi.tasks().taskTimer(40,(iteration, task) ->
                {
                    FluentApi.messages().title().withTitle("Nowy rekord życia").withSubTitle("GÓWNO SRAKA!").buildAndSend(player);
                });
                killedMobs.setScore(maxTime);
            }
            czasZycie  =0;
        });

        FluentApi.tasks().taskTimer(20, (iteration, task) ->
        {
            czasŻycia.setScore(czasZycie);
            czasZycie++;
        }).run();
        // Assign the scoreboard to the player
        player.setScoreboard(scoreboard);
    }
}
