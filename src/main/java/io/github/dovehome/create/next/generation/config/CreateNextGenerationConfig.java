package io.github.dovehome.create.next.generation.config;


import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class CreateNextGenerationConfig {
    public static final ForgeConfigSpec GENERAL_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }
    private static void setupConfig(ForgeConfigSpec.Builder builder) {

    }
}
