package me.lukis.plugin.database;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemData {
    private static final Map<Material, Double> itemInfo = new LinkedHashMap<>();

    public static @NotNull Map<Material, Double> getItemInfo(int fortuneRatio) {
        fortuneRatio += 1;

        itemInfo.put(Material.EMERALD, 0.005 * (double) fortuneRatio);
        itemInfo.put(Material.DIAMOND, 0.01 * (double) fortuneRatio);
        itemInfo.put(Material.GOLD_INGOT, 0.02 * (double) fortuneRatio);
        itemInfo.put(Material.IRON_INGOT, 0.05 * (double) fortuneRatio);
        itemInfo.put(Material.LAPIS_LAZULI, 0.05 * (double) fortuneRatio);
        itemInfo.put(Material.REDSTONE, 0.05 * (double) fortuneRatio);
        itemInfo.put(Material.COAL, 0.1 * (double) fortuneRatio);
        itemInfo.put(Material.FLINT, 0.25 * (double) fortuneRatio);
        itemInfo.put(Material.COBBLESTONE, 1.0);
        // ratio >> 1,2,3,4
        return new LinkedHashMap<>(itemInfo);
    }
}