package io.github.dovehome.create.next.generation.blockies.tile;

import com.mojang.datafixers.DSL;
import io.github.dovehome.create.next.generation.CreateNextGeneration;
import io.github.dovehome.create.next.generation.blockies.CNGBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.function.Supplier;

public enum CNGBlockEntityTypes {
    fluid_vault_block_entity_type(FluidVaultBlockEntity::new),
    ;
    private static final DeferredRegister<BlockEntityType<?>> blockEntityTypes = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CreateNextGeneration.MODID);

    public static DeferredRegister<BlockEntityType<?>> blockEntityTypes() {
        return blockEntityTypes;
    }

    public final RegistryObject<BlockEntityType<? extends BlockEntity>> value;

    CNGBlockEntityTypes(BlockEntityType.BlockEntitySupplier<?> t) {
        value = blockEntityTypes().register(name().toLowerCase(Locale.ROOT), () -> BlockEntityType.Builder.of(t, CNGBlocks.fluid_vault_block.value.get()).build(DSL.remainderType()));
    }

    public static void init() {
        blockEntityTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
