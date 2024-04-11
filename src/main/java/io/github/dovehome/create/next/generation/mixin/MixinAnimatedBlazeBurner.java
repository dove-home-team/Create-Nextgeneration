package io.github.dovehome.create.next.generation.mixin;


import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import io.github.dovehome.create.next.generation.client.BlazeBurnerRenderHelper;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AnimatedBlazeBurner.class, remap = false)
public abstract class MixinAnimatedBlazeBurner extends AnimatedKinetics {


    @Shadow
    private BlazeBurnerBlock.HeatLevel heatLevel;

    /**
     * @author WarmthDawn
     * @reason the rendering is too complex to be done with redirect
     */
    @Overwrite
    public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        int scale = 23;

        float offset = (Mth.sin(AnimationTickHolder.getRenderTime() / 16f) + 0.5f) / 16f;


        blockElement(AllBlocks.BLAZE_BURNER.getDefaultState()).atLocal(0, 1.65, 0)
                .scale(scale)
                .render(matrixStack);

        PartialModel blaze = BlazeBurnerRenderHelper.getBurnerModel(heatLevel, true);
        PartialModel rods2 = BlazeBurnerRenderHelper.getRodModel2(heatLevel);

        blockElement(blaze).atLocal(1, 1.8, 1)
                .rotate(0, 180, 0)
                .scale(scale)
                .render(matrixStack);
        blockElement(rods2).atLocal(1, 1.7 + offset, 1)
                .rotate(0, 180, 0)
                .scale(scale)
                .render(matrixStack);

        matrixStack.scale(scale, -scale, scale);
        matrixStack.translate(0, -1.8, 0);

        SpriteShiftEntry spriteShift = BlazeBurnerRenderHelper.getFlameSprite(heatLevel);

        float spriteWidth = spriteShift.getTarget()
                .getU1()
                - spriteShift.getTarget()
                .getU0();

        float spriteHeight = spriteShift.getTarget()
                .getV1()
                - spriteShift.getTarget()
                .getV0();

        float time = AnimationTickHolder.getRenderTime(Minecraft.getInstance().level);


        float speed = BlazeBurnerRenderHelper.getFlameSpeed(heatLevel);

        double vScroll = BlazeBurnerRenderHelper.getBurnerVScroll(heatLevel, speed, time, spriteHeight);

        double uScroll = speed * time / 2;
        uScroll = uScroll - Math.floor(uScroll);
        uScroll = uScroll * spriteWidth / 2;

        Minecraft mc = Minecraft.getInstance();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers()
                .bufferSource();
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        CachedBufferer.partial(AllPartialModels.BLAZE_BURNER_FLAME, Blocks.AIR.defaultBlockState())
                .shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(matrixStack, vb);
        matrixStack.popPose();
    }

}
