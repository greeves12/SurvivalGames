package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ActivePinger extends BukkitRunnable {
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){

            if(Main.PlayingPlayers.size() < Main.min_players){
                BaseArena.states = BaseArena.ArenaStates.Ended;
                Main.startEndingCountdown();
                SG.endSG();
            }
        }
        if(BaseArena.states == BaseArena.ArenaStates.Ended || BaseArena.states == BaseArena.ArenaStates.Started){
            if(Bukkit.getOnlinePlayers().isEmpty()){
                Bukkit.shutdown();
            }
        }
    }
}
