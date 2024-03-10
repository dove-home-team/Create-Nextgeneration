package io.github.dovehome.create.next.generation.mixin;


import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import io.github.dovehome.create.next.generation.client.BlazeBurnerRenderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AnimatedBlazeBurner.class, remap = false)
public abstract class MixinAnimatedBlazeBurner {


    @Shadow
    private BlazeBurnerBlock.HeatLevel heatLevel;

    @Redirect(method = "draw", at = @At(value = "FIELD",
            target = "Lcom/simibubi/create/AllPartialModels;BLAZE_ACTIVE:Lcom/jozufozu/flywheel/core/PartialModel;"
    ))
    private PartialModel redirect_draw_BLAZE_ACTIVE_get() {

        return BlazeBurnerRenderHelper.getBurnerModel(heatLevel, true);
    }

    @Redirect(method = "draw", at = @At(value = "FIELD",
            target = "Lcom/simibubi/create/AllPartialModels;BLAZE_BURNER_RODS_2:Lcom/jozufozu/flywheel/core/PartialModel;"
    ))
    private PartialModel redirect_draw_BLAZE_BURNER_RODS_2_get() {

        return BlazeBurnerRenderHelper.getRodModel2(heatLevel);
    }

    @Redirect(method = "draw", at = @At(value = "FIELD",
            target = "Lcom/simibubi/create/AllSpriteShifts;BURNER_FLAME:Lcom/simibubi/create/foundation/block/render/SpriteShiftEntry;"
    ))
    private SpriteShiftEntry redirect_draw_BURNER_FLAME_get() {
        return BlazeBurnerRenderHelper.getFlameSprite(heatLevel);
    }

    @Redirect(method = "draw", at = @At(value = "INVOKE",
            target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;ordinal()I"
    ))
    private int redirect_draw_level_ordinal(BlazeBurnerBlock.HeatLevel instance) {

        return BlazeBurnerRenderHelper.getFlameSpeedLevel(instance);
    }
}
