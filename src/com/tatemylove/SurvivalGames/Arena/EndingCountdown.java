package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.MySQL.MySQL;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class EndingCountdown extends BukkitRunnable {
    public static int timeuntilend;
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Ended){
            if(timeuntilend == 0){
                World world = Bukkit.getServer().getWorld("sg");
                List<Entity> entList = world.getEntities();
                for(Entity current : entList){
                    if(current instanceof Item){
                        current.remove();
                    }
                }
                for(Player p : Main.PlayingPlayers){
                    MySQL.firstWin(p);
                    MySQL.addWins(p);
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try{
                        out.writeUTF("Connect");
                        out.writeUTF("sglobby");
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
                    p.sendMessage(Main.prefix + "§aTeleporting you back to the Hub in §5" + timeuntilend + " seconds");
                }
            }
        }
        timeuntilend -= 1;
    }
}
