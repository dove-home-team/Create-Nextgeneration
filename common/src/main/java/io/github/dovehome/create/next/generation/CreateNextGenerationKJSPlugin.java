package io.github.dovehome.create.next.generation;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import io.github.dovehome.create.next.generation.events.CreateNextGenerationEvents;
import io.github.dovehome.create.next.generation.events.GetHeatLevel;
import io.github.dovehome.create.next.generation.events.TryUpdateFuel;

public class CreateNextGenerationKJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        CreateNextGenerationEvents.group.register();
    }

    @Override
    public void afterInit() {
        CreateNextGenerationEvents.heatLevel.post(ScriptType.STARTUP, new GetHeatLevel());
        CreateNextGenerationEvents.tryUpdateLevel.post(ScriptType.STARTUP, new TryUpdateFuel());
    }
}
