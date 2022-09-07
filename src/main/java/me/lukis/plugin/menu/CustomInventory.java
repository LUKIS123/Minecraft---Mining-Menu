package me.lukis.plugin.menu;

import me.lukis.plugin.Plugin;
import me.lukis.plugin.database.ItemData;
import me.lukis.plugin.database.SettingsRepository;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CustomInventory implements Listener {
    private final Plugin plugin;
    private final SettingsRepository repo;
    private final Player player;

    public CustomInventory(Plugin plugin, SettingsRepository repo, Player player) {
        this.plugin = plugin;
        this.repo = repo;
        this.player = player;
    }

    public Inventory createInventoryMenu(int fortuneRatio) {
        // getting drop chances
        Map<Material, Double> itemInfo = ItemData.getItemInfo(fortuneRatio);

        Inventory inventory = plugin.getServer().createInventory(null, 9,
                Component.text(ChatColor.DARK_PURPLE + "Select your drop:"));

        int i = itemInfo.size() - 1;
        for (Map.Entry<Material, Double> entry : itemInfo.entrySet()) {

            ItemMeta meta = createSlot(new ItemStack(entry.getKey()), entry.getValue(), fortuneRatio);
            ItemStack itemStack = new ItemStack(entry.getKey());
            itemStack.setItemMeta(meta);

            inventory.setItem(i, itemStack);
            i--;
        }
        return inventory;
    }

    private @NotNull ItemMeta createSlot(ItemStack material, Double chance, int fortuneRatio) {
        ItemMeta itemMeta = material.getItemMeta();
        itemMeta.displayName(Component.empty());

        String status;
        String note;
        String bonus = "chances x " + (fortuneRatio + 1);
        if (repo.getPlayerSettings(player.getName()).getSetting(material.getType())) {
            status = ChatColor.GREEN + "Enabled";
            note = "Right-Click to disable";
        } else {
            status = ChatColor.RED + "Disabled";
            note = "Left-Click to enable";
        }
        itemMeta.lore(List.of(Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Drop: ").color(NamedTextColor.GRAY))
                        .append(Component.text(material.getType().toString()).color(NamedTextColor.GRAY)),
                Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Chance: ").color(NamedTextColor.GRAY))
                        .append(Component.text(chance * 100 + "%").color(NamedTextColor.GOLD)),
                Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Status: ").color(NamedTextColor.GRAY))
                        .append(Component.text(status).decoration(TextDecoration.BOLD, true)),
                Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(note).color(NamedTextColor.LIGHT_PURPLE)),
                Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Fortune bonus:").color(NamedTextColor.GRAY)),
                Component.text(" \u27a5 ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(bonus).color(NamedTextColor.GOLD)),
                Component.empty()));

        return itemMeta;
    }

    // updating the inventory menu
    public void refreshInventoryMenu(@NotNull Inventory inventory, Integer fortuneRatio) {
        inventory.clear();
        inventory.setContents(this.createInventoryMenu(fortuneRatio).getContents());
    }
}