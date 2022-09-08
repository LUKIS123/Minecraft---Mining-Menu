package me.lukis.plugin;

import me.lukis.plugin.database.PlayerSettings;
import me.lukis.plugin.database.SettingsRepository;
import me.lukis.plugin.events.BlockBreak;
import me.lukis.plugin.events.PlayerJoin;
import me.lukis.plugin.menu.InventoryMenuClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    private final SettingsRepository settingsRepository = new SettingsRepository();
    private final DropMenuCommands dropMenuCommands = new DropMenuCommands(this, settingsRepository);

    @Override
    public void onEnable() {
        getCommand("drop").setExecutor(dropMenuCommands);

        getServer().getPluginManager().registerEvents(new InventoryMenuClickEvent(settingsRepository, this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(settingsRepository), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(settingsRepository), this);
    }

    @Override
    public void onLoad() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            settingsRepository.addPlayerSettings(player.getName(), new PlayerSettings());
            player.sendMessage(ChatColor.RED + "Drop settings have been set to default!");
        });
    }
}