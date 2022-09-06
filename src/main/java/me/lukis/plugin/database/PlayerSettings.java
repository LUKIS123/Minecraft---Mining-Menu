package me.lukis.plugin.database;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class PlayerSettings {

    private final Map<Material, Boolean> playerSettings = new HashMap<>();

    public PlayerSettings() {
        playerSettings.put(Material.COBBLESTONE, true);
        playerSettings.put(Material.FLINT, false);
        playerSettings.put(Material.COAL, false);
        playerSettings.put(Material.REDSTONE, false);
        playerSettings.put(Material.LAPIS_LAZULI, false);
        playerSettings.put(Material.IRON_INGOT, false);
        playerSettings.put(Material.GOLD_INGOT, false);
        playerSettings.put(Material.DIAMOND, false);
        playerSettings.put(Material.EMERALD, false);
    }

    public boolean getSetting(Material material) {
        return playerSettings.getOrDefault(material, false);
    }

    public void setSetting(Material material, boolean enabled) {
        playerSettings.replace(material, enabled);
    }

    @Override
    public String toString() {
        return "Settings{" + "playerSettings=" + playerSettings + '}';
    }
}