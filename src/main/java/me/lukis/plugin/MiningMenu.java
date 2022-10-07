package me.lukis.plugin;

import me.lukis.plugin.database.models.PlayerDropSettings;
import me.lukis.plugin.database.repositories.SettingsRepository;
import me.lukis.plugin.events.BlockBreak;
import me.lukis.plugin.events.PlayerJoin;
import me.lukis.plugin.menu.InventoryMenuClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MiningMenu extends JavaPlugin {

    private final SettingsRepository settingsRepository = new SettingsRepository();

    @Override
    public void onEnable() {
        DropMenuCommands dropMenuCommands = new DropMenuCommands(settingsRepository);

        getCommand("drop").setExecutor(dropMenuCommands);

        getServer().getPluginManager().registerEvents(new InventoryMenuClickEvent(settingsRepository), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(settingsRepository), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(settingsRepository), this);
    }

    @Override
    public void onDisable() {
        settingsRepository.writeDataToJson();
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + getPlugin(this.getClass()).getName() + "is saving the players setting!");
    }

//    @Override
//    public void onLoad() {
//        Bukkit.getOnlinePlayers().forEach(player -> {
//            settingsRepository.addPlayerSettings(player.getName(), new PlayerDropSettings());
//            player.sendMessage(ChatColor.RED + "Drop settings have been set to default!");
//        });
//    }

    @Override
    public void onLoad() {
        settingsRepository.readDataFromJson();

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (settingsRepository.getPlayerSettings(player.getName()) == null) {
                settingsRepository.addPlayerSettings(player.getName(), new PlayerDropSettings());
                player.sendMessage(ChatColor.RED + "Drop settings have been set to default!");
            }
        });
    }

}