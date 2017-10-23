package com.tatemylove.SurvivalGames.Commands;

import com.tatemylove.SurvivalGames.Files.ArenaFile;
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
    public static void setSpawns(Player p, String[] args, int id, int id2){
        if (args.length == 4) {
            if (args[2].equalsIgnoreCase("blue")) {
                String world = p.getLocation().getWorld().getName();
                double x = p.getLocation().getX();
                double y = p.getLocation().getY();
                double z = p.getLocation().getZ();

                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Blue.World", world);
                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Blue.X", x);
                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Blue.Y", y);
                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Blue.Z", z);
                ArenaFile.saveData();
                ArenaFile.reloadData();
                p.sendMessage(Main.prefix + "You set the blue spawn! " + id2);

            }else if (args[2].equalsIgnoreCase("red")){
                String world = p.getLocation().getWorld().getName();
                double x = p.getLocation().getX();
                double y = p.getLocation().getY();
                double z = p.getLocation().getZ();

                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Red.World", world);
                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Red.X", x);
                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Red.Y", y);
                ArenaFile.getData().set("Arenas." + id + "." + id2 + ".Spawns.Red.Z", z);
                ArenaFile.saveData();
                ArenaFile.reloadData();
                p.sendMessage(Main.prefix + "You set the red spawn! " + id2);
            }
    }
}
}
