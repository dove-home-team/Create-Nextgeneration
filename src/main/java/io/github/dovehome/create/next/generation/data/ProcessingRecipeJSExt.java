package io.github.dovehome.create.next.generation.data;

import dev.latvian.mods.kubejs.create.ProcessingRecipeJS;

public interface ProcessingRecipeJSExt {
    ProcessingRecipeJS collapseHeated();
    ProcessingRecipeJS overloadHeated();

    ProcessingRecipeJS hotHeated();
    ProcessingRecipeJS gentlyHeated();

    ProcessingRecipeJS ghostHeated();

    ProcessingRecipeJS dragonBreathing();

    ProcessingRecipeJS heatIncompatible();


}
