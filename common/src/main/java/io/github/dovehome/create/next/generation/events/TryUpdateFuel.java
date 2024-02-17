package io.github.dovehome.create.next.generation.events;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import dev.latvian.mods.kubejs.core.ItemStackKJS;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import io.github.dovehome.create.next.generation.kjs.TagGetJS;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedList;

public class TryUpdateFuel extends EventJS {
    public static final LinkedList<TryUpdateFuelEvent> events = new LinkedList<>();

    @FunctionalInterface
    public interface TryUpdateFuelEvent {
        @SuppressWarnings("UnstableApiUsage")
        Boolean tryUpdateFuelEvent(ItemStack itemStack, boolean forceOverflow, TransactionContext ctx, BlazeBurnerBlockEntity.FuelType newFuel, int newBurnTime, Integer fuel);
    }



    public void listening(TryUpdateFuelEvent event) {
        events.add(event);
    }

    public TagGetJS tag() {
        return CreateNextGenerationEvents.tag;
    }
}
