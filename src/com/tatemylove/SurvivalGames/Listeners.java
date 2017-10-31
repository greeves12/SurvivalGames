package com.tatemylove.SurvivalGames;

import com.tatemylove.SurvivalGames.Arena.BaseArena;
import com.tatemylove.SurvivalGames.Arena.GracePeriodCountDown;
import com.tatemylove.SurvivalGames.Arena.SetLobby;
import com.tatemylove.SurvivalGames.Arena.WaitingCountdown;
import com.tatemylove.SurvivalGames.Inventories.Inventories;
import com.tatemylove.SurvivalGames.ThisPlugin.ThisPlugin;
import com.tatemylove.SurvivalGames.Utilities.Deaths;
import com.tatemylove.SurvivalGames.Utilities.Kills;
import com.tatemylove.SurvivalGames.Utilities.SendCoolMessages;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Listeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        int k = ThisPlugin.getPlugin().getConfig().getInt("server-id");
        String header = ChatColor.translateAlternateColorCodes('&', ThisPlugin.getPlugin().getConfig().getString("header"));
        String footer = ChatColor.translateAlternateColorCodes('&', ThisPlugin.getPlugin().getConfig().getString("footer").replace("%id%", String.valueOf(k)));
        if(BaseArena.states == BaseArena.ArenaStates.Countdown){
            Main.WaitingPlayers.add(p);
            e.setJoinMessage(Main.prefix + "§e" + p.getName() + " §bhas joined the queue!");
            p.getInventory().clear();
            p.getInventory().setItem(8, Inventories.itemAPI(Material.GLOWSTONE_DUST, "§bReturn to hub", null));
            p.teleport(SetLobby.getLobby());
            p.setFireTicks(0);
        }
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try{
                out.writeUTF("Connect");
                out.writeUTF("sglobby");
            }catch (IOException ei){

            }
            p.sendPluginMessage(ThisPlugin.getPlugin(), "BungeeCord", b.toByteArray());
        }
        SendCoolMessages.TabHeaderAndFooter("", "", p);
        SendCoolMessages.TabHeaderAndFooter(header, footer, p);
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(Main.PlayingPlayers.contains(p)){
            Main.PlayingPlayers.remove(p);
        }
        if(Main.WaitingPlayers.contains(p)){
            Main.WaitingPlayers.remove(p);
        }
        e.setQuitMessage(null);
    }
    @EventHandler
    public void stopMove(PlayerMoveEvent e){
        if(WaitingCountdown.timuntilstart > 0){
            Player p = e.getPlayer();
            Location f = e.getFrom();
            Location t = e.getTo();
            if ((Main.PlayingPlayers.contains(p))){

            if((f.getBlockX() != t.getBlockX()) || (f.getBlockY() != t.getBlockY()) || (f.getBlockZ() != t.getBlockZ())) {
                e.setTo(f);
            }
        }
        }
    }
    @EventHandler
    public void cancelDamage(EntityDamageEvent e){
        Entity entity = e.getEntity();
        if(entity instanceof Player){
            Player p = (Player) entity;
            if(Main.WaitingPlayers.contains(p)){
                e.setCancelled(true);
            }
            if(Main.PlayingPlayers.contains(p)){
                if(GracePeriodCountDown.timeuntilstart > 0){
                    if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                            e.setCancelled(true);
                    }
                }
                if(GracePeriodCountDown.timeuntilstart < 0){
                    e.setCancelled(false);
                }
            }
        }
    }
    @EventHandler
    public void playerDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        Player ppe = p.getKiller();
        final CraftPlayer craftPlayer = (CraftPlayer) p;
        Main.PlayingPlayers.remove(p);
        World world = p.getWorld();
        Location location = p.getLocation();
        world.strikeLightningEffect(location);

        Deaths.firstDeath(p);
        Deaths.addDeaths(p);
        Kills.firstKill(ppe);
        Kills.addKills(ppe);

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try{
                out.writeUTF("Connect");
                out.writeUTF("sglobby");
            }catch (IOException ei){

            }
            p.sendPluginMessage(ThisPlugin.getPlugin(), "BungeeCord", b.toByteArray());

        ThisPlugin.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(p.isDead()){
                    craftPlayer.getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                }
            }
        });
        for(Player pp : Main.PlayingPlayers){
            pp.sendMessage(Main.prefix + "§aPlayer: " + p.getName() + " §6has been killed by §c" + p.getKiller().getName());
        }
    }
    @EventHandler
    public void cancelBreaking(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(Main.WaitingPlayers.contains(p)) {
                e.setCancelled(true);
        }
        if(Main.PlayingPlayers.contains(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void cancelBlockPlacing(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(Main.WaitingPlayers.contains(p)) {
                e.setCancelled(true);
        }
        if(Main.PlayingPlayers.contains(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void itemInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action action = e.getAction();
        if(Main.WaitingPlayers.contains(p)){
            if(action == Action.RIGHT_CLICK_AIR && p.getItemInHand().getType() == Material.GLOWSTONE_DUST){
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                try{
                    out.writeUTF("Connect");
                    out.writeUTF("sglobby");

                }catch(IOException ei){

                }
                p.sendPluginMessage(ThisPlugin.getPlugin(), "BungeeCord", b.toByteArray());
            }
        }
    }
    @EventHandler
    public void cancelDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(Main.WaitingPlayers.contains(p)){
            if(!p.hasPermission("sg.drop")){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void moveInventory(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(Main.WaitingPlayers.contains(p)){
            if(!p.hasPermission("sg.moveitem")){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void foodChange(FoodLevelChangeEvent e){
        Player p = (Player) e.getEntity();
        if(Main.WaitingPlayers.contains(p)){
            e.setCancelled(true);
        }
    }
}
