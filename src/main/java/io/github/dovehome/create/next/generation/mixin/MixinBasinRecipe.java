package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.dovehome.create.next.generation.data.HeatConditionEx;
import io.github.dovehome.create.next.generation.data.IProcessingRecipeExt;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BasinRecipe.class, remap = false)
public class MixinBasinRecipe {


    @Unique
    private static final ThreadLocal<Recipe<?>> creative$generation$recipeThreadLocal = new ThreadLocal<>();

    @Inject(method = "apply(Lcom/simibubi/create/content/processing/basin/BasinBlockEntity;Lnet/minecraft/world/item/crafting/Recipe;Z)Z",
            at = @At("HEAD"))
    private static void inject_apply_begin(BasinBlockEntity basin, Recipe<?> recipe, boolean test, CallbackInfoReturnable<Boolean> cir) {
        creative$generation$recipeThreadLocal.set(recipe);
    }

    @Inject(method = "apply(Lcom/simibubi/create/content/processing/basin/BasinBlockEntity;Lnet/minecraft/world/item/crafting/Recipe;Z)Z",
            at = @At("RETURN"))
    private static void inject_apply_end(BasinBlockEntity basin, Recipe<?> recipe, boolean test, CallbackInfoReturnable<Boolean> cir) {
        creative$generation$recipeThreadLocal.remove();
    }

    @Redirect(method = "apply(Lcom/simibubi/create/content/processing/basin/BasinBlockEntity;Lnet/minecraft/world/item/crafting/Recipe;Z)Z",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/recipe/HeatCondition;testBlazeBurner(Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;)Z"))
    private static boolean redirect_apply_testBlazeBurner(HeatCondition instance, BlazeBurnerBlock.HeatLevel level) {
        Recipe<?> recipe = creative$generation$recipeThreadLocal.get();
        boolean onlyHeat = ((IProcessingRecipeExt) recipe).onlyHeat();
        return HeatConditionEx.testHeat(instance, level, onlyHeat);
    }
}
