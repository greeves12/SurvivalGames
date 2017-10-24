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
            if(timuntilstart == 0){
                for(Player p: Main.PlayingPlayers){
                    SendCoolMessages.sendTitle(p, "Game has started!", 30, 50, 30);
                }
                Main.stopWaitingCountdown();
                Main.startGracePeriod();
            }
            if(timuntilstart % 2 == 0){
                for(Player p : Main.PlayingPlayers){
                    SendCoolMessages.sendTitle(p, "Game starting in " + timuntilstart, 10, 30, 10);
                }
            }
        }
        timuntilstart -= 1;
    }
}
