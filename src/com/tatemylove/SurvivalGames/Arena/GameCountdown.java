package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GameCountdown extends BukkitRunnable{
    public static int timeuntilstart;
    public static ArrayList<String> arena = new ArrayList<>();
    @Override
    public void run() {
        arena.add(ArenaFile.getData().getString("Arenas." + GetArena.getNextArena() + ".Name"));
        if (timeuntilstart == 0) {
            Main.stopGameCountdown();
            BaseArena.states = BaseArena.ArenaStates.Started;
            SG.startSG(Integer.toString(GetArena.getCurrentArena()));
            Main.startWaitingCountdown();
        }
        if (timeuntilstart % 10 == 0) {
            for (Player p : Main.WaitingPlayers) {
                if (Main.WaitingPlayers.size() >= Main.min_players) {
                    SendCoolMessages.sendTitle(p, "§3Starting in §a" + timeuntilstart + " §2seconds", 30, 50, 30);
                }
            }
        }
        if (Main.WaitingPlayers.size() >= Main.min_players) {
            timeuntilstart -= 1;
        }
    }
}
