package ru.nextupvamp.builder;

import ru.nextupvamp.maze.Cell;
import ru.nextupvamp.maze.Direction;
import ru.nextupvamp.maze.Maze;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PrimAlgorithmMazeBuilder extends MazeBuilder {
    private final SecureRandom random = new SecureRandom();

    public PrimAlgorithmMazeBuilder(int height, int width) {
        super(height, width);
    }

    @Override
    public Maze build() {
        super.initGrid();

        int row = random.nextInt(height);
        int col = random.nextInt(width);
        List<Cell> frontier = initFrontier(row, col);

        executePrimAlgorithmLoop(frontier);

        Maze maze = new Maze(height, width, grid);
        maze.built(true);
        return maze;
    }

    /**
     * Initializes frontier around cell. Frontier is the adjacent
     * unvisited cells around visited ones.
     *
     * @param row row of the cell in the grid
     * @param col column of the cell in the grid
     * @return List of frontier cells.
     */
    private List<Cell> initFrontier(int row, int col) {
        List<Cell> frontier = new ArrayList<>();

        grid[row][col].visited(true);

        updateFrontier(frontier, row, col);

        return frontier;
    }

    /**
     * The main loop of the Prim's algorithm.
     *
     * @param frontier frontier of the maze
     */
    private void executePrimAlgorithmLoop(List<Cell> frontier) {
        while (!frontier.isEmpty()) {
            int index = random.nextInt(frontier.size());
            Cell cell = frontier.remove(index);

            // It was made for more complexity.
            // The algorithm won't remove wall from the visited cells.
            // That increases the number of walls.
            if (cell.visited()) {
                continue;
            }

            int cellRow = cell.coordinate().row();
            int cellCol = cell.coordinate().col();

            List<Cell> neighbors = findNeighbours(cellRow, cellCol);

            removeEdgeWithRandomNeighbour(neighbors, cellRow, cellCol);

            grid[cellRow][cellCol].visited(true);
            updateFrontier(frontier, cellRow, cellCol);
        }
    }

    /**
     * Finds visited neighbours of the cell.
     *
     * @param row row of the cell in the grid
     * @param col column of the cell in the grid
     * @return List of the adjacent visited cells.
     */
    private List<Cell> findNeighbours(int row, int col) {
        List<Cell> neighbors = new ArrayList<>();

        if (row > 0 && grid[row - 1][col].visited()) {
            neighbors.add(grid[row - 1][col]);
        }
        if (col > 0 && grid[row][col - 1].visited()) {
            neighbors.add(grid[row][col - 1]);
        }
        if (row < height - 1 && grid[row + 1][col].visited()) {
            neighbors.add(grid[row + 1][col]);
        }
        if (col < width - 1 && grid[row][col + 1].visited()) {
            neighbors.add(grid[row][col + 1]);
        }

        return neighbors;
    }

    /**
     * Removes wall with random neighbour from neighbours list.
     * That makes unvisited cell reachable from visited ones.
     *
     * @param neighbors list of neighbours
     * @param cellRow   row of the unvisited cell in the grid
     * @param cellCol   column of the unvisited cell in the grid
     */
    private void removeEdgeWithRandomNeighbour(List<Cell> neighbors, int cellRow, int cellCol) {
        int index = random.nextInt(neighbors.size());
        Cell neighbor = neighbors.remove(index);
        int neighborRow = neighbor.coordinate().row();
        int neighborCol = neighbor.coordinate().col();

        Direction direction;
        if (neighborRow > cellRow) {
            direction = Direction.SOUTH;
        } else if (neighborRow < cellRow) {
            direction = Direction.NORTH;
        } else if (neighborCol > cellCol) {
            direction = Direction.EAST;
        } else {
            direction = Direction.WEST;
        }

        carveWay(grid, cellRow, cellCol, direction);
    }

    /**
     * Updates frontier according to changes in the grid.
     *
     * @param frontier frontier list
     * @param cellRow  row of the last visited cell
     * @param cellCol  column of the last visited cell
     */
    private void updateFrontier(List<Cell> frontier, int cellRow, int cellCol) {

        if (cellRow > 0 && !grid[cellRow - 1][cellCol].visited()) {
            frontier.add(grid[cellRow - 1][cellCol]);
        }
        if (cellCol > 0 && !grid[cellRow][cellCol - 1].visited()) {
            frontier.add(grid[cellRow][cellCol - 1]);
        }
        if (cellRow < height - 1 && !grid[cellRow + 1][cellCol].visited()) {
            frontier.add(grid[cellRow + 1][cellCol]);
        }
        if (cellCol < width - 1 && !grid[cellRow][cellCol + 1].visited()) {
            frontier.add(grid[cellRow][cellCol + 1]);
        }
    }
}
