package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.AllTags;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import io.github.dovehome.create.next.generation.CNGTags;
import io.github.dovehome.create.next.generation.data.ExtendBurnerState;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import io.github.dovehome.create.next.generation.particles.CNGParticleTypes;
import io.github.dovehome.create.next.generation.utility.CNGLang;
import io.github.dovehome.create.next.generation.utility.IBlazeBurnerBlockEntityExt;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Random;

@Mixin(value = BlazeBurnerBlockEntity.class, remap = false)
public abstract class MixinBlazeBurnerBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation, IBlazeBurnerBlockEntityExt {
    @Unique
    private static final int GENTLY_TICKS = 60 * 20;
    @Unique
    private static final int MAX_DRAGON_BREATH_TIMES = 20;
    @Unique
    private static final int THRESHOLD_DRAGON_BREATH_TIMES = 4;
    @Unique
    private static final int OVERLOAD_BURN_TIME = 20000;

    @Shadow
    protected abstract void setBlockHeat(BlazeBurnerBlock.HeatLevel heat);

    @Shadow
    protected boolean isCreative;

    @Shadow
    protected BlazeBurnerBlockEntity.FuelType activeFuel;

    @Shadow
    protected int remainingBurnTime;

    @Shadow
    public abstract void updateBlockState();

    @Shadow
    @Final
    public static int MAX_HEAT_CAPACITY;


    @Shadow
    public abstract BlazeBurnerBlock.HeatLevel getHeatLevelFromBlock();

    @Shadow
    @Final
    public static int INSERTION_THRESHOLD;

    @Shadow
    protected abstract void playSound();

    public MixinBlazeBurnerBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Unique
    private boolean creative$generation$potentialCatalyst = false;

    // 当前状态燃烧持续时间
    @Unique
    private int creative$generation$burningTicks = 0;
    @Unique
    private int creative$generation$ramainingCraftingTimes = 0;

    @Unique
    private ExtendBurnerState creative$generation$extendState = ExtendBurnerState.DEFAULT;

