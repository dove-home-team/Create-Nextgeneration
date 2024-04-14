package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BoilerHeaters.class, remap = false)
public abstract class MixinBoilerHeater {


    @Inject(method = "lambda$registerDefaults$0", at = @At("HEAD"), cancellable = true)
    private static void inject_registerDefaults(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Float> cir) {
        cir.cancel();
        BlazeBurnerBlock.HeatLevel value = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);
        if (value == BlazeBurnerBlock.HeatLevel.NONE) {
            cir.setReturnValue(-1F);
            return;
        }
        if (value == HeatLevelEx.HOT_HEATED) {
            cir.setReturnValue(2F);
            return;
        }
        if(value == BlazeBurnerBlock.HeatLevel.SEETHING){
            cir.setReturnValue(4F);
            return;
        }

        if(value.isAtLeast(HeatLevelEx.OVERLOAD)){
            cir.setReturnValue(-1F);
            return;
        }

        if (value.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            cir.setReturnValue(1F);
            return;
        }

        if(value == HeatLevelEx.DRAGON_BREATH){
            cir.setReturnValue(1.0F);
            // TODO
            return;
        }

        cir.setReturnValue(0F);

    }
}
