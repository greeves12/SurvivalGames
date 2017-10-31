package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
            for(Player p : Bukkit.getOnlinePlayers()){
                if(!Main.WaitingPlayers.contains(p) || (!Main.PlayingPlayers.contains(p))){
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try{
                        out.writeUTF("Connect");
                        out.writeUTF("sglobby");
                    }catch(Exception e){

                    }
                    p.sendPluginMessage(ThisPlugin.getPlugin(), "BungeeCord", b.toByteArray());
                }
            }
        }
        if(BaseArena.states == BaseArena.ArenaStates.Ended || BaseArena.states == BaseArena.ArenaStates.Started){
            if(Bukkit.getOnlinePlayers().isEmpty()){
                Bukkit.shutdown();
            }
        }
    }
}
