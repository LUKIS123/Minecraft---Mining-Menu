package me.lukis.plugin.events;

import me.lukis.plugin.database.DropChances;
import me.lukis.plugin.database.PlayerDropSettings;
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
    private final SettingsRepository settingsRepository;
    private final Set<Material> stoneLikeBlocks = new HashSet<>();

    public BlockBreak(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;

        stoneLikeBlocks.add(Material.STONE);
        stoneLikeBlocks.add(Material.DEEPSLATE);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // setting drop chances
        Map<Material, Double> dropChances = DropChances.getItemInfo(player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS));

        if (stoneLikeBlocks.contains(block.getType())
                && player.getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe")
                && !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)
        ) {

            // deleting drop
            event.setDropItems(false);
            // getting player settings
            PlayerDropSettings playerDropSettings = settingsRepository.getPlayerSettings(player.getName());

            for (Map.Entry<Material, Double> entry : dropChances.entrySet()) {
                if (playerDropSettings.getDrop(entry.getKey()) && (new Random().nextInt(1000) + 1) / 1000.0 <= entry.getValue()) {

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