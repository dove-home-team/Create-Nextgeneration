package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import io.github.dovehome.create.next.generation.utility.IBasinBlockEntityExt;
import io.github.dovehome.create.next.generation.utility.IBlazeBurnerBlockEntityExt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(value = BasinOperatingBlockEntity.class, remap = false)
public abstract class MixinBasinOperatingBlockEntity extends KineticBlockEntity {

    private MixinBasinOperatingBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Shadow
    protected abstract Optional<BasinBlockEntity> getBasin();

    @Inject(method = "applyBasinRecipe", at = @At(value = "INVOKE",
            target = "Lcom/simibubi/create/content/processing/basin/BasinOperatingBlockEntity;getProcessedRecipeTrigger()Ljava/util/Optional;"))

    private void inject_applyBasinRecipe_apply(CallbackInfo ci) {
        BasinBlockEntity basin = getBasin().orElseThrow();
        BlazeBurnerBlock.HeatLevel heatLevel = BasinBlockEntity.getHeatLevelOf(basin.getLevel()
                .getBlockState(basin.getBlockPos()
                        .below(1)));

        if (heatLevel == HeatLevelEx.COLLAPSE) {
            if (basin instanceof IBasinBlockEntityExt basinExt) {
                basinExt.breakBasin();
            }
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            if (level == null)
                return;
            BlockEntity burnerBE = level.getBlockEntity(basin.getBlockPos().below(1));
            if (!(burnerBE instanceof IBlazeBurnerBlockEntityExt burnerExt))
                return;
            burnerExt.create$generation$consumeFuel();
        }

    }

}
