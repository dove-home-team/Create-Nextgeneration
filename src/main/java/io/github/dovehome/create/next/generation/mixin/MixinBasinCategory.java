package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.dovehome.create.next.generation.CNGTags;
import io.github.dovehome.create.next.generation.CreateNextGeneration;
import io.github.dovehome.create.next.generation.data.HeatConditionEx;
import io.github.dovehome.create.next.generation.data.IProcessingRecipeExt;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BasinCategory.class, remap = false)
public class MixinBasinCategory {


    @Inject(method = "setRecipe(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V",

            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/basin/BasinRecipe;getRequiredHeat()Lcom/simibubi/create/content/processing/recipe/HeatCondition;"), cancellable = true)
    private void inject_setRecipe(IRecipeLayoutBuilder builder, BasinRecipe recipe, IFocusGroup focuses, CallbackInfo ci) {
        ci.cancel();

        HeatCondition requiredHeat = recipe.getRequiredHeat();
        boolean onlyHeat = ((IProcessingRecipeExt) recipe).onlyHeat();

        if (requiredHeat != HeatCondition.NONE) {
            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 134, 81)
                    .addItemStack(AllBlocks.BLAZE_BURNER.asStack());
        }

        ItemStack heatCataylst = null;

        if (requiredHeat == HeatCondition.SUPERHEATED) {
            heatCataylst = AllItems.BLAZE_CAKE.asStack();
        }


        if (heatCataylst != null) {
            builder
                    .addSlot(RecipeIngredientRole.CATALYST, 153, 81)
                    .addItemStack(heatCataylst);
        }


    }
}
