package ru.nextupvamp.factory;

import ru.nextupvamp.maze.Cell;
import ru.nextupvamp.maze.Direction;
import ru.nextupvamp.maze.Maze;

public class WilsonAlgorithmMazeBuilder implements Builder {
    private Maze maze;

    @Override
    public Maze build(int height, int width) {
        maze = new Maze(height, width);
        maze.initGrid();

        executeWilsonAlgorithmLoop();

        maze.built(true);
        return maze;
    }

    /**
     * The main loop of the Wilson's algorithm
     */
    private void executeWilsonAlgorithmLoop() {
        Cell[][] grid = maze.grid();
        int height = maze.height();
        int width = maze.width();
        int notVisitedCells = height * width;

        // Visit random cell
        grid[RANDOM.nextInt(height)][RANDOM.nextInt(width)].visited(true);
        --notVisitedCells;

        while (notVisitedCells > 0) {
            int sourceRow = RANDOM.nextInt(height);
            int sourceCol = RANDOM.nextInt(width);

            walkToTheVisitedCell(sourceRow, sourceCol);

            notVisitedCells = carveWayToTheVisitedCell(sourceRow, sourceCol, notVisitedCells);
        }
    }

    /**
     * In the method we create walker on some coordinates.
     * It walks in random direction towards any visited cell.
     * Method marks direction of walker's moves in cells on its way.
     *
     * @param row row of the cell where the walker starts
     * @param col column of the cell where the walker starts
     */
    private void walkToTheVisitedCell(int row, int col) {
        Cell[][] grid = maze.grid();
        Cell walker = grid[row][col];
        int newRow = row;
        int newCol = col;

        while (!walker.visited()) {
            // choose random direction
            Direction walkDirection = Direction.values()[RANDOM.nextInt(Direction.values().length)];

            // and walk in that direction leaving it on the previous cell
            switch (walkDirection) {
                case NORTH:
                    if (newRow > 0) {
                        grid[newRow][newCol].direction(Direction.NORTH);
                        walker = grid[--newRow][newCol];
                    }
                    break;
                case SOUTH:
                    if (newRow < maze.height() - 1) {
                        grid[newRow][newCol].direction(Direction.SOUTH);
                        walker = grid[++newRow][newCol];
                    }
                    break;
                case WEST:
                    if (newCol > 0) {
                        grid[newRow][newCol].direction(Direction.WEST);
                        walker = grid[newRow][--newCol];
                    }
                    break;
                case EAST:
                    if (newCol < maze.width() - 1) {
                        grid[newRow][newCol].direction(Direction.EAST);
                        walker = grid[newRow][++newCol];
                    }
                    break;
            }
        }
    }

    /**
     * Method carves the way from source to the walker through the edges.
     * It marks cells as visited on its way and changes unvisited cells number.
     *
     * @param sourceRow       row of the source cell in the grid
     * @param sourceCol       column of the source cell in the grid
     * @param notVisitedCells amount of unvisited cells
     * @return changed amount of unvisited cells
     */
    private int carveWayToTheVisitedCell(int sourceRow, int sourceCol, int notVisitedCells) {
        Cell[][] grid = maze.grid();
        int row = sourceRow;
        int col = sourceCol;
        int notVisited = notVisitedCells;

        while (!grid[row][col].visited()) {
            grid[row][col].visited(true);
            --notVisited;
            Direction direction = grid[row][col].direction();
            if (direction != null) {
                maze.carveWay(row, col, direction);
                switch (direction) {
                    case NORTH:
                        --row;
                        break;
                    case SOUTH:
                        ++row;
                        break;
                    case EAST:
                        ++col;
                        break;
                    case WEST:
                        --col;
                        break;
                }
            }
        }

        return notVisited;
    }
}