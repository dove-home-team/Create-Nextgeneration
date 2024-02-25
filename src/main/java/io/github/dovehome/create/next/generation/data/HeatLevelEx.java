package io.github.dovehome.create.next.generation.data;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

public final class HeatLevelEx {

    public static BlazeBurnerBlock.HeatLevel RAGE;
    public static BlazeBurnerBlock.HeatLevel OVERLOAD;
    public static BlazeBurnerBlock.HeatLevel EXTERMINATE;


    public static BlazeBurnerBlock.HeatLevel DRAGON_BREATH; // -4
    public static BlazeBurnerBlock.HeatLevel GHOST; // -3
    public static BlazeBurnerBlock.HeatLevel SMOOTH_PERMANENT; // -2
    public static BlazeBurnerBlock.HeatLevel SMOOTH; // -1

    public static int getActualIndex(BlazeBurnerBlock.HeatLevel heatLevel) {
        if(heatLevel.ordinal() > EXTERMINATE.ordinal()) {
            return heatLevel.ordinal() - SMOOTH.ordinal() - 1;
        }

        return heatLevel.ordinal();
    }

    public static boolean isAtMost(BlazeBurnerBlock.HeatLevel instance, BlazeBurnerBlock.HeatLevel most) {
        return getActualIndex(instance) <= getActualIndex(most);
    }


}
