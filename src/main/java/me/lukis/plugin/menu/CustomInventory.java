package me.lukis.plugin.menu;

import me.lukis.plugin.data.models.DropChances;
import me.lukis.plugin.data.repositories.SettingsRepository;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
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
    private final SettingsRepository settingsRepository;
    private final Player player;

    public CustomInventory(SettingsRepository settingsRepository, Player player) {
        this.settingsRepository = settingsRepository;
        this.player = player;
    }

    public Inventory createInventoryMenu(int fortuneRatio) {
        // getting drop chances
        Map<Material, Double> itemInfo = DropChances.getItemInfo(fortuneRatio);

        Inventory inventory = Bukkit.getServer().createInventory(null, 9,
                Component.text("Select your drop:").color(NamedTextColor.DARK_PURPLE));

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

        Component status, note;
        Component bonus = Component.text("x" + (fortuneRatio + 1)).color(NamedTextColor.GOLD);

        if (settingsRepository.getPlayerSettings(player.getName()).getDrop(material.getType())) {
            status = Component.text("Enabled").color(NamedTextColor.GREEN);
            note = Component.text("Right-Click to disable");
        } else {
            status = Component.text("Disabled").color(NamedTextColor.RED);
            note = Component.text("Left-Click to enable");
        }

        itemMeta.lore(List.of(Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Drop: ").color(NamedTextColor.GRAY))
                        .append(Component.text(material.getType().toString()).color(NamedTextColor.GRAY)),
                Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Chance: ").color(NamedTextColor.GRAY))
                        .append(Component.text(chance * 100 + "%").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true)),
                Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Fortune multiplier: ").color(NamedTextColor.GRAY))
                        .append(bonus),
                Component.text(" \u00bb ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Status: ").color(NamedTextColor.GRAY))
                        .append(status.decoration(TextDecoration.BOLD, true)),
                Component.text(" \u27a5 ").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(note.color(NamedTextColor.LIGHT_PURPLE)),
                Component.empty()));

        return itemMeta;
    }

    // updating the inventory menu
    public void refreshInventoryMenu(@NotNull Inventory inventory, Integer fortuneRatio) {
        inventory.clear();
        inventory.setContents(this.createInventoryMenu(fortuneRatio).getContents());
    }
}