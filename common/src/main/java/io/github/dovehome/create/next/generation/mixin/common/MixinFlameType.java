package io.github.dovehome.create.next.generation.mixin.common;

import com.simibubi.create.content.processing.burner.LitBlazeBurnerBlock;
import io.github.dovehome.create.next.generation.Constant;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Arrays;
@Debug(export = true)
@Mixin(value = LitBlazeBurnerBlock.FlameType.class, remap = false)
public class MixinFlameType {
    @SuppressWarnings("target")
    @Shadow
    @Final
    @Mutable
    private static LitBlazeBurnerBlock.FlameType[] $VALUES;
    @Invoker("<init>")
    public static LitBlazeBurnerBlock.FlameType init(String name, int ordinal) {
        throw new AssertionError();
    }

    static {
        for (String flameType : Constant.Config.blazeBurner.get().flameTypes) {
            creative$generation$add(flameType);
        }
    }


    @Unique
    private static void creative$generation$add(String key) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        $VALUES[ordinal] = init(key, ordinal);
    }
}
