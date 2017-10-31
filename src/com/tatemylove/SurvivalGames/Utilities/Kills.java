package com.tatemylove.SurvivalGames.Utilities;

import com.tatemylove.SurvivalGames.MySQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Kills {
    public static boolean exists(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM SGkills");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())) return true;
                break;
            }
            ps.close();
            rs.close();
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static void firstKill(Player p){
        try {
            if (!exists(p)) {
                int number = 0;
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into SGkills(uuid, kills)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void addKills(Player p){
        try{
            if(exists(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SGkills SET kills= kills+1 WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
