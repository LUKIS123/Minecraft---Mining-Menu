package me.lukis.plugin.database;

import java.util.HashMap;
import java.util.Map;

public class SettingsRepository {
    private final Map<String, PlayerDropSettings> playerSettings = new HashMap<>();

    public void addPlayerSettings(String name, PlayerDropSettings playerDropSettings) {
        playerSettings.put(name, playerDropSettings);
    }

    public PlayerDropSettings getPlayerSettings(String name) {
        return playerSettings.get(name);
    }
}