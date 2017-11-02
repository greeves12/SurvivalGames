package com.tatemylove.SurvivalGames.Utilities;

import com.tatemylove.SurvivalGames.MySQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Tate on 11/1/2017.
 */
public class Credits {
    public static boolean exists(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM Credits");
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
    public static void firstCredit(Player p){
        try{
            if(!exists(p)){
                int number = 0;
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into Credits(uuid, credits)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void addCredits(Player p){
        try{
            if(exists(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE Credits SET credits= credits+10 WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
