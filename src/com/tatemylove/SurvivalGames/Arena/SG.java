package com.tatemylove.SurvivalGames.Arena;


import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Files.SpawnsFile;
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
                World world = Bukkit.getServer().getWorld("sg");
                for (Chunk c : world.getLoadedChunks()) {
                    for (BlockState b : c.getTileEntities()) {
                        if (b instanceof Chest) {
                            Chest chest = (Chest) b;
                            Inventory inventory = chest.getBlockInventory();
                            Material[] randomItens = {Material.COOKIE, Material.APPLE, Material.AIR, Material.STICK, Material.CARROT, Material.IRON_INGOT, Material.DIAMOND, Material.COBBLESTONE, Material.MUSHROOM_SOUP, Material.COOKED_FISH, Material.FISHING_ROD, Material.RAW_FISH, Material.COAL, Material.MELON, Material.GOLD_INGOT, Material.WOOD, Material.SUGAR, Material.BOW, Material.ARROW, Material.WOOD_SWORD, Material.STONE_SWORD, Material.WOOD_AXE, Material.STONE_AXE, Material.PUMPKIN_PIE, Material.GRILLED_PORK, Material.APPLE, Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS};
                            Random randy = new Random();
                            for (int i = 0; i < 4; i++) {
                                Random rand = new Random();
                                inventory.setItem(randy.nextInt(26), new ItemStack(randomItens[rand.nextInt(randomItens.length)]));
                            }
                        }
                    }
                }
            }
            Bukkit.setWhitelist(true);
        }
    }

    public static void endSG(){
        if(BaseArena.states  == BaseArena.ArenaStates.Ended){
            for(Player p : Main.PlayingPlayers){
                SendCoolMessages.sendTitle(p, "§7You have won the Game!", 30, 50, 30);
                p.sendMessage(Main.prefix + "§7You have won the Game!");
                p.sendMessage(Main.prefix + "§aTeleporting you back to the Hub in §55" + " seconds");
            }
        }
    }
}
