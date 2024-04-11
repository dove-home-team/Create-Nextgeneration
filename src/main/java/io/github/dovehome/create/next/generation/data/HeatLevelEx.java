package io.github.dovehome.create.next.generation.data;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

public final class HeatLevelEx {

    public static BlazeBurnerBlock.HeatLevel HOT_HEATED; // 8
    public static BlazeBurnerBlock.HeatLevel OVERLOAD; // 10
    public static BlazeBurnerBlock.HeatLevel COLLAPSE; // 11


    public static BlazeBurnerBlock.HeatLevel DRAGON_BREATH; // 1
    public static BlazeBurnerBlock.HeatLevel GHOST; // 2
    public static BlazeBurnerBlock.HeatLevel GENTLY_PERMANENT; // 4
    public static BlazeBurnerBlock.HeatLevel GENTLY; // 5


    private static class Holder {
        private final int[] INDEXES = new int[BlazeBurnerBlock.HeatLevel.values().length];

        private final BlazeBurnerBlock.HeatLevel[] VALUES = new BlazeBurnerBlock.HeatLevel[BlazeBurnerBlock.HeatLevel.values().length];

        private Holder()
        {
            int i = 0;

            INDEXES[BlazeBurnerBlock.HeatLevel.NONE.ordinal()] = i++;

            INDEXES[DRAGON_BREATH.ordinal()] = i++;
            INDEXES[GHOST.ordinal()] = i++;

            INDEXES[BlazeBurnerBlock.HeatLevel.SMOULDERING.ordinal()] = i++;

            INDEXES[GENTLY_PERMANENT.ordinal()] = i++;
            INDEXES[GENTLY.ordinal()] = i++;

            INDEXES[BlazeBurnerBlock.HeatLevel.FADING.ordinal()] = i++;
            INDEXES[BlazeBurnerBlock.HeatLevel.KINDLED.ordinal()] = i++;

            INDEXES[HOT_HEATED.ordinal()] = i++;

            INDEXES[BlazeBurnerBlock.HeatLevel.SEETHING.ordinal()] = i++;

            INDEXES[OVERLOAD.ordinal()] = i++;
            INDEXES[COLLAPSE.ordinal()] = i++;

            if(i != BlazeBurnerBlock.HeatLevel.values().length) {
                throw new AssertionError();
            }

            for (BlazeBurnerBlock.HeatLevel value : BlazeBurnerBlock.HeatLevel.values()) {
                int ordinal = value.ordinal();
                int index = INDEXES[ordinal];
                VALUES[index] = value;
            }

        }

        private static final Holder INSTANCE = new Holder();

        public static Holder getInstance() {
            return INSTANCE;
        }
    }

    public static BlazeBurnerBlock.HeatLevel byActualIndex(int index) {
        return Holder.getInstance().VALUES[index];
    }

    public static int levelCount() {
        return Holder.getInstance().VALUES.length;
    }

    public static int getActualIndex(BlazeBurnerBlock.HeatLevel heatLevel) {
        int ordinal = heatLevel.ordinal();
        return Holder.getInstance().INDEXES[ordinal];
    }

    public static boolean isAtMost(BlazeBurnerBlock.HeatLevel instance, BlazeBurnerBlock.HeatLevel most) {
        return getActualIndex(instance) <= getActualIndex(most);
    }

    public static boolean isBurning(BlazeBurnerBlock.HeatLevel heatLevel) {
        return heatLevel != BlazeBurnerBlock.HeatLevel.NONE && heatLevel != BlazeBurnerBlock.HeatLevel.SMOULDERING;
    }

    public static final class Colors {

        public static final int COLLAPSE = 0x566462;
        public static final int OVERLOAD = 0x95C7C1;
        public static final int SUPERHEATED = 0x5E6DC8;
        public static final int HOT_HEATED = 0xF97841;
        public static final int HEATED = 0xE7CC42;
        public static final int GENTLY = 0xB5A469;
        public static final int GHOST = 0x658480;
        public static final int DRAGON_BREATH = 0xB840E4;
    }


}
