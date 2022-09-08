package me.lukis.plugin.events;

import me.lukis.plugin.database.PlayerDropSettings;
import me.lukis.plugin.database.SettingsRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoin implements Listener {
    private final SettingsRepository settingsRepository;

    public PlayerJoin(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent playerJoinEvent) {
        settingsRepository.addPlayerSettings(playerJoinEvent.getPlayer().getName(), new PlayerDropSettings());
    }
}