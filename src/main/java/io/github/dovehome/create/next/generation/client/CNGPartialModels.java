package io.github.dovehome.create.next.generation.client;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;
import io.github.dovehome.create.next.generation.CreateNextGeneration;
import net.minecraft.resources.ResourceLocation;

public class CNGPartialModels {
    public static void init() {
    }

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(CreateNextGeneration.MODID, "block/" + path));
    }

    public static final PartialModel BLAZE_HOT_HEATED = block("blaze_burner/blaze/hot_heated");
    public static final PartialModel BLAZE_HOT_HEATED_ACTIVE = block("blaze_burner/blaze/hot_heated_active");

    public static final PartialModel BLAZE_BURNER_HOT_HEATED_RODS = block("blaze_burner/hot_heated_rods_small");
    public static final PartialModel BLAZE_BURNER_HOT_HEATED_RODS_2 = block("blaze_burner/hot_heated_rods_large");

    public static final PartialModel BLAZE_OVERLOAD = block("blaze_burner/blaze/overload");
    public static final PartialModel BLAZE_OVERLOAD_ACTIVE = block("blaze_burner/blaze/overload_active");

    public static final PartialModel BLAZE_BURNER_OVERLOAD_RODS = block("blaze_burner/overload_rods_small");
    public static final PartialModel BLAZE_BURNER_OVERLOAD_RODS_2 = block("blaze_burner/overload_rods_large");


    public static final PartialModel BLAZE_COLLAPSE = block("blaze_burner/blaze/collapse");
    public static final PartialModel BLAZE_COLLAPSE_ACTIVE = block("blaze_burner/blaze/collapse_active");

    public static final PartialModel BLAZE_BURNER_COLLAPSE_RODS = block("blaze_burner/collapse_rods_small");
    public static final PartialModel BLAZE_BURNER_COLLAPSE_RODS_2 = block("blaze_burner/collapse_rods_large");


    public static final PartialModel BLAZE_DRAGON_BREATH = block("blaze_burner/blaze/dragon_breath");
    public static final PartialModel BLAZE_DRAGON_BREATH_ACTIVE = block("blaze_burner/blaze/dragon_breath_active");

    public static final PartialModel BLAZE_BURNER_DRAGON_BREATH_RODS = block("blaze_burner/dragon_breath_rods_small");
    public static final PartialModel BLAZE_BURNER_DRAGON_BREATH_RODS_2 = block("blaze_burner/dragon_breath_rods_large");

    public static final PartialModel BLAZE_GHOST = block("blaze_burner/blaze/ghost");
    public static final PartialModel BLAZE_GHOST_ACTIVE = block("blaze_burner/blaze/ghost_active");

    public static final PartialModel BLAZE_BURNER_GHOST_RODS = block("blaze_burner/ghost_rods_small");
    public static final PartialModel BLAZE_BURNER_GHOST_RODS_2 = block("blaze_burner/ghost_rods_large");


    public static final PartialModel BLAZE_GENTLY = block("blaze_burner/blaze/gently");
    public static final PartialModel BLAZE_GENTLY_ACTIVE = block("blaze_burner/blaze/gently_active");

    public static final PartialModel BLAZE_BURNER_GENTLY_RODS = block("blaze_burner/gently_rods_small");
    public static final PartialModel BLAZE_BURNER_GENTLY_RODS_2 = block("blaze_burner/gently_rods_large");


}
