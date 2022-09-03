package me.lukis.plugin.database;

import java.util.HashMap;
import java.util.Map;

public class SettingsRepository {
    private final Map<String, PlayerSettings> settingsMap = new HashMap<>();

    public Map<String, PlayerSettings> getSettingsMap() {
        return settingsMap;
    }

    public void addPlayerSettings(String name, PlayerSettings playerSettings) {
        settingsMap.put(name, playerSettings);
    }

    public PlayerSettings getPlayerSettings(String name) {
        return settingsMap.get(name);
    }

    public String settingsToString(String name) {
        return name + " " + getPlayerSettings(name).toString();
    }
}