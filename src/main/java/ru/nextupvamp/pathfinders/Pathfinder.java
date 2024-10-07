package ru.nextupvamp.pathfinders;

import ru.nextupvamp.maze.Coordinate;
import ru.nextupvamp.maze.Maze;

import java.util.List;

public interface Pathfinder {
    List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end);

    int IMPLEMENTATIONS = 2;
}
