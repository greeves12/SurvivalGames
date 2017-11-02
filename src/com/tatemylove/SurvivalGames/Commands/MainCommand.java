package com.tatemylove.SurvivalGames.Commands;

import com.tatemylove.SurvivalGames.Arena.SetLobby;
import com.tatemylove.SurvivalGames.Main;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("create")) {
                if (p.hasPermission("sg.create")) {
                    String name = args[1];
                    CreateArena.createArena(p, name);
                }
            }
            if(args[0].equalsIgnoreCase("set")){
                if(p.hasPermission("sg.setspawns")){
                    String k = args[1];
                    int j = Integer.parseInt(k);

                    CreateArena.setSpawns(p, args, j);
                }
            }
            if(args[0].equalsIgnoreCase("setlobby")){
                if(p.hasPermission("sg.setlobby")){
                    SetLobby.setLobby(p);
                }
            }
            if(args[0].equalsIgnoreCase("leave")){
                if(Main.WaitingPlayers.contains(p)){
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
            if(args[0].equalsIgnoreCase("fill")){
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
                                inventory.setItem(randy.nextInt(27), new ItemStack(randomItens[rand.nextInt(randomItens.length)]));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
