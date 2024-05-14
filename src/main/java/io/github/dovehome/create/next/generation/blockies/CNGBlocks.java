package io.github.dovehome.create.next.generation.blockies;

import io.github.dovehome.create.next.generation.CreateNextGeneration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public enum CNGBlocks {
    fluid_vault_block(new FluidVaultBlock(BlockBehaviour.Properties.of()));

    private static final DeferredRegister<Block> blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateNextGeneration.MODID);

    public static DeferredRegister<Block> blocks() {

        return blocks;
    }

    public final RegistryObject<Block> value;
    <T extends Block> CNGBlocks(String registerName, T t) {
        value = blocks().register(registerName, () -> t);
    }

    public static void init() {

        blocks.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
