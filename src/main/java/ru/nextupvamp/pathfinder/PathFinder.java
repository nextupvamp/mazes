package ru.nextupvamp.pathfinder;

import ru.nextupvamp.maze.Coordinate;
import ru.nextupvamp.maze.Maze;

import java.util.List;

public interface PathFinder {
    List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end);
}
