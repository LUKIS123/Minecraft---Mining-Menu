package me.lukis.plugin.events;

import me.lukis.plugin.database.models.PlayerDropSettings;
import me.lukis.plugin.database.repositories.SettingsRepository;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoin implements Listener {
    private final SettingsRepository settingsRepository;

    public PlayerJoin(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent playerJoinEvent) {
        if (settingsRepository.getPlayerSettings(playerJoinEvent.getPlayer().getName()) == null) {
            settingsRepository.addPlayerSettings(playerJoinEvent.getPlayer().getName(), new PlayerDropSettings());
            playerJoinEvent.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Your drop settings have been set to default!");
        }
    }
}