package io.github.dovehome.create.next.generation.blockies.tile;

import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FluidVaultBlockEntity extends SmartBlockEntity implements IMultiBlockEntityContainer.Inventory {
    public FluidVaultBlockEntity(BlockPos pos, BlockState state) {
        super(CNGBlockEntityTypes.fluid_vault_block_entity_type.value.get(), pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {

    }

    @Override
    public BlockPos getController() {
        return null;
    }

    @Override
    public <T extends BlockEntity & IMultiBlockEntityContainer> T getControllerBE() {
        return null;
    }

    @Override
    public boolean isController() {
        return false;
    }

    @Override
    public void setController(BlockPos blockPos) {

    }

    @Override
    public void removeController(boolean b) {

    }

    @Override
    public BlockPos getLastKnownPos() {
        return null;
    }

    @Override
    public void preventConnectivityUpdate() {

    }

    @Override
    public void notifyMultiUpdated() {

    }

    @Override
    public Direction.Axis getMainConnectionAxis() {
        return null;
    }

    @Override
    public int getMaxLength(Direction.Axis axis, int i) {
        return 0;
    }

    @Override
    public int getMaxWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setHeight(int i) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public void setWidth(int i) {

    }
}
