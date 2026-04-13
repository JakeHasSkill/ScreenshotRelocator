package com.jakehasskill.screenshot_relocator.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent ->  {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("Screenshot Relocator Settings"));

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            general.addEntry(entryBuilder.startStrField(Text.literal("Screenshot Folder Path"), ConfigManager.config.customScreenshotPath)
                    .setDefaultValue("")
                    .setTooltip(Text.literal("Absolute path for where screenshots are saved."))
                    .setSaveConsumer(newValue -> {
                        ConfigManager.config.customScreenshotPath = newValue;
                    })
                    .build());

            builder.setSavingRunnable(ConfigManager::saveConfig);

            return builder.build();
        };
    }
}
