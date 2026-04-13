package com.jakehasskill.screenshot_relocator.mixin;

import com.jakehasskill.screenshot_relocator.client.ConfigManager;
import com.jakehasskill.screenshot_relocator.client.ScreenshotRelocatorClient;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(ScreenshotRecorder.class)
public class ScreenshotMixin {
    @Inject(method = "getScreenshotFilename", at = @At("HEAD"), cancellable = true)
    private static void redirectScreenshotFolder(File directory, CallbackInfoReturnable<File> cir) {
        String customPath = ConfigManager.config.customScreenshotPath;
        File customDir = new File(customPath);

        if (!customDir.exists()) {
            customDir.mkdirs();
        }

        String time = Util.getFormattedCurrentTime();
        int i = 1;
        File file;
        while ((file = new File(customDir, time + (i == 1 ? "" : "_" + i) + ".png")).exists()) {
            ++i;
        }

        cir.setReturnValue(file);
    }
}
