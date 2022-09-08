package me.lukis.plugin.database;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class PlayerDropSettings {

    private final Map<Material, Boolean> dropSettings = new HashMap<>();

    public PlayerDropSettings() {
        dropSettings.put(Material.COBBLESTONE, true);
        dropSettings.put(Material.FLINT, false);
        dropSettings.put(Material.COAL, false);
        dropSettings.put(Material.REDSTONE, false);
        dropSettings.put(Material.LAPIS_LAZULI, false);
        dropSettings.put(Material.IRON_INGOT, false);
        dropSettings.put(Material.GOLD_INGOT, false);
        dropSettings.put(Material.DIAMOND, false);
        dropSettings.put(Material.EMERALD, false);
    }

    public boolean getDrop(Material material) {
        return dropSettings.getOrDefault(material, false);
    }

    public void setDrop(Material material, boolean enabled) {
        dropSettings.replace(material, enabled);
    }
}