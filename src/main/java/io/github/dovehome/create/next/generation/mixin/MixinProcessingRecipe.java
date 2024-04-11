package io.github.dovehome.create.next.generation.mixin;


import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import io.github.dovehome.create.next.generation.data.IProcessingRecipeExt;
import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ProcessingRecipe.class, remap = false)
public abstract class MixinProcessingRecipe implements IProcessingRecipeExt {

    @Unique
    private boolean creative$generation$onlyHeat = false;
    @Override
    public boolean onlyHeat() {
        return creative$generation$onlyHeat;
    }

    @Override
    public void setOnlyHeat(boolean value) {
        creative$generation$onlyHeat = value;
    }

    @Inject(method = "writeAdditional(Lcom/google/gson/JsonObject;)V", at = @At("RETURN"))
    private void inject_writeAdditional(JsonObject json, CallbackInfo ci) {
        json.addProperty("onlyHeat", creative$generation$onlyHeat);
    }

    @Inject(method = "readAdditional(Lcom/google/gson/JsonObject;)V", at = @At("RETURN"))
    private void inject_readAdditional(JsonObject json, CallbackInfo ci) {
        if(json.has("onlyHeat")) {
            creative$generation$onlyHeat = json.get("onlyHeat").getAsBoolean();
            return;
        }
        creative$generation$onlyHeat = false;
    }

    @Inject(method = "writeAdditional(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
    private void inject_writeAdditional(FriendlyByteBuf buffer, CallbackInfo ci) {
        buffer.writeBoolean(creative$generation$onlyHeat);
    }

    @Inject(method = "readAdditional(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
    private void inject_readAdditional(FriendlyByteBuf buffer, CallbackInfo ci) {
        creative$generation$onlyHeat = buffer.readBoolean();
    }

}
