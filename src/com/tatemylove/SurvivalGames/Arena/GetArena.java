package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Files.SpawnsFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collections;

public class GetArena {

    public static int CurrentArena;

    public static int chooseNextMap()
    {
        ArrayList<Integer> numbers = new ArrayList();
        for (int k = 0; ArenaFile.getData().contains("Arenas." + k + ".Name"); k++) {
            numbers.add(k);
        }
        Collections.shuffle(numbers);
        return numbers.get(0);
    }

    public static int getNextArena()
    {
        CurrentArena = chooseNextMap();
        return CurrentArena;
    }

    public static int getCurrentArena()
    {
        return CurrentArena;
    }

    public static Location getRedSpawn(int id){
        final double x;
        final double y;
        final double z;
        final World world;
        world = Bukkit.getWorld(SpawnsFile.getData().getString("Spawns." + id + ".Spawns.Red.World"));
        x = SpawnsFile.getData().getDouble("Spawns." + id + ".Spawns.Red.X");
        y = SpawnsFile.getData().getDouble("Spawns." + id + ".Spawns.Red.Y");
        z = SpawnsFile.getData().getDouble("Spawns." + id + ".Spawns.Red.Z");

        Location redSpawn = new Location(world, x, y, z);
        return redSpawn;
    }
}
