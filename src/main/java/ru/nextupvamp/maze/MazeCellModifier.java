package ru.nextupvamp.maze;

public enum MazeCellModifier {
    DEFAULT, JUNGLE, DIAMOND;

    // Increasing value causes increasing cost of transition.
    // The pivot is the default value (1). That's why
    // jungle's one is 3 and the diamond's one is -2.
    public static final int DEFAULT_MODIFIER_VALUE = 1;
    public static final int JUNGLE_MODIFIER_VALUE = 3;
    public static final int DIAMOND_MODIFIER_VALUE = -2;
}
