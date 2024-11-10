package ru.nextupvamp.pathfinder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathFinderFactory {
    public static PathFinder getPathFinder(PathFinderType pathFinderType) {
        return switch (pathFinderType) {
            case A_STAR_PATH_FINDER -> new AStarPathFinder();
            case DFS_PATH_FINDER -> new DFSPathFinder();
        };
    }
}
