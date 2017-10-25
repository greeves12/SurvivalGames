package com.tatemylove.SurvivalGames.Commands;

import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Files.SpawnsFile;
import com.tatemylove.SurvivalGames.Main;
import org.bukkit.entity.Player;

import java.util.TreeMap;

public class CreateArena {
    public static void createArena(Player p, String name){
        TreeMap<Integer, Integer> numbers = new TreeMap<>();

        for (int k = 0; ArenaFile.getData().contains("Arenas." + k); k++) {
            numbers.put(k, k);
        }

        int newID;
        if (numbers.size() == 0) {
            newID = 0;
        } else {
            newID = numbers.lastEntry().getValue() + 1;
        }        ArenaFile.getData().set("Arenas." + newID + ".Name", name);
        ArenaFile.saveData();
        ArenaFile.reloadData();
        p.sendMessage(Main.prefix + "§bArena: §a" + name  + " §bcreated with the ID §a" + newID);
    }
    public static void setSpawns(Player p, String[] args, int id){
        if (args.length == 3) {
             if (args[2].equalsIgnoreCase("spawn")){
                String world = p.getLocation().getWorld().getName();
                double x = p.getLocation().getX();
                double y = p.getLocation().getY();
                double z = p.getLocation().getZ();

                SpawnsFile.getData().set("Spawns." + id + ".Spawns.Red.World", world);
                SpawnsFile.getData().set("Spawns." + id + ".Spawns.Red.X", x);
                SpawnsFile.getData().set("Spawns." + id + ".Spawns.Red.Y", y);
                SpawnsFile.getData().set("Spawns." + id + ".Spawns.Red.Z", z);
                SpawnsFile.saveData();
                SpawnsFile.reloadData();
                p.sendMessage(Main.prefix + "§bYou set the spawn! §c" + id);
            }
    }
}
}
