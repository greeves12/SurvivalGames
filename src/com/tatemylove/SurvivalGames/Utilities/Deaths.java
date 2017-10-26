package com.tatemylove.SurvivalGames.Utilities;

import com.tatemylove.SurvivalGames.MySQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Deaths {
    public static boolean exists(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM SGdeaths");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())) return true;
            }
            ps.close();
            rs.close();
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static void firstDeath(Player p){
        try{
            if(!exists(p)){
                int number = 0;
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into SGdeaths(uuid, deaths)\nvalues('" + p.getUniqueId() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void addDeaths(Player p){
        try{
            if(exists(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SGdeaths SET deaths= deaths+1 WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
