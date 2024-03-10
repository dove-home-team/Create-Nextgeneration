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

    public static final PartialModel BLAZE_RAGE = block("blaze_burner/blaze/rage");
    public static final PartialModel BLAZE_RAGE_ACTIVE = block("blaze_burner/blaze/rage_active");

    public static final PartialModel BLAZE_BURNER_RAGE_RODS = block("blaze_burner/rage_rods_small");
    public static final PartialModel BLAZE_BURNER_RAGE_RODS_2 = block("blaze_burner/rage_rods_large");

    public static final PartialModel BLAZE_OVERLOAD = block("blaze_burner/blaze/overload");
    public static final PartialModel BLAZE_OVERLOAD_ACTIVE = block("blaze_burner/blaze/overload_active");

    public static final PartialModel BLAZE_BURNER_OVERLOAD_RODS = block("blaze_burner/overload_rods_small");
    public static final PartialModel BLAZE_BURNER_OVERLOAD_RODS_2 = block("blaze_burner/overload_rods_large");


    public static final PartialModel BLAZE_EXTERMINATE = block("blaze_burner/blaze/exterminate");
    public static final PartialModel BLAZE_EXTERMINATE_ACTIVE = block("blaze_burner/blaze/exterminate_active");

    public static final PartialModel BLAZE_BURNER_EXTERMINATE_RODS = block("blaze_burner/exterminate_rods_small");
    public static final PartialModel BLAZE_BURNER_EXTERMINATE_RODS_2 = block("blaze_burner/exterminate_rods_large");


    public static final PartialModel BLAZE_DRAGON_BREATH = block("blaze_burner/blaze/dragon_breath");
    public static final PartialModel BLAZE_DRAGON_BREATH_ACTIVE = block("blaze_burner/blaze/dragon_breath_active");

    public static final PartialModel BLAZE_BURNER_DRAGON_BREATH_RODS = block("blaze_burner/dragon_breath_rods_small");
    public static final PartialModel BLAZE_BURNER_DRAGON_BREATH_RODS_2 = block("blaze_burner/dragon_breath_rods_large");

    public static final PartialModel BLAZE_GHOST = block("blaze_burner/blaze/ghost");
    public static final PartialModel BLAZE_GHOST_ACTIVE = block("blaze_burner/blaze/ghost_active");

    public static final PartialModel BLAZE_BURNER_GHOST_RODS = block("blaze_burner/ghost_rods_small");
    public static final PartialModel BLAZE_BURNER_GHOST_RODS_2 = block("blaze_burner/ghost_rods_large");


    public static final PartialModel BLAZE_SMOOTH = block("blaze_burner/blaze/smooth");
    public static final PartialModel BLAZE_SMOOTH_ACTIVE = block("blaze_burner/blaze/smooth_active");

    public static final PartialModel BLAZE_BURNER_SMOOTH_RODS = block("blaze_burner/smooth_rods_small");
    public static final PartialModel BLAZE_BURNER_SMOOTH_RODS_2 = block("blaze_burner/smooth_rods_large");


}