    @Unique
    private MutableComponent creative$generation$getLevelName(BlazeBurnerBlock.HeatLevel level) {
        Style style = Style.EMPTY;
        if (level == HeatLevelEx.HOT_HEATED) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.hot_heated").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.HOT_HEATED)));
        } else if (level == HeatLevelEx.OVERLOAD) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.overload").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.OVERLOAD)));
        } else if (level == HeatLevelEx.COLLAPSE) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.collapse").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.COLLAPSE)));
        } else if (level == HeatLevelEx.DRAGON_BREATH) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.dragon_breath").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.DRAGON_BREATH)));
        } else if (level == HeatLevelEx.GHOST) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.ghost").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.GHOST)));
        } else if (level == HeatLevelEx.GENTLY_PERMANENT || level == HeatLevelEx.GENTLY) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.gently").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.GENTLY)));
        } else if (level == BlazeBurnerBlock.HeatLevel.FADING || level == BlazeBurnerBlock.HeatLevel.KINDLED) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.kindled").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.HEATED)));
        } else if (level == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.seething").withStyle(style.withColor(TextColor.fromRgb(HeatLevelEx.Colors.SUPERHEATED)));
        } else if (level == BlazeBurnerBlock.HeatLevel.SMOULDERING) {
            return CNGLang.translateDirect("gui.goggles.burner_stat.smouldering").withStyle(ChatFormatting.GRAY);
        }
        return Lang.translateDirect("gui.goggles.burner_stat.none");
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {


        BlazeBurnerBlock.HeatLevel heatLevel = getHeatLevelFromBlock();

        MutableComponent heatLevelComponent = creative$generation$getLevelName(heatLevel);
        CNGLang.translate("gui.goggles.burner_stat", heatLevelComponent).forGoggles(tooltip);

        if (heatLevel == BlazeBurnerBlock.HeatLevel.NONE || heatLevel == BlazeBurnerBlock.HeatLevel.SMOULDERING) {

            if (creative$generation$potentialCatalyst) {
                CNGLang.translate("gui.goggles.burner_stat.potential_activated").style(ChatFormatting.GREEN).forGoggles(tooltip, 1);
            }
            return true;
        }

        MutableComponent burnTimeComponent;
        if (isCreative || heatLevel == HeatLevelEx.GENTLY_PERMANENT) {
            burnTimeComponent = CNGLang.translateDirect("gui.goggles.burner_stat.burn_time.infinite").withStyle(ChatFormatting.GREEN);
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            burnTimeComponent = CNGLang.translateDirect("gui.goggles.burner_stat.burn_time.times", creative$generation$ramainingCraftingTimes).withStyle(ChatFormatting.YELLOW);
        } else {
            burnTimeComponent = CNGLang.translateDirect("gui.goggles.burner_stat.burn_time.seconds", remainingBurnTime / 20).withStyle(ChatFormatting.YELLOW);
        }

        if (heatLevel == HeatLevelEx.GENTLY) {
            CNGLang.translate("gui.goggles.burner_stat.cooldown").style(ChatFormatting.RED).forGoggles(tooltip, 1);
        }

        if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            CNGLang.translate("gui.goggles.burner_stat.crafting_times", burnTimeComponent).style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
        } else if (heatLevel == HeatLevelEx.GENTLY) {
            CNGLang.translate("gui.goggles.burner_stat.cool_time", burnTimeComponent).style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
        } else {
            CNGLang.translate("gui.goggles.burner_stat.burn_time", burnTimeComponent).style(ChatFormatting.GRAY).forGoggles(tooltip, 1);
        }

        if (creative$generation$potentialCatalyst) {
            CNGLang.translate("gui.goggles.burner_stat.potential_activated").style(ChatFormatting.GREEN).forGoggles(tooltip, 1);
        }

        return true;
    }


    @Override
    @Unique
    public void create$generation$consumeFuel() {
        if (isCreative) {
            return;
        }
        if (creative$generation$extendState == ExtendBurnerState.DRAGON_BREATH) {
            creative$generation$ramainingCraftingTimes--;
            notifyUpdate();
            if (creative$generation$ramainingCraftingTimes <= 0) {
                creative$generation$extendState = ExtendBurnerState.DEFAULT;
                remainingBurnTime = MAX_HEAT_CAPACITY / 2;
                activeFuel = BlazeBurnerBlockEntity.FuelType.NORMAL;
                updateBlockState();
            }
        }
    }

    @Unique
    private void creative$generation$updateAndPlaySound() {

        if (level.isClientSide) {

            SimpleParticleType particleType = ParticleTypes.FLAME;
            switch (creative$generation$extendState) {
                case DEFAULT -> {
                    if (activeFuel == BlazeBurnerBlockEntity.FuelType.SPECIAL) {
                        particleType = ParticleTypes.SOUL_FIRE_FLAME;
                    }
                }
                case OVERLOAD -> particleType = CNGParticleTypes.OVERLOAD_BURNER_FLAME.get();
                case DRAGON_BREATH -> particleType = CNGParticleTypes.DRAGON_BREATH_BURNER_FLAME.get();
                case GHOST -> particleType = CNGParticleTypes.GHOST_BURNER_FLAME.get();
                case GENTLY_PERMANENT -> particleType = CNGParticleTypes.GENTLY_BURNER_FLAME.get();
                case COLLAPSE -> particleType = CNGParticleTypes.COLLAPSE_BURNER_FLAME.get();
                case SUPER_HEATED -> particleType = CNGParticleTypes.HOT_HEATED_BURNER_FLAME.get();
            }

            creative$generation$spawnParticleBurst(particleType);
            return;
        }

        BlazeBurnerBlock.HeatLevel prev = getHeatLevelFromBlock();
        playSound();
        updateBlockState();

        if (prev != getHeatLevelFromBlock())
            level.playSound(null, worldPosition, SoundEvents.BLAZE_AMBIENT, SoundSource.BLOCKS,
                    .125f + level.random.nextFloat() * .125f, 1.15f - level.random.nextFloat() * .25f);
    }

    @Inject(method = "tryUpdateFuel", at = @At(value = "HEAD"), cancellable = true)
    private void inject_tryUpdateFuel(ItemStack itemStack, boolean forceOverflow, boolean simulate, CallbackInfoReturnable<Boolean> cir) {
        if (isCreative) {
            return;
        }
        if (itemStack.is(CNGTags.BLAZE_BURNER_CATALYST_POTENTIAL)) {
            if (!simulate) {
                creative$generation$potentialCatalyst = true;
                notifyUpdate();
                creative$generation$updateAndPlaySound();
            }
            cir.setReturnValue(true);
            cir.cancel();
            return;
        }


        switch (creative$generation$extendState) {
            case DEFAULT -> {
                if (activeFuel == BlazeBurnerBlockEntity.FuelType.SPECIAL && itemStack.is(CNGTags.BLAZE_BURNER_FUEL_DRAGON_BREATH)) {
                    if (!simulate) {
                        creative$generation$extendState = ExtendBurnerState.DRAGON_BREATH;
                        creative$generation$ramainingCraftingTimes = 4;
                        notifyUpdate();
                        creative$generation$updateAndPlaySound();
                    }
                    cir.setReturnValue(true);
                    cir.cancel();
                } else if (activeFuel == BlazeBurnerBlockEntity.FuelType.NONE && itemStack.is(CNGTags.BLAZE_BURNER_CATALYST_GENTLY)) {
                    if (!simulate) {
                        creative$generation$extendState = ExtendBurnerState.GENTLY_PERMANENT;
                        notifyUpdate();
                        creative$generation$updateAndPlaySound();
                    }
                    cir.setReturnValue(true);
                    cir.cancel();
                }


                // otherwise use old logic
            }
            case OVERLOAD, COLLAPSE, GENTLY_TEMPORARY -> {
                // do not accept any fuel
                cir.setReturnValue(false);
                cir.cancel();
            }
            case DRAGON_BREATH -> {
                if (itemStack.is(CNGTags.BLAZE_BURNER_FUEL_DRAGON_BREATH)) {
                    if (!forceOverflow && creative$generation$ramainingCraftingTimes >= THRESHOLD_DRAGON_BREATH_TIMES) {
                        cir.setReturnValue(false);
                        cir.cancel();
                        return;
                    } else if (creative$generation$ramainingCraftingTimes >= MAX_DRAGON_BREATH_TIMES) {
                        cir.setReturnValue(false);
                        cir.cancel();
                        return;
                    }
                    if (!simulate) {
                        creative$generation$ramainingCraftingTimes += 4;
                        notifyUpdate();
                        creative$generation$updateAndPlaySound();
                    }
                    cir.setReturnValue(true);
                    cir.cancel();
                } else {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
            case GHOST, SUPER_HEATED -> {
                boolean isGhost = creative$generation$extendState == ExtendBurnerState.GHOST;
                if (remainingBurnTime > INSERTION_THRESHOLD && !(forceOverflow && !isGhost)) {
                    cir.setReturnValue(false);
                    cir.cancel();
                    return;
                }


                // can accept any normal fuel
                int newBurnTime = 0;

                if (isGhost && itemStack.is(CNGTags.BLAZE_BURNER_FUEL_GHOST)) {
                    newBurnTime = 3200;
                } else if (AllTags.AllItemTags.BLAZE_BURNER_FUEL_SPECIAL.matches(itemStack)) {
                    newBurnTime = 3200;
                } else {
                    newBurnTime = ForgeHooks.getBurnTime(itemStack, null);
                    if (newBurnTime <= 0 && AllTags.AllItemTags.BLAZE_BURNER_FUEL_REGULAR.matches(itemStack)) {
                        newBurnTime = 1600; // Same as coal
                    }
                }

                if (newBurnTime <= 0) {
                    cir.setReturnValue(false);
                    cir.cancel();
                    return;
                }

                boolean overload = false;
                if (!isGhost && newBurnTime >= OVERLOAD_BURN_TIME) {
                    overload = true;
                }

                if (!isGhost) {
                    // consume two times faster
                    newBurnTime /= 2;
                }

                if (remainingBurnTime < MAX_HEAT_CAPACITY) {
                    newBurnTime = Math.min(remainingBurnTime + newBurnTime, MAX_HEAT_CAPACITY);
                } else {
                    newBurnTime = remainingBurnTime;
                }

                if (!simulate) {
                    if (overload) {
                        remainingBurnTime = newBurnTime / 2;
                        creative$generation$extendState = ExtendBurnerState.OVERLOAD;
                    } else {

                        remainingBurnTime = newBurnTime;
                    }
                    notifyUpdate();
                    creative$generation$updateAndPlaySound();
                }


            }
            case GENTLY_PERMANENT -> {
                if (itemStack.is(CNGTags.BLAZE_BURNER_CATALYST_GENTLY_WAKEUP)) {
                    if (!simulate) {
                        creative$generation$extendState = ExtendBurnerState.DEFAULT;
                        notifyUpdate();
                        creative$generation$updateAndPlaySound();
                    }
                    cir.setReturnValue(true);
                    cir.cancel();
                } else if (itemStack.is(CNGTags.BLAZE_BURNER_FUEL_GHOST)) {
                    // TODO 添加声音和粒子效果
                    if (!simulate) {
                        remainingBurnTime = 1600;
                        creative$generation$extendState = ExtendBurnerState.GHOST;
                        notifyUpdate();
                        creative$generation$updateAndPlaySound();
                    }
                    cir.setReturnValue(true);
                    cir.cancel();
                } else {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
        }


    }

    @Inject(method = "getHeatLevel", at = @At("HEAD"), cancellable = true)
    private void inject_getHeatLevel(CallbackInfoReturnable<BlazeBurnerBlock.HeatLevel> cir) {
        switch (creative$generation$extendState) {
            case DEFAULT -> {
                if(activeFuel == BlazeBurnerBlockEntity.FuelType.SPECIAL) {
                    cir.setReturnValue(HeatLevelEx.HOT_HEATED);
                    cir.cancel();
                }
                // use previous logic
                return;
            }
            case OVERLOAD -> cir.setReturnValue(HeatLevelEx.OVERLOAD);
            case DRAGON_BREATH -> cir.setReturnValue(HeatLevelEx.DRAGON_BREATH);
            case GHOST -> cir.setReturnValue(HeatLevelEx.GHOST);
            case COLLAPSE -> cir.setReturnValue(HeatLevelEx.COLLAPSE);
            case SUPER_HEATED -> cir.setReturnValue(BlazeBurnerBlock.HeatLevel.SEETHING);
            case GENTLY_PERMANENT -> cir.setReturnValue(HeatLevelEx.GENTLY_PERMANENT);
            case GENTLY_TEMPORARY -> cir.setReturnValue(HeatLevelEx.GENTLY);
        }
        cir.cancel();
    }


    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/SmartBlockEntity;tick()V", shift = At.Shift.AFTER))
    private void inject_tick_head(CallbackInfo ci) {
        if (isCreative || level == null || !level.isClientSide)
            return;

        if (remainingBurnTime > 0)
            remainingBurnTime--;
    }

    @Inject(method = "tick", at = @At(value = "RETURN", shift = At.Shift.BY, by = 2, ordinal = 1), cancellable = true)
    private void inject_tick(CallbackInfo ci) {

        if (!creative$generation$potentialCatalyst && creative$generation$extendState == ExtendBurnerState.DEFAULT) {
            // use previous logic
            return;
        }

        if (creative$generation$extendState == ExtendBurnerState.DEFAULT) {

            if (activeFuel != BlazeBurnerBlockEntity.FuelType.SPECIAL) {
                creative$generation$burningTicks = 0;
                // also use previous logic
                return;
            }

            // 60s
            if (creative$generation$burningTicks >= 60 * 20) {
                creative$generation$extendState = ExtendBurnerState.SUPER_HEATED;
                creative$generation$burningTicks = 0;

                remainingBurnTime /= 2;
                activeFuel = BlazeBurnerBlockEntity.FuelType.NORMAL;
                updateBlockState();
                // not use previous logic
                ci.cancel();
                return;
            }

            creative$generation$burningTicks++;
            // use previous logic
            return;
        }

        ci.cancel();

        if (creative$generation$extendState == ExtendBurnerState.GENTLY_PERMANENT) {
            // do nothing
            return;
        }


        if (creative$generation$extendState == ExtendBurnerState.DRAGON_BREATH) {
            // do nothing too because it's computed by times
            return;
        }


        if (creative$generation$extendState == ExtendBurnerState.SUPER_HEATED) {
            if (remainingBurnTime > 0) {
                remainingBurnTime--;
            } else {
                activeFuel = BlazeBurnerBlockEntity.FuelType.NORMAL;
                remainingBurnTime = MAX_HEAT_CAPACITY / 2;
                creative$generation$extendState = ExtendBurnerState.DEFAULT;
                updateBlockState();
            }
            return;
        }

        if (creative$generation$extendState == ExtendBurnerState.OVERLOAD) {
            if (remainingBurnTime > 0) {
                remainingBurnTime--;
            } else {
                remainingBurnTime = GENTLY_TICKS;
                activeFuel = BlazeBurnerBlockEntity.FuelType.NONE;
                creative$generation$extendState = ExtendBurnerState.GENTLY_TEMPORARY;
                updateBlockState();
            }
            return;
        }

        if (creative$generation$extendState == ExtendBurnerState.COLLAPSE) {
            if (remainingBurnTime > 0) {
                remainingBurnTime--;
            } else {
                remainingBurnTime = GENTLY_TICKS;
                activeFuel = BlazeBurnerBlockEntity.FuelType.NONE;
                creative$generation$extendState = ExtendBurnerState.GENTLY_TEMPORARY;
                updateBlockState();
            }
            return;
        }

        if (creative$generation$extendState == ExtendBurnerState.GENTLY_TEMPORARY) {
            if (remainingBurnTime > 0) {
                remainingBurnTime--;
            } else {
                creative$generation$extendState = ExtendBurnerState.DEFAULT;
                updateBlockState();
            }
            return;
        }

        if (creative$generation$extendState == ExtendBurnerState.GHOST) {
            if (remainingBurnTime > 0) {
                remainingBurnTime--;
            } else {
                activeFuel = BlazeBurnerBlockEntity.FuelType.NONE;
                creative$generation$extendState = ExtendBurnerState.GENTLY_PERMANENT;
                updateBlockState();
            }
        }


    }

    @Inject(method = "write", at = @At("HEAD"))
    private void inject_write(CompoundTag compound, boolean clientPacket, CallbackInfo ci) {
        if (creative$generation$potentialCatalyst)
            compound.putBoolean("PotentialCatalyst", true);


        if (!isCreative) {
            compound.putInt("ExtendState", creative$generation$extendState.ordinal());
            compound.putInt("BurningTicks", creative$generation$burningTicks);
            compound.putInt("CraftingTimes", creative$generation$ramainingCraftingTimes);

        }
    }

    @Inject(method = "read", at = @At("HEAD"))
    private void inject_read(CompoundTag compound, boolean clientPacket, CallbackInfo ci) {
        creative$generation$potentialCatalyst = compound.getBoolean("PotentialCatalyst");
        creative$generation$extendState = ExtendBurnerState.values()[compound.getInt("ExtendState")];
        creative$generation$burningTicks = compound.getInt("BurningTicks");
        creative$generation$ramainingCraftingTimes = compound.getInt("CraftingTimes");
    }

    @Inject(method = "spawnParticles", at = @At(value = "RETURN", ordinal = 3), locals = LocalCapture.CAPTURE_FAILHARD)
    private void inject_spawnParticles_beforeReturn(BlazeBurnerBlock.HeatLevel heatLevel, double burstMult, CallbackInfo ci, Random r, Vec3 c, Vec3 v, boolean empty, double yMotion, Vec3 v2) {
        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.NONE) && HeatLevelEx.isAtMost(heatLevel, BlazeBurnerBlock.HeatLevel.SEETHING)) {
            return;
        }
        SimpleParticleType particleType = null;
        if (heatLevel == HeatLevelEx.COLLAPSE) {
            particleType = CNGParticleTypes.COLLAPSE_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            particleType = CNGParticleTypes.OVERLOAD_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.HOT_HEATED) {
            particleType = CNGParticleTypes.HOT_HEATED_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            particleType = CNGParticleTypes.DRAGON_BREATH_BURNER_FLAME.get();
        } else if (heatLevel == HeatLevelEx.GHOST) {
            particleType = CNGParticleTypes.GHOST_BURNER_FLAME.get();
        } else if (HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.GENTLY)) {
            particleType = CNGParticleTypes.GENTLY_BURNER_FLAME.get();
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
        if (next == HeatLevelEx.COLLAPSE) {
            particleType = CNGParticleTypes.COLLAPSE_BURNER_FLAME.get();
        } else if (next == HeatLevelEx.OVERLOAD) {
            particleType = CNGParticleTypes.OVERLOAD_BURNER_FLAME.get();
        } else if (next == HeatLevelEx.HOT_HEATED) {
            particleType = CNGParticleTypes.HOT_HEATED_BURNER_FLAME.get();
        } else if (next.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
            particleType = ParticleTypes.SOUL_FIRE_FLAME;
        } else if (next == HeatLevelEx.DRAGON_BREATH) {
            particleType = CNGParticleTypes.DRAGON_BREATH_BURNER_FLAME.get();
        } else if (next == HeatLevelEx.GHOST) {
            particleType = CNGParticleTypes.GHOST_BURNER_FLAME.get();
        } else if (HeatLevelEx.isAtMost(next, HeatLevelEx.GENTLY_PERMANENT)) {
            particleType = CNGParticleTypes.GENTLY_BURNER_FLAME.get();
        }

        creative$generation$spawnParticleBurst(particleType);
    }

    @Inject(method = "applyCreativeFuel", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity;setBlockHeat(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void inject_applyCreativeFuel_setBlockHeat(CallbackInfo ci, BlazeBurnerBlock.HeatLevel next) {

        if (next == HeatLevelEx.GENTLY) {
            next = next.nextActiveLevel();
        }

        if (next == BlazeBurnerBlock.HeatLevel.FADING) {
            next = next.nextActiveLevel();
        }
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
