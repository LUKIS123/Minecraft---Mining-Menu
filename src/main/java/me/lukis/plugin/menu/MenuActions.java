package me.lukis.plugin.menu;

import me.lukis.plugin.Plugin;
import me.lukis.plugin.database.SettingsRepository;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MenuActions implements Listener {
    private final SettingsRepository repo;
    private final Plugin plugin;

    public MenuActions(SettingsRepository repo, Plugin plugin) {
        this.repo = repo;
        this.plugin = plugin;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent inventoryClickEvent) {

        Player player = (Player) inventoryClickEvent.getWhoClicked();
        Inventory inventory = inventoryClickEvent.getClickedInventory();
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        ClickType clickType = inventoryClickEvent.getClick();

        CustomInventory customInventory = new CustomInventory(plugin, repo, player);

        if (inventory == null) {
            return;
        }

        if (inventoryClickEvent.getView().title().equals(Component.text(ChatColor.DARK_PURPLE + "Select your drop:"))) {
            inventoryClickEvent.setCancelled(true);

            if (itemStack == null || !itemStack.hasItemMeta()) {
                return;
            }

            updatePlayerSettings(player, itemStack, clickType);

            // refreshing the inventory menu
            if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
                customInventory.getUpdatedInventory(inventory, player.getInventory().getItemInMainHand().getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS) + 1);
            } else {
                customInventory.getUpdatedInventory(inventory, 1);
            }
        }
    }

    private void updatePlayerSettings(@NotNull Player player, @NotNull ItemStack itemStack, @NotNull ClickType clickType) {
        repo.getPlayerSettings(player.getName()).setPlayerSetting(itemStack.getType(), !clickType.isRightClick());
    }
}