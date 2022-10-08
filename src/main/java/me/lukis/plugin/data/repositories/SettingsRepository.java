package me.lukis.plugin.data.repositories;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.lukis.plugin.data.models.PlayerDropSettings;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SettingsRepository {
    private final Map<String, PlayerDropSettings> playerSettings = new HashMap<>();
    private final File file;

    public SettingsRepository(String filename) {
        this.file = new File(filename);
    }

    private final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();

    public void addPlayerSettings(String name, PlayerDropSettings playerDropSettings) {
        playerSettings.put(name, playerDropSettings);
    }

    public PlayerDropSettings getPlayerSettings(String name) {
        return playerSettings.get(name);
    }

    public void save() {
        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            gson.toJson(playerSettings, writer);
            writer.close();
        } catch (IOException e) {
            Bukkit.getLogger().severe("MiningMenu could not save players drop settings!");
        }
    }

    @SuppressWarnings("Unchecked")
    public void load() {
        Type mapType = new TypeToken<Map<String, PlayerDropSettings>>() {}.getType();
        try (FileReader reader = new FileReader(file)) {
            playerSettings.putAll(gson.fromJson(reader, mapType));
        } catch (IOException e) {
            Bukkit.getLogger().severe("MiningMenu could not load players drop settings!");
        }
    }
}