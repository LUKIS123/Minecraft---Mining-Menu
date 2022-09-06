package me.lukis.plugin.database;

import java.util.HashMap;
import java.util.Map;

public class SettingsRepository {
    private final Map<String, PlayerSettings> settingsMap = new HashMap<>();

    public void addPlayerSettings(String name, PlayerSettings playerSettings) {
        settingsMap.put(name, playerSettings);
    }

    public PlayerSettings getPlayerSettings(String name) {
        return settingsMap.get(name);
    }
}