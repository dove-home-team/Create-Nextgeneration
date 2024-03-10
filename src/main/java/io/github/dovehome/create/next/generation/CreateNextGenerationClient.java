package io.github.dovehome.create.next.generation;

import io.github.dovehome.create.next.generation.client.CNGPartialModels;
import io.github.dovehome.create.next.generation.client.CNGSpriteShifts;
import io.github.dovehome.create.next.generation.particles.CNGParticleTypes;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CreateNextGenerationClient {

    public static void onCtorClient() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CreateNextGenerationClient::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CNGParticleTypes::registerFactory);
        CNGPartialModels.init();
        CNGSpriteShifts.init();
    }
    private static void clientSetup(final FMLClientSetupEvent event) {
    }


}
