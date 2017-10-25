package com.tatemylove.SurvivalGames.Arena;


import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.Utilities.SendCoolMessages;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SG {
    public static void startSG(String id){
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(ArenaFile.getData().contains("Arenas." + id + ".Name")){
                Main.PlayingPlayers.addAll(Main.WaitingPlayers);
                Main.WaitingPlayers.clear();
                for(int ID = 0; ID < Main.PlayingPlayers.size(); ID++){
                    final Player p = Main.PlayingPlayers.get(ID);
                    p.getInventory().clear();
                    p.teleport(GetArena.getRedSpawn(ID));
                    p.setGameMode(GameMode.SURVIVAL);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                }
                World world = Bukkit.getWorld("sg");

                for (Chunk c : world.getLoadedChunks()) {

                    for (BlockState b : c.getTileEntities()) {
                        if (b instanceof Chest) {
                            Chest chest = (Chest) b;
                            Inventory inventory = chest.getBlockInventory();
                            Material[] randomItens = {Material.COOKIE, Material.APPLE, Material.AIR, Material.STICK, Material.CARROT, Material.IRON_INGOT, Material.DIAMOND, Material.COBBLESTONE, Material.MUSHROOM_SOUP, Material.COOKED_FISH, Material.FISHING_ROD, Material.RAW_FISH, Material.COAL, Material.MELON, Material.GOLD_INGOT, Material.WOOD, Material.SUGAR, Material.BOW, Material.ARROW};
                            Random randy = new Random();
                            for (int i = 0; i < 3; i++) {
                                Random rand = new Random();
                                inventory.setItem(randy.nextInt(27), new ItemStack(randomItens[rand.nextInt(randomItens.length)]));
                            }
                        }
                    }
                }
            }
        }
    }
    public static void endSG(){
        if(BaseArena.states  == BaseArena.ArenaStates.Ended){
            for(Player p : Main.PlayingPlayers){
                SendCoolMessages.sendTitle(p, "§7You have won the Game!", 30, 50, 30);
            }
        }
    }
}
