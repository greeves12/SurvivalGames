package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GracePeriodCountDown extends BukkitRunnable {
    public static int timeuntilstart;
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
        if(timeuntilstart == 0) {
            for (Player p : Main.PlayingPlayers) {
                SendCoolMessages.sendTitle(p, "Combat has started!", 30, 50, 30);
            }
            Main.stopGracePeriod();
        }
        if(timeuntilstart % 10 == 0){
            for(Player p : Main.PlayingPlayers){
                SendCoolMessages.sendTitle(p, timeuntilstart + " seconds!", 30, 50, 30);
            }
        }
        }
        timeuntilstart -= 1;
    }
}
