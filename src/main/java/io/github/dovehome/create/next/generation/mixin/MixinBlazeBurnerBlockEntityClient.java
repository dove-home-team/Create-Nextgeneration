package io.github.dovehome.create.next.generation.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlazeBurnerBlockEntity.class, remap = false)
public class MixinBlazeBurnerBlockEntityClient {


    @Redirect(method = "tickAnimation", at = @At(value = "INVOKE", ordinal = 0,
            target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;isAtLeast(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)Z"))
    private boolean redirect_renderSafe_isAtLeast(BlazeBurnerBlock.HeatLevel heatLevel, BlazeBurnerBlock.HeatLevel level) {
        return heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) || HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.GENTLY);
    }
}
