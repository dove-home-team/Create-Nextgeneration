package io.github.dovehome.create.next.generation;

import com.simibubi.create.Create;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;

public class CNGTags {

    private static <T extends IForgeRegistryEntry<T>> TagKey<T> optionalTag(IForgeRegistry<T> registry,
                                                                            ResourceLocation id) {

        return registry.tags()
                .createOptionalTagKey(id, Collections.emptySet());
    }

    private static TagKey<Item> optionalItemTag(ResourceLocation id) {
        return optionalTag(ForgeRegistries.ITEMS, id);
    }


    public static final TagKey<Item> BLAZE_BURNER_CATALYST_POTENTIAL = optionalItemTag(new ResourceLocation(CreateNextGeneration.MODID, "blaze_burner_catalyst/potential"));
    public static final TagKey<Item> BLAZE_BURNER_CATALYST_SMOOTH = optionalItemTag(new ResourceLocation(CreateNextGeneration.MODID, "blaze_burner_catalyst/smooth"));
    public static final TagKey<Item> BLAZE_BURNER_CATALYST_SMOOTH_WAKEUP = optionalItemTag(new ResourceLocation(CreateNextGeneration.MODID, "blaze_burner_catalyst/smooth_wakeup"));

    public static final TagKey<Item> BLAZE_BURNER_FUEL_GHOST = optionalItemTag(new ResourceLocation(CreateNextGeneration.MODID, "blaze_burner_fuel/ghost"));
    public static final TagKey<Item> BLAZE_BURNER_FUEL_DRAGON_BREATH = optionalItemTag(new ResourceLocation(CreateNextGeneration.MODID, "blaze_burner_fuel/dragon_breath"));


}
