package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdown extends BukkitRunnable{
    public static int timeuntilstart;
    @Override
    public void run() {
        if (timeuntilstart == 0) {
            Main.stopGameCountdown();
            BaseArena.states = BaseArena.ArenaStates.Started;
            SG.startSG(Integer.toString(GetArena.getCurrentArena()));
        }
        if (timeuntilstart % 10 == 0) {
            for (Player p : Main.WaitingPlayers) {
                p.sendMessage(Main.prefix + "Starting in " + timeuntilstart);
            }
        }
        if (Main.WaitingPlayers.size() >= Main.min_players) {
            timeuntilstart -= 1;
        }
    }
}
