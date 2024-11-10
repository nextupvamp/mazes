package ru.nextupvamp.pathfinder;

import org.junit.jupiter.api.Test;
import ru.nextupvamp.TestSourceData;
import ru.nextupvamp.maze.Coordinate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathFinderTest {
    // There's only one way in a unicursal maze so paths of
    // two following methods will be the same
    @Test
    public void testDFSPathfinderOnUnicursalMaze() {
        PathFinder pathfinder = new DFSPathFinder();
        List<Coordinate> path = pathfinder.findPath(TestSourceData.UNICURSAL_MAZE,
                TestSourceData.UNICURSAL_MAZE.entrance(), TestSourceData.UNICURSAL_MAZE.exit());
        Collections.sort(path);
        assertEquals(TestSourceData.UNICURSAL_MAZE_DFS_PATH, path);
    }

    @Test
    public void testAStarPathfinderOnUnicuralMaze() {
        PathFinder pathfinder = new AStarPathFinder();
        List<Coordinate> path = pathfinder.findPath(TestSourceData.UNICURSAL_MAZE,
                TestSourceData.UNICURSAL_MAZE.entrance(), TestSourceData.UNICURSAL_MAZE.exit());
        Collections.sort(path);
        assertEquals(TestSourceData.UNICURSAL_MAZE_DFS_PATH, path);
    }

    // On a braid maze DFS returns random paths
    // so we'll just check if its path contains
    // both entrance and exit
    @Test
    public void testDFSPathfinderOnBraidMazeWithModifiers() {
        PathFinder pathfinder = new DFSPathFinder();
        List<Coordinate> path = pathfinder.findPath(TestSourceData.BRAID_MAZE_WITH_MODIFIERS,
                TestSourceData.BRAID_MAZE_WITH_MODIFIERS.entrance(),
                TestSourceData.BRAID_MAZE_WITH_MODIFIERS.exit());
        Collections.sort(path);
        assertTrue(path.contains(TestSourceData.BRAID_MAZE_WITH_MODIFIERS.entrance()) &&
                path.contains(TestSourceData.BRAID_MAZE_WITH_MODIFIERS.exit()));
    }

    // We test if algorithm finds an optimal path.
    // The test may fall if a diamond's value will be increased
    // or a jungle's value will be reduced.
    @Test
    public void testAStarPathfinderOnBraidMazeWithModifiers() {
        PathFinder pathfinder = new AStarPathFinder();
        List<Coordinate> path = pathfinder.findPath(TestSourceData.BRAID_MAZE_WITH_MODIFIERS,
                TestSourceData.BRAID_MAZE_WITH_MODIFIERS.entrance(),
                TestSourceData.BRAID_MAZE_WITH_MODIFIERS.exit());
        assertEquals(TestSourceData.BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH, path);
    }
}
