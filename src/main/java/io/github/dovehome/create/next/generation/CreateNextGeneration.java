package io.github.dovehome.create.next.generation;

import io.github.dovehome.create.next.generation.blockies.CNGBlocks;
import io.github.dovehome.create.next.generation.blockies.tile.CNGBlockEntityTypes;
import io.github.dovehome.create.next.generation.config.CreateNextGenerationConfig;
import io.github.dovehome.create.next.generation.particles.CNGParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateNextGeneration.MODID)
public class CreateNextGeneration {
    public static final String MODID = "createnextgeneration";
    public static final Logger logger = LoggerFactory.getLogger(MODID);

    public CreateNextGeneration() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CreateNextGenerationConfig.GENERAL_SPEC, "create-next-generation.toml");

        CNGBlocks.init();
        CNGBlockEntityTypes.init();
        CNGParticleTypes.init();


        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CreateNextGenerationClient::onCtorClient);
    }

    private void setup(final FMLCommonSetupEvent event) {
        logger.info("Initialization Create Next Generation Mod");
    }


}
