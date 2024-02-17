package io.github.dovehome.create.next.generation.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventGroupWrapper;
import dev.latvian.mods.kubejs.event.EventHandler;
import io.github.dovehome.create.next.generation.kjs.TagGetJS;

public interface CreateNextGenerationEvents {
    EventGroup group = EventGroup.of("CreateNextGenerationEvents");
    EventHandler heatLevel = group.startup("getHeatLevel", () -> GetHeatLevel.class);
    EventHandler tryUpdateLevel = group.startup("getHeatLevel", () -> TryUpdateFuel.class);
    TagGetJS tag = new TagGetJS();

}
