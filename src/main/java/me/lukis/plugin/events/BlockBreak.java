package me.lukis.plugin.events;

import me.lukis.plugin.database.ItemData;
import me.lukis.plugin.database.PlayerSettings;
import me.lukis.plugin.database.SettingsRepository;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BlockBreak implements Listener {
    private final SettingsRepository repo;
    public Map<Material, Double> chances;
    private final Random random = new Random();
    private final Set<Material> stoneLike = new HashSet<>();

    public BlockBreak(SettingsRepository repo) {
        this.repo = repo;

        stoneLike.add(Material.STONE);
        stoneLike.add(Material.DEEPSLATE);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // setting drop chances
        chances = ItemData.getItemInfo(player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS));

        if (stoneLike.contains(block.getType()) &&
                player.getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe")) {

            // deleting drop
            event.setDropItems(false);
            // getting player settings
            PlayerSettings playerSettings = repo.getPlayerSettings(player.getName());

            for (Map.Entry<Material, Double> entry : chances.entrySet()) {
                if (playerSettings.getSetting(entry.getKey()) && (random.nextInt(1000) + 1) / 1000.0 <= entry.getValue()) {

                    if (entry.getKey().equals(Material.COBBLESTONE)) {
                        dropItem(player, block, block.getDrops().iterator().next());
                    } else {
                        dropItem(player, block, new ItemStack(entry.getKey()));
                    }
                    break;
                }
            }
        }
    }

    private void dropItem(@NotNull Player player, Block block, ItemStack itemStack) {
        Map<Integer, ItemStack> rejectedItems = player.getInventory().addItem(itemStack);
        if (!rejectedItems.isEmpty()) {
            block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
        }
    }
}