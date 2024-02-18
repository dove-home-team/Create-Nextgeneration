package io.github.dovehome.create.next.generation;

import io.github.dovehome.create.next.generation.config.CreateNextGenerationConfig;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        logger.info("Initialization Create Next Generation Mod");
    }
}
