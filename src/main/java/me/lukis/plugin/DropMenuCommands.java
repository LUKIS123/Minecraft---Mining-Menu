package me.lukis.plugin;

import me.lukis.plugin.database.SettingsRepository;
import me.lukis.plugin.menu.CustomInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class DropMenuCommands implements Listener, CommandExecutor {

    private final SettingsRepository settingsRepository;

    public DropMenuCommands(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            CustomInventory customInventory = new CustomInventory(settingsRepository, player);

            // checking if player has fortune enchantment and opening inventory
            player.openInventory(customInventory.createInventoryMenu(player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)));

        } else {
            sender.sendMessage(Component.text("Only players can use this command!").color(NamedTextColor.RED));
        }
        return true;
    }
}