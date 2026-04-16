package com.jakehasskill.screenshot_relocator.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ConfigManager {
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("screenshot_relocator.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static ConfigData config = new ConfigData();

    public static class ConfigData {
        public String customScreenshotPath = "";
    }

    public static void loadConfig() {
        if (Files.exists(CONFIG_FILE)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                ScreenshotRelocatorClient.LOGGER.error("Failed to load screenshot reloader config!", e);
                return;
            }
            if (Objects.equals(config.customScreenshotPath, getDefaultOsPath())) {
                config.customScreenshotPath = "";
                saveConfig();
            }
        } else {
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            ScreenshotRelocatorClient.LOGGER.error("Failed to save screenshot reloader config!", e);
        }
    }

    public static String getEffectivePath() {
        if (config.customScreenshotPath == null || config.customScreenshotPath.trim().isEmpty()) {
            return getDefaultOsPath();
        }
        return config.customScreenshotPath;
    }

    private static String getDefaultOsPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (os.contains("win")) {
            return userHome + "\\Pictures\\Screenshots\\Minecraft";
        } else if (os.contains("mac")) {
            return userHome + "/Desktop/Minecraft Screenshots";
        } else {
            return userHome + "/Pictures/Screenshots/Minecraft";
        }
    }
}
