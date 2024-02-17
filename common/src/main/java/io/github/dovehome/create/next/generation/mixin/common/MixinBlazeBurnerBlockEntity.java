package io.github.dovehome.create.next.generation.mixin.common;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import dev.latvian.mods.kubejs.core.ItemStackKJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import io.github.dovehome.create.next.generation.events.GetHeatLevel;
import io.github.dovehome.create.next.generation.events.TryUpdateFuel;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Debug(export = true)
@Mixin(value = BlazeBurnerBlockEntity.class, remap = false)
public abstract class MixinBlazeBurnerBlockEntity extends SmartBlockEntity {
    @Shadow
    protected BlazeBurnerBlockEntity.FuelType activeFuel;

    public MixinBlazeBurnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Inject(method = "tryUpdateFuel", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/AllTags$AllItemTags;matches(Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 1), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void tryUpdateFuelEvent(ItemStack itemStack, boolean forceOverflow, TransactionContext ctx, CallbackInfoReturnable<Boolean> cir, BlazeBurnerBlockEntity.FuelType newFuel, int newBurnTime, Integer fuel) {
        for (TryUpdateFuel.TryUpdateFuelEvent event : TryUpdateFuel.events) {
            Boolean b = event.tryUpdateFuelEvent(itemStack, forceOverflow, ctx, newFuel, newBurnTime, fuel);
            if (b != null) {
                cir.setReturnValue(b);
            }
        }
    }

    @Inject(method = "getHeatLevel", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlockEntity$FuelType;ordinal()I"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void getHeatLevel(CallbackInfoReturnable<BlazeBurnerBlock.HeatLevel> cir, BlazeBurnerBlock.HeatLevel level) {
        for (int i = 0; i < GetHeatLevel.events.size(); i++) {
            GetHeatLevel.GetHeatLevelEvent getHeatLevelEvent = GetHeatLevel.events.get(i);
            String name = GetHeatLevel.names.get(i);
            BlazeBurnerBlock.HeatLevel heatLevel = getHeatLevelEvent.getHeatLevel(level, name);
            if (heatLevel != null) {
                cir.setReturnValue(heatLevel);
            }
        }
    }
}
