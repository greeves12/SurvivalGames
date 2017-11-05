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
        }
        return true;
    }
}
