package com.tatemylove.SurvivalGames.Arena;


import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.Utilities.SendCoolMessages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SG {
    public static void startSG(String id){
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(ArenaFile.getData().contains("Arenas." + id + ".Name")){
                for(int ID = 0; ID < Main.PlayingPlayers.size(); ID++){
                    final Player p = Main.PlayingPlayers.get(ID);
                    p.getInventory().clear();
                    p.teleport(GetArena.getRedSpawn(ID));
                    SendCoolMessages.sendTitle(p, "Game is Starting Soon", 30, 50, 30);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                }
                Main.startWaitingCountdown();
            }
        }
    }
    public static void endSG(){
        if(BaseArena.states  == BaseArena.ArenaStates.Ended){
            for(Player p : Main.PlayingPlayers){
                SendCoolMessages.sendTitle(p, "You have won the Game!", 30, 50, 30);
            }
        }
    }
}
