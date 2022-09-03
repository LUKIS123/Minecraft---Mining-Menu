package me.lukis.plugin.events;

import me.lukis.plugin.database.PlayerSettings;
import me.lukis.plugin.database.SettingsRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
    private final SettingsRepository repo;

    public PlayerJoinEvent(SettingsRepository repo) {
        this.repo = repo;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent playerJoinEvent) {
        repo.addPlayerSettings(playerJoinEvent.getPlayer().getName(), new PlayerSettings());
    }
}