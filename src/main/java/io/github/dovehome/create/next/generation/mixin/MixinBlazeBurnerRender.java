package io.github.dovehome.create.next.generation.mixin;


import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerRenderer;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import io.github.dovehome.create.next.generation.client.BlazeBurnerRenderHelper;
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


    @Redirect(method = "renderSafe(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;isAtLeast(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)Z"))
    private boolean redirect_renderSafe_isAtLeast(BlazeBurnerBlock.HeatLevel heatLevel, BlazeBurnerBlock.HeatLevel level) {
        return HeatLevelEx.isBurning(heatLevel);
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
        float offsetMult = HeatLevelEx.isBurning(heatLevel) ? 64 : 16;
        float offset = Mth.sin((float) ((renderTick / 16f) % (2 * Math.PI))) / offsetMult;
        float offset1 = Mth.sin((float) ((renderTick / 16f + Math.PI) % (2 * Math.PI))) / offsetMult;
        float offset2 = Mth.sin((float) ((renderTick / 16f + Math.PI / 2) % (2 * Math.PI))) / offsetMult;
        float headY = offset - (animation * .75f);

        VertexConsumer solid = bufferSource.getBuffer(RenderType.solid());
        VertexConsumer cutout = bufferSource.getBuffer(RenderType.cutoutMipped());

        ms.pushPose();

        if (canDrawFlame && blockAbove) {
            SpriteShiftEntry spriteShift = BlazeBurnerRenderHelper.getFlameSprite(heatLevel);

            float spriteWidth = spriteShift.getTarget()
                    .getU1()
                    - spriteShift.getTarget()
                    .getU0();

            float spriteHeight = spriteShift.getTarget()
                    .getV1()
                    - spriteShift.getTarget()
                    .getV0();


            float speed = BlazeBurnerRenderHelper.getFlameSpeed(heatLevel);

//            double repeatY = 1.0 * spriteShift.getTarget().getHeight() / spriteShift.getOriginal().getWidth();
//            double repeatZ = 1.0 * spriteShift.getTarget().getWidth() / spriteShift.getOriginal().getHeight();
//
//            double vScroll = speed * time / (repeatY - 1);
//            vScroll = vScroll - Math.floor(vScroll);
//            vScroll = vScroll * spriteHeight * (1 - 1 / repeatY);
//
//            double uScroll = speed * time / (repeatZ - 1) / 2;
//            uScroll = uScroll - Math.floor(uScroll);
//            uScroll = uScroll * spriteWidth * (1 - 1 / repeatZ);

            double vScroll = BlazeBurnerRenderHelper.getBurnerVScroll(heatLevel, speed, time, spriteHeight);

            double uScroll = speed * time / 2;
            uScroll = uScroll - Math.floor(uScroll);
            uScroll = uScroll * spriteWidth / 2;

            SuperByteBuffer flameBuffer = CachedBufferer.partial(AllPartialModels.BLAZE_BURNER_FLAME, blockState);
            if (modelTransform != null)
                flameBuffer.transform(modelTransform);
            flameBuffer.shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll);
            draw(flameBuffer, horizontalAngle, ms, cutout);
        }

        PartialModel blazeModel = BlazeBurnerRenderHelper.getBurnerModel(heatLevel, blockAbove);

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

        if (HeatLevelEx.isBurning(heatLevel)) {
            PartialModel rodsModel = BlazeBurnerRenderHelper.getRodModel1(heatLevel);
            PartialModel rodsModel2 = BlazeBurnerRenderHelper.getRodModel2(heatLevel);

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
