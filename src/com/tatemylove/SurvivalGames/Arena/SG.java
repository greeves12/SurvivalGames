package com.tatemylove.SurvivalGames.Arena;


import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Main;
import org.bukkit.entity.Player;

public class SG {
    public static void startSG(String id){
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(ArenaFile.getData().contains("Arenas." + id + ".Name")){
                for(int ID = 0; ID < Main.PlayingPlayers.size(); ID++){
                    final Player p = Main.PlayingPlayers.get(ID);

                }
            }
        }
    }
}
