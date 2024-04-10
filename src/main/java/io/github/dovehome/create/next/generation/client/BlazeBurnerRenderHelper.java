package io.github.dovehome.create.next.generation.client;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;

public final class BlazeBurnerRenderHelper {


    public static SpriteShiftEntry getFlameSprite(BlazeBurnerBlock.HeatLevel heatLevel) {
        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return AllSpriteShifts.SUPER_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.HOT_HEATED) {
            return CNGSpriteShifts.HOT_HEATED_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return CNGSpriteShifts.OVERLOAD_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.COLLAPSE) {
            return CNGSpriteShifts.COLLAPSE_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return CNGSpriteShifts.DRAGON_BREATH_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return CNGSpriteShifts.GHOST_BURNER_FLAME;
        } else if (heatLevel == HeatLevelEx.GENTLY || heatLevel == HeatLevelEx.GENTLY_PERMANENT) {
            return CNGSpriteShifts.GENTLY_BURNER_FLAME;
        } else {
            return AllSpriteShifts.BURNER_FLAME;
        }
    }

    public static int getFlameSpeedLevel(BlazeBurnerBlock.HeatLevel heatLevel) {
        int speed;

        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
            speed = heatLevel.ordinal();
        } else if (HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.GHOST)) {
            speed = 4 + HeatLevelEx.getActualIndex(heatLevel);
        } else if (HeatLevelEx.isAtMost(heatLevel, HeatLevelEx.GENTLY)) {
            speed = 2;
        } else {
            speed = 3;
        }

        return speed;
    }

    public static float getFlameSpeed(BlazeBurnerBlock.HeatLevel heatLevel) {

        return 1 / 32f + 1 / 64f * getFlameSpeedLevel(heatLevel);
    }

    public static PartialModel getBurnerModel(BlazeBurnerBlock.HeatLevel heatLevel, boolean blockAbove) {
        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return blockAbove ? AllPartialModels.BLAZE_SUPER_ACTIVE : AllPartialModels.BLAZE_SUPER;
        } else if (heatLevel == HeatLevelEx.HOT_HEATED) {
            return blockAbove ? CNGPartialModels.BLAZE_HOT_HEATED_ACTIVE : CNGPartialModels.BLAZE_HOT_HEATED;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return blockAbove ? CNGPartialModels.BLAZE_OVERLOAD_ACTIVE : CNGPartialModels.BLAZE_OVERLOAD;
        } else if (heatLevel == HeatLevelEx.COLLAPSE) {
            return blockAbove ? CNGPartialModels.BLAZE_COLLAPSE_ACTIVE : CNGPartialModels.BLAZE_COLLAPSE;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return blockAbove ? CNGPartialModels.BLAZE_DRAGON_BREATH_ACTIVE : CNGPartialModels.BLAZE_DRAGON_BREATH;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return blockAbove ? CNGPartialModels.BLAZE_GHOST_ACTIVE : CNGPartialModels.BLAZE_GHOST;
        } else if (heatLevel == HeatLevelEx.GENTLY || heatLevel == HeatLevelEx.GENTLY_PERMANENT) {
            return blockAbove ? CNGPartialModels.BLAZE_GENTLY_ACTIVE : CNGPartialModels.BLAZE_GENTLY;
        } else if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            return blockAbove ? AllPartialModels.BLAZE_ACTIVE : AllPartialModels.BLAZE_IDLE;
        } else {
            return AllPartialModels.BLAZE_INERT;
        }
    }

    public static PartialModel getRodModel1(BlazeBurnerBlock.HeatLevel heatLevel) {

        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return AllPartialModels.BLAZE_BURNER_SUPER_RODS;
        } else if (heatLevel == HeatLevelEx.HOT_HEATED) {
            return CNGPartialModels.BLAZE_BURNER_HOT_HEATED_RODS;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return CNGPartialModels.BLAZE_BURNER_OVERLOAD_RODS;
        } else if (heatLevel == HeatLevelEx.COLLAPSE) {
            return CNGPartialModels.BLAZE_BURNER_COLLAPSE_RODS;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return CNGPartialModels.BLAZE_BURNER_DRAGON_BREATH_RODS;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return CNGPartialModels.BLAZE_BURNER_GHOST_RODS;
        } else if (heatLevel == HeatLevelEx.GENTLY || heatLevel == HeatLevelEx.GENTLY_PERMANENT) {
            return CNGPartialModels.BLAZE_BURNER_GENTLY_RODS;
        } else {
            return AllPartialModels.BLAZE_BURNER_RODS;
        }
    }

    public static PartialModel getRodModel2(BlazeBurnerBlock.HeatLevel heatLevel) {

        if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return AllPartialModels.BLAZE_BURNER_SUPER_RODS_2;
        } else if (heatLevel == HeatLevelEx.HOT_HEATED) {
            return CNGPartialModels.BLAZE_BURNER_HOT_HEATED_RODS_2;
        } else if (heatLevel == HeatLevelEx.OVERLOAD) {
            return CNGPartialModels.BLAZE_BURNER_OVERLOAD_RODS_2;
        } else if (heatLevel == HeatLevelEx.COLLAPSE) {
            return CNGPartialModels.BLAZE_BURNER_COLLAPSE_RODS_2;
        } else if (heatLevel == HeatLevelEx.DRAGON_BREATH) {
            return CNGPartialModels.BLAZE_BURNER_DRAGON_BREATH_RODS_2;
        } else if (heatLevel == HeatLevelEx.GHOST) {
            return CNGPartialModels.BLAZE_BURNER_GHOST_RODS_2;
        } else if (heatLevel == HeatLevelEx.GENTLY || heatLevel == HeatLevelEx.GENTLY_PERMANENT) {
            return CNGPartialModels.BLAZE_BURNER_GENTLY_RODS_2;
        } else {
            return AllPartialModels.BLAZE_BURNER_RODS_2;
        }
    }
}
