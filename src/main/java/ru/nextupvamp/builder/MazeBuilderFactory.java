package ru.nextupvamp.builder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MazeBuilderFactory {
    public static MazeBuilder getMazeBuilder(MazeBuilderType builderType, int height, int width) {
        return switch (builderType) {
            case PRIM_ALGORITHM_MAZE_BUILDER -> new PrimAlgorithmMazeBuilder(height, width);
            case WILSON_ALGORITHM_MAZE_BUILDER -> new WilsonAlgorithmMazeBuilder(height, width);
        };
    }
}
