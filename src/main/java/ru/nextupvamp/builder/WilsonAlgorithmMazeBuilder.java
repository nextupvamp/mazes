package ru.nextupvamp.builder;

import lombok.experimental.Accessors;
import ru.nextupvamp.maze.Cell;
import ru.nextupvamp.maze.Direction;
import ru.nextupvamp.maze.Maze;

import java.security.SecureRandom;

public class WilsonAlgorithmMazeBuilder extends MazeBuilder {
    private final SecureRandom random = new SecureRandom();

    public WilsonAlgorithmMazeBuilder(int height, int width) {
        super(height, width);
    }

    @Override
    public Maze build() {
        super.initGrid();

        executeWilsonAlgorithmLoop();

        Maze maze = new Maze(height, width, grid);
        maze.built(true);
        return maze;
    }

    /**
     * The main loop of the Wilson's algorithm
     */
    private void executeWilsonAlgorithmLoop() {
        int notVisitedCells = height * width;

        // Visit random cell
        grid[random.nextInt(height)][random.nextInt(width)].visited(true);
        --notVisitedCells;

        while (notVisitedCells > 0) {
            int sourceRow = random.nextInt(height);
            int sourceCol = random.nextInt(width);

            walkToVisitedCell(sourceRow, sourceCol);

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
    @SuppressWarnings("checkstyle:missingswitchdefault")
    private void walkToVisitedCell(int row, int col) {
        @Accessors(chain = true)
        class Walker {
            private final Cell[][] grid;
            private int row;
            private int col;

            Walker(int row, int col) {
                this.grid = WilsonAlgorithmMazeBuilder.this.grid;
                this.row = row;
                this.col = col;
            }

            boolean visited() {
                return grid[row][col].visited();
            }

            void walk(Direction direction) {
                int newRow = switch (direction) {
                    case NORTH -> row - 1;
                    case SOUTH -> row + 1;
                    case EAST, WEST -> row;
                };
                int newCol = switch (direction) {
                    case WEST -> col - 1;
                    case EAST -> col + 1;
                    case NORTH, SOUTH -> col;
                };
                walk(direction, newRow, newCol);
            }

            private void walk(Direction direction, int row, int col) {
                if (row <= 0 || row >= WilsonAlgorithmMazeBuilder.this.height
                        || col <= 0 || col >= WilsonAlgorithmMazeBuilder.this.width) {
                    return;
                }
                this.grid[row][col].direction(direction);
                this.row = row;
                this.col = col;
            }

        }

        var walker = new Walker(row, col);
        while (!walker.visited()) {
            // choose random direction
            Direction newDirection = Direction.values()[random.nextInt(Direction.values().length)];

            // and walk in that direction leaving it on the previous cell
            walker.walk(newDirection);
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
    @SuppressWarnings("checkstyle:missingswitchdefault")
    private int carveWayToTheVisitedCell(int sourceRow, int sourceCol, int notVisitedCells) {
        int row = sourceRow;
        int col = sourceCol;
        int notVisited = notVisitedCells;

        while (!grid[row][col].visited()) {
            grid[row][col].visited(true);
            --notVisited;
            Direction direction = grid[row][col].direction();
            if (direction != null) {
                carveWay(grid, row, col, direction);
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
