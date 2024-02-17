package io.github.dovehome.create.next.generation.mixin.common;

import com.simibubi.create.AllTags;
import io.github.dovehome.create.next.generation.Constant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Arrays;

@Debug(export = true)
@Mixin(value = AllTags.AllItemTags.class, remap = false)
public class MixinAllItemTags {
    @Final
    @SuppressWarnings("target")
    @Shadow
    @Mutable
    private static AllTags.AllItemTags[] $VALUES;

    static {
        Constant
                .Config
                .blazeBurner
                .get()
                .itemTags
                .tags
                .forEach(MixinAllItemTags::creative$generation$add);
    }

    @Unique
    private static void creative$generation$add(@NotNull String name, @Nullable String path) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES,ordinal + 1);

        if (path == null || path.isEmpty())
            $VALUES[ordinal] = init(name, ordinal, AllTags.NameSpace.MOD);
        $VALUES[ordinal] = init(name, ordinal, AllTags.NameSpace.MOD, path);
    }

    @Invoker("<init>")
    public static AllTags.AllItemTags init(String name, int ordinal, AllTags.NameSpace namespace, String path) {
        throw new RuntimeException();
    }

    @Invoker("<init>")
    public static AllTags.AllItemTags init(String name, int ordinal, AllTags.NameSpace namespace) {
        throw new RuntimeException();
    }
}
