package io.github.dovehome.create.next.generation.kjs;


import com.simibubi.create.AllTags;
import dev.latvian.mods.kubejs.core.ItemKJS;
import dev.latvian.mods.kubejs.core.ItemStackKJS;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagGetJS {
    public AllTags.AllItemTags getTags(String name) {
        return AllTags.AllItemTags.valueOf(name);
    }

    public boolean matches(ItemKJS item, String name) {
        return AllTags.AllItemTags.valueOf(name).matches(item.kjs$self());
    }

    public boolean matches(ItemStackKJS stack, String name) {
        return AllTags.AllItemTags.valueOf(name).matches(stack.kjs$self());
    }

    public boolean alwaysDatagen(String name) {
        return AllTags.AllItemTags.valueOf(name).alwaysDatagen;
    }

    public TagKey<Item> tag(String name) {
        return AllTags.AllItemTags.valueOf(name).tag;
    }
}
