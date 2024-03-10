package io.github.dovehome.create.next.generation.mixin;


import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerRenderer;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import io.github.dovehome.create.next.generation.client.CNGPartialModels;
import io.github.dovehome.create.next.generation.client.CNGSpriteShifts;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(value = BlazeBurnerRenderer.class, remap = false)
public abstract class MixinBlazeBurnerRender extends SafeBlockEntityRenderer<BlazeBurnerBlockEntity> {


    @Shadow
    private static void draw(SuperByteBuffer buffer, float horizontalAngle, PoseStack ms, VertexConsumer vc) {
    }


    @Unique
    private static SpriteShiftEntry creative$generation$getFlameSprite(BlazeBurnerBlock.HeatLevel heatLevel) {
        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return AllSpriteShifts.SUPER_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.RAGE) {
            return CNGSpriteShifts.RAGE_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return CNGSpriteShifts.OVERLOAD_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.EXTERMINATE) {
            return CNGSpriteShifts.EXTERMINATE_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return CNGSpriteShifts.DRAGON_BREATH_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return CNGSpriteShifts.GHOST_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.SMOOTH || heatLevel == HeatLevelEx.SMOOTH_PERMANENT) {
            return CNGSpriteShifts.SMOOTH_BURNER_FLAME;
        } else {
            return AllSpriteShifts.BURNER_FLAME;
        }
    }

    @Unique
    private static float creative$generation$getFlameSpeed(BlazeBurnerBlock.HeatLevel heatLevel) {
        int speed;

        if(heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
            speed = heatLevel.ordinal();
        } else if(HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.GHOST)) {
            speed = 4 + HeatLevelEx.getActualIndex(heatLevel);
        } else if(HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.SMOOTH)) {
            speed = 2;
        } else {
            speed = 3;
        }

        return 1 / 32f + 1 / 64f * speed;
    }

    @Unique
    private static PartialModel creative$generation$getBurnerModel(BlazeBurnerBlock.HeatLevel heatLevel, boolean blockAbove) {
        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return blockAbove ? AllPartialModels.BLAZE_SUPER_ACTIVE : AllPartialModels.BLAZE_SUPER;
        } else if (heatLevel == HeatLevelEx.RAGE) {
            return blockAbove ? CNGPartialModels.BLAZE_RAGE_ACTIVE : CNGPartialModels.BLAZE_RAGE;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return blockAbove ? CNGPartialModels.BLAZE_OVERLOAD_ACTIVE : CNGPartialModels.BLAZE_OVERLOAD;
        } else if (heatLevel == HeatLevelEx.EXTERMINATE) {
            return blockAbove ? CNGPartialModels.BLAZE_EXTERMINATE_ACTIVE : CNGPartialModels.BLAZE_EXTERMINATE;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return blockAbove ? CNGPartialModels.BLAZE_DRAGON_BREATH_ACTIVE : CNGPartialModels.BLAZE_DRAGON_BREATH;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return blockAbove ? CNGPartialModels.BLAZE_GHOST_ACTIVE : CNGPartialModels.BLAZE_GHOST;
        } else if (heatLevel == HeatLevelEx.SMOOTH || heatLevel == HeatLevelEx.SMOOTH_PERMANENT) {
            return blockAbove ? CNGPartialModels.BLAZE_SMOOTH_ACTIVE : CNGPartialModels.BLAZE_SMOOTH;
        } else if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            return blockAbove ? AllPartialModels.BLAZE_ACTIVE : AllPartialModels.BLAZE_IDLE;
        } else {
            return AllPartialModels.BLAZE_INERT;
        }
    }

    @Unique
    private static PartialModel creative$generation$getRodModel1(BlazeBurnerBlock.HeatLevel heatLevel) {

        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return AllPartialModels.BLAZE_BURNER_SUPER_RODS;
        } else if (heatLevel == HeatLevelEx.RAGE) {
            return CNGPartialModels.BLAZE_BURNER_RAGE_RODS;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return CNGPartialModels.BLAZE_BURNER_OVERLOAD_RODS;
        } else if (heatLevel == HeatLevelEx.EXTERMINATE) {
            return CNGPartialModels.BLAZE_BURNER_EXTERMINATE_RODS;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return CNGPartialModels.BLAZE_BURNER_DRAGON_BREATH_RODS;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return CNGPartialModels.BLAZE_BURNER_GHOST_RODS;
        } else if (heatLevel == HeatLevelEx.SMOOTH || heatLevel == HeatLevelEx.SMOOTH_PERMANENT) {
            return CNGPartialModels.BLAZE_BURNER_SMOOTH_RODS;
        } else {
            return AllPartialModels.BLAZE_BURNER_RODS;
        }
    }

    @Unique
    private static PartialModel creative$generation$getRodModel2(BlazeBurnerBlock.HeatLevel heatLevel) {

        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return AllPartialModels.BLAZE_BURNER_SUPER_RODS_2;
        } else if (heatLevel == HeatLevelEx.RAGE) {
            return CNGPartialModels.BLAZE_BURNER_RAGE_RODS_2;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return CNGPartialModels.BLAZE_BURNER_OVERLOAD_RODS_2;
        } else if (heatLevel == HeatLevelEx.EXTERMINATE) {
            return CNGPartialModels.BLAZE_BURNER_EXTERMINATE_RODS_2;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return CNGPartialModels.BLAZE_BURNER_DRAGON_BREATH_RODS_2;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return CNGPartialModels.BLAZE_BURNER_GHOST_RODS_2;
        } else if (heatLevel == HeatLevelEx.SMOOTH || heatLevel == HeatLevelEx.SMOOTH_PERMANENT) {
            return CNGPartialModels.BLAZE_BURNER_SMOOTH_RODS_2;
        } else {
            return AllPartialModels.BLAZE_BURNER_RODS_2;
        }
    }

    @Redirect(method = "renderSafe(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;isAtLeast(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)Z"))
    private boolean redirect_renderSafe_isAtLeast(BlazeBurnerBlock.HeatLevel heatLevel, BlazeBurnerBlock.HeatLevel level) {
        return heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) || HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.SMOOTH);
    }

    /**
     * @author WarmthDawn
     * @reason the rendering is too complex to be done with redirect
     */
    @Overwrite
    private static void renderShared(PoseStack ms, @Nullable PoseStack modelTransform, MultiBufferSource bufferSource,
                                     Level level, BlockState blockState, BlazeBurnerBlock.HeatLevel heatLevel, float animation, float horizontalAngle,
                                     boolean canDrawFlame, boolean drawGoggles, boolean drawHat, int hashCode) {

        boolean blockAbove = animation > 0.125f;
        float time = AnimationTickHolder.getRenderTime(level);
        float renderTick = time + (hashCode % 13) * 16f;
        float offsetMult = heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) || HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.SMOOTH) ? 64 : 16;
        float offset = Mth.sin((float) ((renderTick / 16f) % (2 * Math.PI))) / offsetMult;
        float offset1 = Mth.sin((float) ((renderTick / 16f + Math.PI) % (2 * Math.PI))) / offsetMult;
        float offset2 = Mth.sin((float) ((renderTick / 16f + Math.PI / 2) % (2 * Math.PI))) / offsetMult;
        float headY = offset - (animation * .75f);

        VertexConsumer solid = bufferSource.getBuffer(RenderType.solid());
        VertexConsumer cutout = bufferSource.getBuffer(RenderType.cutoutMipped());

        ms.pushPose();

        if (canDrawFlame && blockAbove) {
            SpriteShiftEntry spriteShift = creative$generation$getFlameSprite(heatLevel);

            float spriteWidth = spriteShift.getTarget()
                    .getU1()
                    - spriteShift.getTarget()
                    .getU0();

            float spriteHeight = spriteShift.getTarget()
                    .getV1()
                    - spriteShift.getTarget()
                    .getV0();

            float speed = creative$generation$getFlameSpeed(heatLevel);

            double vScroll = speed * time;
            vScroll = vScroll - Math.floor(vScroll);
            vScroll = vScroll * spriteHeight / 2;

            double uScroll = speed * time / 2;
            uScroll = uScroll - Math.floor(uScroll);
            uScroll = uScroll * spriteWidth / 2;

            SuperByteBuffer flameBuffer = CachedBufferer.partial(AllPartialModels.BLAZE_BURNER_FLAME, blockState);
            if (modelTransform != null)
                flameBuffer.transform(modelTransform);
            flameBuffer.shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll);
            draw(flameBuffer, horizontalAngle, ms, cutout);
        }

        PartialModel blazeModel = creative$generation$getBurnerModel(heatLevel, blockAbove);

        SuperByteBuffer blazeBuffer = CachedBufferer.partial(blazeModel, blockState);
        if (modelTransform != null)
            blazeBuffer.transform(modelTransform);
        blazeBuffer.translate(0, headY, 0);
        draw(blazeBuffer, horizontalAngle, ms, solid);

        boolean isSmallModel = blazeModel == AllPartialModels.BLAZE_INERT;

        if (drawGoggles) {
            // TODO google size
            PartialModel gogglesModel = isSmallModel ? AllPartialModels.BLAZE_GOGGLES_SMALL : AllPartialModels.BLAZE_GOGGLES;

            SuperByteBuffer gogglesBuffer = CachedBufferer.partial(gogglesModel, blockState);
            if (modelTransform != null)
                gogglesBuffer.transform(modelTransform);
            gogglesBuffer.translate(0, headY + 8 / 16f, 0);
            draw(gogglesBuffer, horizontalAngle, ms, solid);
        }

        if (drawHat) {
            SuperByteBuffer hatBuffer = CachedBufferer.partial(AllPartialModels.TRAIN_HAT, blockState);
            if (modelTransform != null)
                hatBuffer.transform(modelTransform);
            hatBuffer.translate(0, headY, 0);
            if (isSmallModel) {
                hatBuffer.translateY(0.5f)
                        .centre()
                        .scale(0.75f)
                        .unCentre();
            } else {
                hatBuffer.translateY(0.75f);
            }
            hatBuffer
                    .rotateCentered(Direction.UP, horizontalAngle + Mth.PI)
                    .translate(0.5f, 0, 0.5f)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(ms, solid);
        }

        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) || HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.SMOOTH)) {
            PartialModel rodsModel = creative$generation$getRodModel1(heatLevel);
            PartialModel rodsModel2 = creative$generation$getRodModel2(heatLevel);

            SuperByteBuffer rodsBuffer = CachedBufferer.partial(rodsModel, blockState);
            if (modelTransform != null)
                rodsBuffer.transform(modelTransform);
            rodsBuffer.translate(0, offset1 + animation + .125f, 0)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(ms, solid);

            SuperByteBuffer rodsBuffer2 = CachedBufferer.partial(rodsModel2, blockState);
            if (modelTransform != null)
                rodsBuffer2.transform(modelTransform);
            rodsBuffer2.translate(0, offset2 + animation - 3 / 16f, 0)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(ms, solid);
        }

        ms.popPose();
    }

}
