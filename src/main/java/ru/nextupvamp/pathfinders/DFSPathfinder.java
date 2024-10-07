package ru.nextupvamp.pathfinders;

import ru.nextupvamp.maze.Cell;
import ru.nextupvamp.maze.Coordinate;
import ru.nextupvamp.maze.Direction;
import ru.nextupvamp.maze.Maze;

import java.util.*;

public class DFSPathfinder implements Pathfinder {
    @Override
    public List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end) {
        Cell[][] grid = maze.grid();
        Deque<Coordinate> toVisit = new LinkedList<>();
        boolean[][] isVisited = new boolean[grid.length][grid[0].length];
        Map<Coordinate, Coordinate> parentMap = new HashMap<>();

        toVisit.push(start);
        while (!toVisit.isEmpty()) {
            Coordinate current = toVisit.pop();
            if (current.equals(end)) {
                return buildPath(parentMap, end);
            }

            int row = current.row();
            int col = current.col();
            if (!isVisited[row][col]) {
                isVisited[row][col] = true;
                if (!grid[row][col].walls().contains(Direction.NORTH) && !isVisited[row - 1][col]) {
                    Coordinate neighbour = new Coordinate(row - 1, col);
                    toVisit.push(neighbour);
                    parentMap.put(neighbour, current);
                }
                if (!grid[row][col].walls().contains(Direction.SOUTH) && !isVisited[row + 1][col]) {
                    Coordinate neighbour = new Coordinate(row + 1, col);
                    toVisit.push(neighbour);
                    parentMap.put(neighbour, current);
                }
                if (!grid[row][col].walls().contains(Direction.WEST) && !isVisited[row][col - 1]) {
                    Coordinate neighbour = new Coordinate(row, col - 1);
                    toVisit.push(neighbour);
                    parentMap.put(neighbour, current);
                }
                if (!grid[row][col].walls().contains(Direction.EAST) && !isVisited[row][col + 1]) {
                    Coordinate neighbour = new Coordinate(row, col + 1);
                    toVisit.push(neighbour);
                    parentMap.put(neighbour, current);
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Builds path from transitions map
     *
     * @param parentMap   transitions map
     * @param destination initial cell
     * @return list of path cells coordinates
     */
    private List<Coordinate> buildPath(Map<Coordinate, Coordinate> parentMap, Coordinate destination) {
        List<Coordinate> path = new ArrayList<>();
        for (Coordinate at = destination; at != null; at = parentMap.get(at)) {
            path.add(at);
        }

        return path;
    }
}