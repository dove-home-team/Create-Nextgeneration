package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinInventory;
import io.github.dovehome.create.next.generation.utility.IBasinBlockEntityExt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BasinBlockEntity.class, remap = false)
public abstract class MixinBasinBlockEntity implements IBasinBlockEntityExt {
    @Shadow public BasinInventory inputInventory;

    @Override
    public void breakBasin() {
        BasinBlockEntity thisObj = (BasinBlockEntity) (Object) this;
        Level level = thisObj.getLevel();
        if (level == null)
            return;
        inputInventory.clearContent();
//        Block.dropResources(thisObj.getBlockState(), level, thisObj.getBlockPos(), thisObj);
        level.removeBlock(thisObj.getBlockPos(), false);
    }
}
