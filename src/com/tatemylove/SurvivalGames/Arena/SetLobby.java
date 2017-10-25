package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Files.LobbyFile;
import com.tatemylove.SurvivalGames.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SetLobby {
    public static void setLobby(Player p){
        String world = p.getWorld().getName();
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();

        LobbyFile.getData().set("Lobby.World", world);
        LobbyFile.getData().set("Lobby.X", x);
        LobbyFile.getData().set("Lobby.Y", y);
        LobbyFile.getData().set("Lobby.Z", z);
        LobbyFile.saveData();
        LobbyFile.reloadData();
        p.sendMessage(Main.prefix + "§bYou set the lobby!");
    }
    public static Location getLobby(){
        final double x;
        final double y;
        final double z;
        final World world;

         world = Bukkit.getServer().getWorld(LobbyFile.getData().getString("Lobby.World"));
         x = LobbyFile.getData().getDouble("Lobby.X");
         y = LobbyFile.getData().getDouble("Lobby.Y");
         z = LobbyFile.getData().getDouble("Lobby.Z");

        Location loc = new Location(world, x, y, z);
        return loc;
    }
}
