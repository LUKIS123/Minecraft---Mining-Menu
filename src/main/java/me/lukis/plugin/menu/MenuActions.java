package me.lukis.plugin.menu;

import me.lukis.plugin.database.SettingsRepository;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
    private final SettingsRepository settingsRepository;

    public MenuActions(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent inventoryClickEvent) {

        Player player = (Player) inventoryClickEvent.getWhoClicked();
        Inventory inventory = inventoryClickEvent.getClickedInventory();
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        ClickType clickType = inventoryClickEvent.getClick();

        CustomInventory customInventory = new CustomInventory(settingsRepository, player);

        if (inventory == null) {
            return;
        }

        PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
        if (plainTextComponentSerializer.serialize(inventoryClickEvent.getView().title()).equalsIgnoreCase("Select your drop:")) {
            inventoryClickEvent.setCancelled(true);

            if (itemStack == null || !itemStack.hasItemMeta()) {
                return;
            }
            updatePlayerSettings(player, itemStack, clickType);

            // refreshing the inventory menu
            customInventory.refreshInventoryMenu(inventory, player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS));
        }
    }

    private void updatePlayerSettings(@NotNull Player player, @NotNull ItemStack itemStack, @NotNull ClickType clickType) {
        settingsRepository.getPlayerSettings(player.getName()).setDrop(itemStack.getType(), !clickType.isRightClick());
    }
}