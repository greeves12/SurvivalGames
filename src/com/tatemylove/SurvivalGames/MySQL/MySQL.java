package com.tatemylove.SurvivalGames.MySQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MySQL {
    public static Connection connection;

    public MySQL(String ip, String userName, String password, String db){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
            createTable();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static void createTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SGstats(uuid varchar(36), points int)");
        ps.executeUpdate();
        ps.close();
    }
}
