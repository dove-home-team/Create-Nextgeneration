package io.github.dovehome.create.next.generation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.latvian.mods.kubejs.KubeJSPaths;
import io.github.dovehome.create.next.generation.config.BlazeBurnerConfig;
import io.github.dovehome.create.next.generation.config.CreateNextGenerationConfig;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final Gson gson = new GsonBuilder().disableJdkUnsafe().setPrettyPrinting().setLenient().create();
    public static final List<CreateNextGenerationConfig<?>> loadedConfigs = new ArrayList<>();
    public static final String MODID = "createnextgeneration";

    public static final Path cgfDir = KubeJSPaths.dir(KubeJSPaths.DIRECTORY.resolve("preInit").resolve("creative-generation"), true);
    public static final Path blazeBurnerPath = cgfDir.resolve("blaze-level.json");

    public static class Config {
        public static final CreateNextGenerationConfig<BlazeBurnerConfig> blazeBurner = new CreateNextGenerationConfig<>(
                blazeBurnerPath, BlazeBurnerConfig.class,
                new BlazeBurnerConfig()
                        .addItemTag("INFINITY")
                        .addFuel("INFINITY")
                        .addFlame("INFINITY")
        );
    }
}
