package io.github.dovehome.create.next.generation.mixin;


import dev.latvian.mods.kubejs.create.ProcessingRecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import io.github.dovehome.create.next.generation.data.ProcessingRecipeJSExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ProcessingRecipeJS.class, remap = false)
public abstract class MixinProcessingRecipeJS extends RecipeJS implements ProcessingRecipeJSExt {


    @Shadow
    public abstract ProcessingRecipeJS heatRequirement(String req);

    @Override
    public ProcessingRecipeJS collapseHeated() {
        return heatRequirement("collapsed");
    }

    @Override
    public ProcessingRecipeJS overloadHeated() {
        return heatRequirement("overloaded");
    }

    @Override
    public ProcessingRecipeJS hotHeated() {
        return heatRequirement("hot_heated");
    }

    @Override
    public ProcessingRecipeJS gentlyHeated() {
        return heatRequirement("gently_heated");
    }

    @Override
    public ProcessingRecipeJS ghostHeated() {
        return heatRequirement("ghost_heated");
    }

    @Override
    public ProcessingRecipeJS dragonBreathing() {
        return heatRequirement("dragon_breathing");
    }

    @Override
    public ProcessingRecipeJS heatIncompatible() {
        this.json.addProperty("onlyHeat", true);
        return (ProcessingRecipeJS) (Object) this;

    }
}
