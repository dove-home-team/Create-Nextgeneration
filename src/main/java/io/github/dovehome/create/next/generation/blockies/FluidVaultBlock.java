package io.github.dovehome.create.next.generation.blockies;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import io.github.dovehome.create.next.generation.blockies.tile.FluidVaultBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class FluidVaultBlock extends Block implements IWrenchable, IBE<FluidVaultBlockEntity> {

    public FluidVaultBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Class<FluidVaultBlockEntity> getBlockEntityClass() {
        return FluidVaultBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends FluidVaultBlockEntity> getBlockEntityType() {
        return null;
    }
}
