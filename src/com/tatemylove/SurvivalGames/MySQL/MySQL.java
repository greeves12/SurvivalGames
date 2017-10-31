package com.tatemylove.SurvivalGames.MySQL;


import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {
    public static Connection connection;

    public MySQL(String ip, String userName, String password, String db){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
            createTable();
            createKillsTable();
            createDeathsTable();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static void createTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SGstats(uuid varchar(36) NOT NULL, points int)");
        ps.executeUpdate();
        ps.close();
    }
    private static void createKillsTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SGkills(uuid varchar(36) NOT NULL, kills int)");
        ps.executeUpdate();
        ps.close();
    }
    private static void createDeathsTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SGdeaths(uuid varchar(36) NOT NULL, deaths int)");
        ps.executeUpdate();
        ps.close();
    }
    public static void firstWin(Player p){
        int number = 0;
        try {
            if (!exists(p)) {
                PreparedStatement ps = connection.prepareStatement("INSERT into SGstats(uuid, points)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    public static boolean exists(Player p){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT uuid FROM SGstats");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())) return true;
            }
            rs.close();
            ps.close();
            return false;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static void addWins(Player p){
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE SGstats SET points= points+1 WHERE uuid='" + p.getUniqueId().toString() + "'");
            ps.executeUpdate();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
