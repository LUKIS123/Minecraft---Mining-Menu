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
    private final Plugin plugin;
    private final SettingsRepository repo;

    public DropMenuCommands(Plugin plugin, SettingsRepository repo) {
        this.plugin = plugin;
        this.repo = repo;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            CustomInventory i = new CustomInventory(plugin, repo, player);

            // checking if player holds item with fortune enchantment and opening inventory
            player.openInventory(i.createInventoryMenu(player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)));

        } else {
            sender.sendMessage(Component.text("Only players can use this command!").color(NamedTextColor.RED));
        }
        return true;
    }
}