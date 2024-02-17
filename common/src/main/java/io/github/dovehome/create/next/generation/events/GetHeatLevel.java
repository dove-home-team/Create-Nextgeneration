package io.github.dovehome.create.next.generation.events;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import dev.latvian.mods.kubejs.event.EventJS;
import io.github.dovehome.create.next.generation.kjs.TagGetJS;

import java.util.LinkedList;

public class GetHeatLevel extends EventJS {
    public static final LinkedList<GetHeatLevelEvent> events = new LinkedList<>();
    public static final LinkedList<String> names = new LinkedList<>();
    @FunctionalInterface
    public interface GetHeatLevelEvent {
        BlazeBurnerBlock.HeatLevel getHeatLevel(BlazeBurnerBlock.HeatLevel level, String fuelTypeName);
    }

    public void listening(GetHeatLevelEvent event, String fuelTypeName) {
        events.add(event);
        names.add(fuelTypeName);
    }

    public TagGetJS tag() {
        return CreateNextGenerationEvents.tag;
    }
}
