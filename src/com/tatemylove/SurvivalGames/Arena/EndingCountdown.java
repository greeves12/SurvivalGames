package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.MySQL.MySQL;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import com.tatemylove.SurvivalGames.Utilities.Credits;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class EndingCountdown extends BukkitRunnable {
    public static int timeuntilend;
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Ended){
            if(timeuntilend == 0){
                World world = Bukkit.getServer().getWorld("sg");
                for (Chunk c : world.getLoadedChunks()) {
                    for (BlockState b : c.getTileEntities()) {
                        if (b instanceof Chest) {
                            Chest chest = (Chest) b;
                            Inventory inventory = chest.getBlockInventory();
                            for (int i = 0; i < 27; i++) {
                                inventory.setItem(i, null);
                            }
                        }
                    }
                }
                World world2 = Bukkit.getServer().getWorld("sg");
                List<Entity> entList = world2.getEntities();
                for(Entity current : entList){
                    if(current instanceof Item){
                        current.remove();
                    }
                }
                for(Player p : Main.PlayingPlayers){
                    MySQL.firstWin(p);
                    MySQL.addWins(p);
                    Credits.firstCredit(p);
                    Credits.addCredits(p);
                    p.sendMessage(Main.prefix + "§bYou have received §e§l10 §3§lCredits §bfor winning!");
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
            if(timeuntilend > 0){
            if(timeuntilend % 1 == 0) {
                for (Player p : Main.PlayingPlayers) {
                    Firework f = (Firework) p.getPlayer().getWorld().spawn(p.getLocation(), Firework.class);
                    FireworkMeta fmeta = f.getFireworkMeta();
                    fmeta.addEffect(FireworkEffect.builder()
                            .trail(true)
                            .withColor(Color.ORANGE)
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .build());
                    fmeta.setPower(2);
                    f.setFireworkMeta(fmeta);
                }
            }
            }
        }
        timeuntilend -= 1;
    }
}
