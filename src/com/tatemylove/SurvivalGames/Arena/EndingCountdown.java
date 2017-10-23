package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EndingCountdown extends BukkitRunnable {
    public static int timeuntilend;
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Ended){
            if(timeuntilend == 0){
                for(Player p : Main.PlayingPlayers){
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try{
                        out.writeUTF("Connect");
                        out.writeUTF("lobby");
                    }catch (IOException e){

                    }
                    p.sendPluginMessage(ThisPlugin.getPlugin(), "BungeeCord", b.toByteArray());
                }
            }
            if(timeuntilend % 1 == 0) {
                for (Player p : Main.PlayingPlayers) {
                    Firework f = (Firework)p.getPlayer().getWorld().spawn(p.getLocation(), Firework.class);
                }
            }
        }
        timeuntilend -= 1;
    }
}
