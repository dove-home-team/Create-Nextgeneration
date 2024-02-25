package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.utility.VecHelper;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import io.github.dovehome.create.next.generation.particles.CNGParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(value = BlazeBurnerBlockEntity.class)
public abstract class MixinBlazeBurnerBlockEntity extends BlockEntity {


    @Shadow protected abstract void setBlockHeat(BlazeBurnerBlock.HeatLevel heat);

    public MixinBlazeBurnerBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Unique
    private boolean creative$generation$potentialCatalyst = false;

    @Inject(method = "write", at = @At("HEAD"))
    private void inject_write(CompoundTag compound, boolean clientPacket, CallbackInfo ci) {
        if (creative$generation$potentialCatalyst)
            compound.putBoolean("PotentialCatalyst", true);
    }

    @Inject(method = "read", at = @At("HEAD"))
    private void inject_read(CompoundTag compound, boolean clientPacket, CallbackInfo ci) {
        creative$generation$potentialCatalyst = compound.getBoolean("PotentialCatalyst");
    }

    @Inject(method = "spawnParticles", at = @At(value = "RETURN", ordinal = 3), locals = LocalCapture.CAPTURE_FAILHARD)
    private void inject_spawnParticles_beforeReturn(BlazeBurnerBlock.HeatLevel heatLevel, double burstMult, CallbackInfo ci, Random r, Vec3 c, Vec3 v, boolean empty, double yMotion, Vec3 v2) {
        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.NONE) && HeatLevelEx.isAtMost(heatLevel, BlazeBurnerBlock.HeatLevel.SEETHING)) {
            return;
        }
        SimpleParticleType particleType = null;
        if (heatLevel == HeatLevelEx.EXTERMINATE) {
            particleType = CNGParticleTypes.EXTERMINATE_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            particleType = CNGParticleTypes.OVERLOAD_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.RAGE) {
            particleType = CNGParticleTypes.RAGE_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            particleType = CNGParticleTypes.DRAGON_BREATH_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.GHOST) {
            particleType = CNGParticleTypes.GHOST_BURNER_FLAME.get();
        } else if (HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.SMOOTH)) {
            particleType = CNGParticleTypes.SMOOTH_BURNER_FLAME.get();
        }

        if (particleType != null) {
            level.addParticle(particleType, v2.x, v2.y, v2.z, 0, yMotion, 0);
        }
    }

    @Redirect(method = "spawnParticles", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;isAtLeast(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)Z"))
    private boolean redirect_spawnParticles_first_addParticle(BlazeBurnerBlock.HeatLevel instance, BlazeBurnerBlock.HeatLevel heatLevel) {
        return instance.isAtLeast(heatLevel) && HeatLevelEx.isAtMost(instance, BlazeBurnerBlock.HeatLevel.SEETHING);
    }

    @Redirect(method = "applyCreativeFuel", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;spawnParticleBurst(Z)V"))
    private void redirect_applyCreativeFuel_spawnParticleBurst(BlazeBurnerBlockEntity instance, boolean v) {
        BlazeBurnerBlock.HeatLevel next = instance.getHeatLevelFromBlock().nextActiveLevel();

        SimpleParticleType particleType = ParticleTypes.FLAME;
        if (next == HeatLevelEx.EXTERMINATE) {
            particleType = CNGParticleTypes.EXTERMINATE_BURNER_FLAME.get();
        } else if (next == HeatLevelEx.OVERLOAD) {
            particleType = CNGParticleTypes.OVERLOAD_BURNER_FLAME.get();
        } else if (next == HeatLevelEx.RAGE) {
            particleType = CNGParticleTypes.RAGE_BURNER_FLAME.get();
        } else if (next.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
            particleType = ParticleTypes.SOUL_FIRE_FLAME;
        } else if (next == HeatLevelEx.DRAGON_BREATH) {
            particleType = CNGParticleTypes.DRAGON_BREATH_BURNER_FLAME.get();
        } else if (next == HeatLevelEx.GHOST) {
            particleType = CNGParticleTypes.GHOST_BURNER_FLAME.get();
        } else if (HeatLevelEx.isAtMost(next, HeatLevelEx.SMOOTH_PERMANENT)) {
            particleType = CNGParticleTypes.SMOOTH_BURNER_FLAME.get();
        }

        creative$generation$spawnParticleBurst(particleType);
    }
    @Inject(method = "applyCreativeFuel", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;setBlockHeat(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void inject_applyCreativeFuel_setBlockHeat(CallbackInfo ci, BlazeBurnerBlock.HeatLevel next) {

        if (next == HeatLevelEx.SMOOTH)
            next = next.nextActiveLevel();
        this.setBlockHeat(next);
        ci.cancel();
    }

    @Unique
    private void creative$generation$spawnParticleBurst(ParticleOptions particle) {
        Vec3 c = VecHelper.getCenterOf(worldPosition);
        Random r = level.random;
        for (int i = 0; i < 20; i++) {
            Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, r, .5f)
                    .multiply(1, .25f, 1)
                    .normalize();
            Vec3 v = c.add(offset.scale(.5 + r.nextDouble() * .125f))
                    .add(0, .125, 0);
            Vec3 m = offset.scale(1 / 32f);

            level.addParticle(particle, v.x, v.y, v.z, m.x, m.y,
                    m.z);
        }
    }


}
