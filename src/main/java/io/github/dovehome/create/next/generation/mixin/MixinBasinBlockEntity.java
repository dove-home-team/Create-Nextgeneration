package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import io.github.dovehome.create.next.generation.utility.IBasinBlockEntityExt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BasinBlockEntity.class, remap = false)
public abstract class MixinBasinBlockEntity implements IBasinBlockEntityExt {
    @Override
    public void breakBasin() {
        BasinBlockEntity thisObj = (BasinBlockEntity) (Object) this;
        Level level = thisObj.getLevel();
        if (level == null)
            return;
//        Block.dropResources(thisObj.getBlockState(), level, thisObj.getBlockPos(), thisObj);
        level.removeBlock(thisObj.getBlockPos(), false);
    }
}
