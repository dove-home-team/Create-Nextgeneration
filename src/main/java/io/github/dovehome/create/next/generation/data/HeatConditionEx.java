package io.github.dovehome.create.next.generation.data;


import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;

public class HeatConditionEx {


    public static HeatCondition HOT_HEATED;
    public static HeatCondition OVERLOADED;
    public static HeatCondition COLLAPSED;


    public static HeatCondition DRAGON_BREATHING; // -4
    public static HeatCondition GHOST_HEATED; // -3
    public static HeatCondition GENTLY_HEATED; // -1


    public static boolean testHeat(HeatCondition condition, BlazeBurnerBlock.HeatLevel level, boolean onlyHeat) {
        if (condition == HeatCondition.NONE) {
            return true;
        }

        if (condition == GHOST_HEATED) {
            return level == HeatLevelEx.GHOST;
        }
        if (condition == DRAGON_BREATHING) {
            return level == HeatLevelEx.DRAGON_BREATH;
        }

        if (condition == COLLAPSED) {
            return level == HeatLevelEx.COLLAPSE;
        }

        if (condition == HeatCondition.SUPERHEATED) {
            return level == BlazeBurnerBlock.HeatLevel.SEETHING;
        }

        if (onlyHeat) {
            if (condition == HeatCondition.HEATED) {
                return level == BlazeBurnerBlock.HeatLevel.FADING || level == BlazeBurnerBlock.HeatLevel.KINDLED;
            }
            if (condition == HOT_HEATED) {
                return level == HeatLevelEx.HOT_HEATED;
            }
            if (condition == OVERLOADED) {
                return level == HeatLevelEx.OVERLOAD;
            }

            if (condition == GENTLY_HEATED) {
                return level == HeatLevelEx.GENTLY || level == HeatLevelEx.GENTLY_PERMANENT;
            }


            return false;
        }

        if (condition == HeatCondition.HEATED) {
            return level.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) && HeatLevelEx.isAtMost(level, BlazeBurnerBlock.HeatLevel.SEETHING);
        }

        if (condition == HOT_HEATED) {
            return level.isAtLeast(HeatLevelEx.HOT_HEATED) && HeatLevelEx.isAtMost(level, BlazeBurnerBlock.HeatLevel.SEETHING);
        }

        if (condition == GENTLY_HEATED) {
            return level.isAtLeast(HeatLevelEx.GENTLY_PERMANENT) && HeatLevelEx.isAtMost(level, BlazeBurnerBlock.HeatLevel.SEETHING);
        }

        if (condition == OVERLOADED) {
            return level.isAtLeast(HeatLevelEx.OVERLOAD) && HeatLevelEx.isAtMost(level, HeatLevelEx.COLLAPSE);
        }

        return false;
    }

}
