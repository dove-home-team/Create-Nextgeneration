package io.github.dovehome.create.next.generation.mixin.common;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.github.dovehome.create.next.generation.Constant;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Arrays;
@Debug(export = true)
@Mixin(value = BlazeBurnerBlockEntity.FuelType.class, remap = false)
public class MixinFuelType {
    @Final
    @SuppressWarnings("target")
    @Shadow
    @Mutable
    private static BlazeBurnerBlockEntity.FuelType[] $VALUES;
    @Invoker("<init>")
    public static BlazeBurnerBlockEntity.FuelType init(String name, int ordinal) {
        throw new AssertionError();
    }


    static {
        for (String fuelType : Constant.Config.blazeBurner.get().fuelTypes) {
            creative$generation$add(fuelType);
        }
    }

    @Unique
    private static void creative$generation$add(String key) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        $VALUES[ordinal] = init(key, ordinal);
    }
}
