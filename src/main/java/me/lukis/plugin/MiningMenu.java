package me.lukis.plugin;

import me.lukis.plugin.data.models.PlayerDropSettings;
import me.lukis.plugin.data.repositories.SettingsRepository;
import me.lukis.plugin.eventlisteners.BlockBreakListener;
import me.lukis.plugin.eventlisteners.PlayerJoinListener;
import me.lukis.plugin.menu.InventoryMenuClickEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MiningMenu extends JavaPlugin {
    private final SettingsRepository settingsRepository = new SettingsRepository("player-drop-settings.json");

    @Override
    public void onEnable() {
        DropMenuCommandExecutor dropMenuCommandExecutor = new DropMenuCommandExecutor(settingsRepository);
        getCommand("drop").setExecutor(dropMenuCommandExecutor);

        getServer().getPluginManager().registerEvents(new InventoryMenuClickEvent(settingsRepository), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(settingsRepository), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(settingsRepository), this);
    }

    @Override
    public void onDisable() {
        settingsRepository.writeData();
        getLogger().info("Saving the players settings!");
    }

    @Override
    public void onLoad() {
        settingsRepository.readData();
        getLogger().info("Loading the players settings!");

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (settingsRepository.getPlayerSettings(player.getName()) == null) {
                settingsRepository.addPlayerSettings(player.getName(), new PlayerDropSettings());
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&6Your &cdrop &6settings have been set to default!"));
            }
        });
    }
}