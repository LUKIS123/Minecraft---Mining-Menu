package me.lukis.plugin.database.repositories;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.lukis.plugin.database.models.PlayerDropSettings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SettingsRepository {
    private Map<String, PlayerDropSettings> playerSettings = new HashMap<>();
    File file = new File("player-drop-settings.json");
    private final Gson gson = new Gson();

    public void addPlayerSettings(String name, PlayerDropSettings playerDropSettings) {
        playerSettings.put(name, playerDropSettings);
    }

    public PlayerDropSettings getPlayerSettings(String name) {
        return playerSettings.get(name);
    }

    private void createFileIfNotPresent(boolean isPresent) {
        if (!isPresent) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeDataToJson() {
        createFileIfNotPresent(file.exists());

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(playerSettings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Unchecked")
    public void readDataFromJson() {
        Type mapType = new TypeToken<Map<String, PlayerDropSettings>>() {}.getType();
        try (FileReader reader = new FileReader(file)) {
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