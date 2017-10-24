package com.tatemylove.SurvivalGames.Inventories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Inventories {
    public static ItemStack itemAPI(Material m, String name, ArrayList<String> lore){
        ItemStack i = new ItemStack(m, 1);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }

}
