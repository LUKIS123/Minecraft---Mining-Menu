package me.lukis.plugin.data.models;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class DropChances {
    private static final Map<Integer, Map<Material, Double>> dropChances = new LinkedHashMap<>();

    public static @NotNull Map<Material, Double> getItemInfo(int dropMultiplier) {

        dropMultiplier++;

        if (!dropChances.containsKey(dropMultiplier)) {
            Map<Material, Double> drop = new LinkedHashMap<>();

            drop.put(Material.EMERALD, 0.005 * (double) dropMultiplier);
            drop.put(Material.DIAMOND, 0.01 * (double) dropMultiplier);
            drop.put(Material.GOLD_INGOT, 0.02 * (double) dropMultiplier);
            drop.put(Material.IRON_INGOT, 0.05 * (double) dropMultiplier);
            drop.put(Material.LAPIS_LAZULI, 0.05 * (double) dropMultiplier);
            drop.put(Material.REDSTONE, 0.05 * (double) dropMultiplier);
            drop.put(Material.COAL, 0.1 * (double) dropMultiplier);
            drop.put(Material.FLINT, 0.25 * (double) dropMultiplier);
            drop.put(Material.COBBLESTONE, 1.0);

            dropChances.put(dropMultiplier, ImmutableMap.copyOf(drop));
        }

        // ratio >> 1,2,3,4
        return dropChances.get(dropMultiplier);
    }
}