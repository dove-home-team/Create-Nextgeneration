package io.github.dovehome.create.next.generation.mixin;



import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        HeatLevelEx.RAGE = creative$generation$add("RAGE");
        HeatLevelEx.OVERLOAD = creative$generation$add("OVERLOAD");
        HeatLevelEx.EXTERMINATE = creative$generation$add("EXTERMINATE");

        HeatLevelEx.DRAGON_BREATH = creative$generation$add("DRAGON_BREATH");
        HeatLevelEx.GHOST = creative$generation$add("GHOST");
        HeatLevelEx.SMOOTH_PERMANENT = creative$generation$add("SMOOTH_PERMANENT");
        HeatLevelEx.SMOOTH = creative$generation$add("SMOOTH");
    }

    @Unique
    private static BlazeBurnerBlock.HeatLevel creative$generation$add(String key) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        BlazeBurnerBlock.HeatLevel ret = creative$generation$init(key, ordinal);
        $VALUES[ordinal] = ret;
        return ret;
    }

    /**
     * @author WarmthDawn
     * @reason too simple to inject
     */
    @Overwrite
    public boolean isAtLeast(BlazeBurnerBlock.HeatLevel heatLevel) {
        return  HeatLevelEx.getActualIndex((BlazeBurnerBlock.HeatLevel) (Object) this) >= HeatLevelEx.getActualIndex(heatLevel);
    }

}
