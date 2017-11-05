package com.tatemylove.SurvivalGames;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.tatemylove.SurvivalGames.Arena.*;
import com.tatemylove.SurvivalGames.Commands.MainCommand;
import com.tatemylove.SurvivalGames.Files.ArenaFile;
import com.tatemylove.SurvivalGames.Files.LobbyFile;
import com.tatemylove.SurvivalGames.Files.SpawnsFile;
import com.tatemylove.SurvivalGames.MySQL.MySQL;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {
    public static String prefix = "§a§l[Survival§2§lGames] ";
    public static ArrayList<Player> WaitingPlayers = new ArrayList<>();
    public static ArrayList<Player> PlayingPlayers = new ArrayList<>();
    public static int min_players = 2;
    private static int gamecountdownid;
    private static int endingcountdownid;
    private static int waitingcountdownid;
    private static int graceperiod;
    private static int delay;
    private ProtocolManager manager;
    private MySQL mySQL;


    public void onEnable() {
        String ip = ThisPlugin.getPlugin().getConfig().getString("MySQL.Ip");
        String userName = ThisPlugin.getPlugin().getConfig().getString("MySQL.Username");
        String password = ThisPlugin.getPlugin().getConfig().getString("MySQL.Password");
        String db = ThisPlugin.getPlugin().getConfig().getString("MySQL.Database");
            mySQL = new MySQL(ip, userName, password, db);

            manager = ProtocolLibrary.getProtocolManager();
            Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
            MainCommand cmd = new MainCommand();
            getCommand("sg").setExecutor(cmd);

            ActivePinger pinger = new ActivePinger();
            pinger.runTaskTimerAsynchronously(this, 50, 5);
            startGameCountdown();

            ArenaFile.setup(this);
            LobbyFile.setup(this);
            SpawnsFile.setup(this);

            BaseArena.states = BaseArena.ArenaStates.Countdown;

            getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

            ThisPlugin.getPlugin().getConfig().options().copyDefaults(true);
            ThisPlugin.getPlugin().saveDefaultConfig();
            ThisPlugin.getPlugin().reloadConfig();
            Bukkit.setWhitelist(false);

    }
    public static void startGameCountdown(){
        gamecountdownid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GameCountdown(), 0L, 20L);
        GameCountdown.timeuntilstart = 80;
    }
    public static void stopGameCountdown(){
        Bukkit.getServer().getScheduler().cancelTask(gamecountdownid);
    }
    public static void startEndingCountdown(){
        endingcountdownid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new EndingCountdown(), 0L, 20L);
        EndingCountdown.timeuntilend = 5;
    }
    public static void startWaitingCountdown(){
        waitingcountdownid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new WaitingCountdown(), 0L, 20L);
        WaitingCountdown.timuntilstart = 15;
    }
    public static void stopWaitingCountdown(){
        Bukkit.getServer().getScheduler().cancelTask(waitingcountdownid);
    }
    public static void startGracePeriod(){
        graceperiod = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GracePeriodCountDown(), 0L, 20L);
        GracePeriodCountDown.timeuntilstart = 30;
    }
    public static void stopGracePeriod(){
        Bukkit.getServer().getScheduler().cancelTask(graceperiod);
    }
    public static void startDelay(){
        delay = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new DelayCountdown(), 0L, 20L);
        DelayCountdown.timeuntilstart = 3;
    }
    public static void stopDelay(){
        Bukkit.getServer().getScheduler().cancelTask(delay);
    }

}
