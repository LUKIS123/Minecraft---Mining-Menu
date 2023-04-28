package me.lukis.plugin.eventlisteners;

import me.lukis.plugin.data.models.PlayerDropSettings;
import me.lukis.plugin.data.repositories.SettingsRepository;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final SettingsRepository settingsRepository;

    public PlayerJoinListener(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        if (settingsRepository.getPlayerSettings(playerJoinEvent.getPlayer().getName()) == null) {
            settingsRepository.addPlayerSettings(playerJoinEvent.getPlayer().getName(), new PlayerDropSettings());
            playerJoinEvent.getPlayer().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&6Your &cdrop settings &6have been set to default!"));
        }
    }
}