package me.lukis.plugin;

import me.lukis.plugin.database.PlayerDropSettings;
import me.lukis.plugin.database.SettingsRepository;
import me.lukis.plugin.events.BlockBreak;
import me.lukis.plugin.events.PlayerJoin;
import me.lukis.plugin.menu.InventoryMenuClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MiningMenu extends JavaPlugin {

    private SettingsRepository settingsRepository;
    private DropMenuCommands dropMenuCommands;

    @Override
    public void onEnable() {
        settingsRepository = new SettingsRepository();
        dropMenuCommands = new DropMenuCommands(settingsRepository);

        getCommand("drop").setExecutor(dropMenuCommands);

        getServer().getPluginManager().registerEvents(new InventoryMenuClickEvent(settingsRepository), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(settingsRepository), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(settingsRepository), this);
    }

    @Override
    public void onLoad() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            settingsRepository.addPlayerSettings(player.getName(), new PlayerDropSettings());
            player.sendMessage(ChatColor.RED + "Drop settings have been set to default!");
        });
    }
}