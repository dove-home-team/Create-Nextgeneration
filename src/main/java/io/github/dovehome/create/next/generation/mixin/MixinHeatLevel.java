package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = BlazeBurnerBlock.HeatLevel.class, remap = false)
public abstract class MixinHeatLevel {

    @Final
    @Shadow
    @Mutable
    private static BlazeBurnerBlock.HeatLevel[] $VALUES;

    @Invoker("<init>")
    public static BlazeBurnerBlock.HeatLevel creative$generation$init(String name, int ordinal) {
        throw new AssertionError();
    }


    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void inject_clinit(CallbackInfo ci) {
        HeatLevelEx.HOT_HEATED = creative$generation$add("HOT_HEATED");
        HeatLevelEx.OVERLOAD = creative$generation$add("OVERLOAD");
        HeatLevelEx.COLLAPSE = creative$generation$add("COLLAPSE");

        HeatLevelEx.DRAGON_BREATH = creative$generation$add("DRAGON_BREATH");
        HeatLevelEx.GHOST = creative$generation$add("GHOST");
        HeatLevelEx.GENTLY_PERMANENT = creative$generation$add("GENTLY_PERMANENT");
        HeatLevelEx.GENTLY = creative$generation$add("GENTLY");
    }

    @Unique
    private static BlazeBurnerBlock.HeatLevel creative$generation$add(String key) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        BlazeBurnerBlock.HeatLevel ret = creative$generation$init(key, ordinal);
        $VALUES[ordinal] = ret;
        return ret;
    }

    @Inject(method = "isAtLeast", at = @At("HEAD"), cancellable = true)
    public void inject_isAtLeast(BlazeBurnerBlock.HeatLevel heatLevel, CallbackInfoReturnable<Boolean> cir) {
        boolean ret = HeatLevelEx.getActualIndex((BlazeBurnerBlock.HeatLevel) (Object) this) >= HeatLevelEx.getActualIndex(heatLevel);
        cir.setReturnValue(ret);
        cir.cancel();
    }

    @Inject(method = "nextActiveLevel", at = @At("HEAD"), cancellable = true)
    public void inject_nextActiveLevel(CallbackInfoReturnable<BlazeBurnerBlock.HeatLevel> cir) {
        int index = HeatLevelEx.getActualIndex((BlazeBurnerBlock.HeatLevel) (Object) this);
        BlazeBurnerBlock.HeatLevel ret = HeatLevelEx.byActualIndex((index % (HeatLevelEx.levelCount() - 1) + 1));
        cir.setReturnValue(ret);
    }

}
