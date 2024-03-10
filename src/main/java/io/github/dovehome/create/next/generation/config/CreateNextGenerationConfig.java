package io.github.dovehome.create.next.generation.config;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

public class CreateNextGenerationConfig {
    public static final ForgeConfigSpec GENERAL_SPEC;


    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static boolean isValidItem(Object obj) {
        String item = obj.toString();
        if (!ResourceLocation.isValidResourceLocation(item)) {
            return false;
        }
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(item));
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {



    }
}
