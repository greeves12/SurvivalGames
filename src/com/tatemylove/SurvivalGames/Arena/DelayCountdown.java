package com.tatemylove.SurvivalGames.Arena;

import com.tatemylove.SurvivalGames.Main;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Tate on 10/25/2017.
 */
public class DelayCountdown extends BukkitRunnable {
    public static int timeuntilstart;
    @Override
    public void run() {
        if(timeuntilstart == 0){
            Main.stopDelay();

        }
        timeuntilstart -= 1;
    }
}
