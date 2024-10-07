package ru.nextupvamp.factory;

import lombok.experimental.UtilityClass;
import ru.nextupvamp.maze.Maze;

@UtilityClass
public class MazeFactory {
    public static Maze getMaze(Builder builder, int height, int width) {
        return builder.build(height, width);
    }
}
