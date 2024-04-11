package io.github.dovehome.create.next.generation.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.dovehome.create.next.generation.data.HeatConditionEx;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = HeatCondition.class, remap = false)
public abstract class MixinHeatCondition {

    @Mutable
    @Shadow
    @Final
    private static HeatCondition[] $VALUES;

    @Invoker("<init>")
    public static HeatCondition creative$generation$init(String name, int ordinal, int color) {
        throw new AssertionError();
    }


    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void inject_clinit(CallbackInfo ci) {
        HeatConditionEx.DRAGON_BREATHING = creative$generation$add("DRAGON_BREATHING", HeatLevelEx.Colors.DRAGON_BREATH);
        HeatConditionEx.GHOST_HEATED = creative$generation$add("GHOST_HEATED", HeatLevelEx.Colors.GHOST);
        HeatConditionEx.GENTLY_HEATED = creative$generation$add("GENTLY_HEATED", HeatLevelEx.Colors.GENTLY);
        HeatConditionEx.HOT_HEATED = creative$generation$add("HOT_HEATED", HeatLevelEx.Colors.HOT_HEATED);
        HeatConditionEx.OVERLOADED = creative$generation$add("OVERLOADED", HeatLevelEx.Colors.OVERLOAD);
        HeatConditionEx.COLLAPSED = creative$generation$add("COLLAPSED", HeatLevelEx.Colors.COLLAPSE);
    }

    @Unique
    private static HeatCondition creative$generation$add(String key, int color) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        HeatCondition ret = creative$generation$init(key, ordinal, color);
        $VALUES[ordinal] = ret;
        return ret;
    }


    /**
     * @author WarmthDawn
     * @reason the heat level changed completely
     */
    @Overwrite
    public boolean testBlazeBurner(BlazeBurnerBlock.HeatLevel level) {
        HeatCondition thisObj = (HeatCondition) (Object) this;
        return HeatConditionEx.testHeat(thisObj, level, false);
    }

    @Inject(method = "visualizeAsBlazeBurner", at = @At("HEAD"), cancellable = true)
    public void inject_visualizeAsBlazeBurner(CallbackInfoReturnable<BlazeBurnerBlock.HeatLevel> cir) {
        HeatCondition thisObj = (HeatCondition) (Object) this;
        if (thisObj == HeatConditionEx.HOT_HEATED) {
            cir.setReturnValue(HeatLevelEx.HOT_HEATED);
        } else if (thisObj == HeatConditionEx.OVERLOADED) {
            cir.setReturnValue(HeatLevelEx.OVERLOAD);
        } else if (thisObj == HeatConditionEx.COLLAPSED) {
            cir.setReturnValue(HeatLevelEx.COLLAPSE);
        } else if (thisObj == HeatConditionEx.DRAGON_BREATHING) {
            cir.setReturnValue(HeatLevelEx.DRAGON_BREATH);
        } else if (thisObj == HeatConditionEx.GHOST_HEATED) {
            cir.setReturnValue(HeatLevelEx.GHOST);
        } else if (thisObj == HeatConditionEx.GENTLY_HEATED) {
            cir.setReturnValue(HeatLevelEx.GENTLY);
        } else {
            // use default logic
            return;
        }
        cir.cancel();

    }
}
