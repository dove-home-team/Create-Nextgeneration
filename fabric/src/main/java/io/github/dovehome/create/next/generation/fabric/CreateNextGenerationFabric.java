package io.github.dovehome.create.next.generation.fabric;

import com.mojang.logging.LogUtils;
import io.github.dovehome.create.next.generation.CreateNextGeneration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateNextGenerationFabric extends CreateNextGeneration implements ModInitializer {

    @Override
    public void onInitialize() {
        init();
    }
}
