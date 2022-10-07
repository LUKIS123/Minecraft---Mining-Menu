package me.lukis.plugin.database.repositories;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.lukis.plugin.database.models.PlayerDropSettings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SettingsRepository {
    private Map<String, PlayerDropSettings> playerSettings = new HashMap<>();
    private final String jsonDatabaseDirectory = "./database/playerSettings.json";
    private final Gson gson = new Gson();

    public void addPlayerSettings(String name, PlayerDropSettings playerDropSettings) {
        playerSettings.put(name, playerDropSettings);
    }

    public PlayerDropSettings getPlayerSettings(String name) {
        return playerSettings.get(name);
    }

    // added
    public Map<String, PlayerDropSettings> getPlayersSettings() {
        return playerSettings;
    }

    public void writeDataToJson() {
        try (FileWriter writer = new FileWriter(jsonDatabaseDirectory)) {
            gson.toJson(playerSettings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Unchecked")
    public void readDataFromJson() {
        Type mapType = new TypeToken<Map<String, PlayerDropSettings>>() {}.getType();
        try (FileReader reader = new FileReader(jsonDatabaseDirectory)) {
            Map<String, PlayerDropSettings> readMap = gson.fromJson(reader, mapType);
            if (playerSettings != null) {
                playerSettings.putAll(readMap);
            } else {
                playerSettings = readMap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}