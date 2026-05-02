package com.jakehasskill.screenshot_relocator.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent ->  {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.literal("Screenshot Relocator Settings"));

            ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            general.addEntry(entryBuilder.startStrField(Component.literal("Screenshot Folder Path"), ConfigManager.config.customScreenshotPath)
                    .setDefaultValue("")
                    .setTooltip(Component.literal("Absolute path for where screenshots are saved. Leave blank for your OS default."))
                    .setSaveConsumer(newValue -> ConfigManager.config.customScreenshotPath = newValue)
                    .build());

            builder.setSavingRunnable(ConfigManager::saveConfig);

            return builder.build();
        };
    }
}
