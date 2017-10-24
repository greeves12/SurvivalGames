package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
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
                    FireworkMeta fmeta = f.getFireworkMeta();
                    fmeta.addEffect(FireworkEffect.builder()
                            .trail(true)
                            .withColor(Color.ORANGE)
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .build());
                    fmeta.setPower(2);
                    f.setFireworkMeta(fmeta);
                    p.sendMessage(Main.prefix + "Teleported you back to the Hub in " + timeuntilend + " seconds");
                }
            }
        }
        timeuntilend -= 1;
    }
}
