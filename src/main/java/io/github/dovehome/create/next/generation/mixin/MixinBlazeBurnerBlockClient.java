package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlazeBurnerBlock.class)
public abstract class MixinBlazeBurnerBlockClient {

    @Redirect(method = "animateTick",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;isAtLeast(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)Z"))
    private boolean redirect_animateTick_isAtLeast(BlazeBurnerBlock.HeatLevel heatLevel, BlazeBurnerBlock.HeatLevel level) {
        return heatLevel != BlazeBurnerBlock.HeatLevel.NONE;
    }
}
