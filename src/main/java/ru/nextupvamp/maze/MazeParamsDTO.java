package ru.nextupvamp.maze;

public record MazeParamsDTO(
        int height,
        int width,
        boolean braid,
        boolean modifiers,
        int modifiersProbability,
        Coordinate entrance,
        Coordinate exit) {
}
