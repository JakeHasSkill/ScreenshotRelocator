package com.jakehasskill.screenshot_relocator.client;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenshotRelocatorClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("screenshot_relocator");

    @Override
    public void onInitializeClient() {
        ConfigManager.loadConfig();
        LOGGER.info("Screenshot Relocator Path: {}", ConfigManager.config.customScreenshotPath);
    }
}
