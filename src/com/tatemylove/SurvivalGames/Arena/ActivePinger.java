package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ActivePinger extends BukkitRunnable {
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(Main.PlayingPlayers.size() == 1){
                BaseArena.states = BaseArena.ArenaStates.Ended;
                Main.startEndingCountdown();
                SG.endSG();
            }
        }
        if(BaseArena.states == BaseArena.ArenaStates.Ended){
            if(Bukkit.getOnlinePlayers().isEmpty()){
                Bukkit.shutdown();
            }
        }
    }
}
