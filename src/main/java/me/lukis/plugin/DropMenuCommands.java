package me.lukis.plugin;

import me.lukis.plugin.database.SettingsRepository;
import me.lukis.plugin.menu.CustomInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class DropMenuCommands implements Listener, CommandExecutor {
    public final String cmd1 = "drop";
    private final Plugin plugin;
    private final SettingsRepository repo;

    public DropMenuCommands(Plugin plugin, SettingsRepository repo) {
        this.plugin = plugin;
        this.repo = repo;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CustomInventory i = new CustomInventory(plugin, repo, player);

            // checking if player has fortune enchantment and opening inventory
            player.openInventory(i.createInventoryMenu(player.getInventory().getItemInMainHand().getEnchantments().getOrDefault(Enchantment.LOOT_BONUS_BLOCKS, 0)));

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
        }
        return true;
    }
}