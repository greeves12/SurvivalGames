package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitingCountdown extends BukkitRunnable {
    public static int timuntilstart;
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(timuntilstart == 0) {
                for (Player p : Main.PlayingPlayers) {
                    SendCoolMessages.sendTitle(p, "§cGrace Period Starting!", 10, 30, 10);
                    Main.stopWaitingCountdown();
                    Main.startGracePeriod();
                }
            }
            if(timuntilstart % 1 == 0){
                for(Player p : Main.PlayingPlayers){
                    SendCoolMessages.sendTitle(p, "§6Starting in §3" + timuntilstart + " §2seconds", 10, 30, 10);
                }
            }
            timuntilstart -= 1;
        }
    }
}
