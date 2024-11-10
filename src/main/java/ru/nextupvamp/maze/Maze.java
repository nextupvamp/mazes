package ru.nextupvamp.maze;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.nextupvamp.builder.MazeBuilder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Maze {
    // I use SIZE * 2 + 1 char array cells
    // to store maze depiction. My terminal
    // supports line length of 160 chars.
    // So the size of maze must be 3 times
    // less than terminal size.
    public static final int MAX_SIZE = 160 / 3;
    public static final int MIN_SIZE = 1;

    private static final SecureRandom RANDOM = new SecureRandom();
    public static final int MAX_PROBABILITY = 100;

    private final int height;
    private final int width;
    @Getter(AccessLevel.NONE)
    private final Cell[][] grid;
    @Setter
    private boolean built;
    private Coordinate entrance;
    private Coordinate exit;

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    public Cell[][] getGridCopy() {
        Cell[][] copy = new Cell[height][width];
        for (int i = 0; i != grid.length; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, grid[i].length);
        }

        return copy;
    }

    /**
     * Makes a maze braid. If you use the method on
     * a not built maze it will throw an exception.
     * So you must apply any builder before the
     * method use.
     *
     * @throws IllegalStateException if maze isn't built
     */
    @SuppressWarnings("checkstyle:cyclomaticcomplexity")
    public void makeBraid() {
        if (!built) {
            throw new IllegalStateException("Maze is not built");
        }

        for (int i = 0; i != height; ++i) {
            for (int j = 0; j != width; ++j) {
                // Three walls means that the cell is a dead end
                if (grid[i][j].walls().size() != Direction.values().length - 1) {
                    continue;
                }

                // The list of walls of a cell
                List<Direction> directions = new ArrayList<>(grid[i][j].walls());

                // Remove boundary walls from the list
                if (i == 0) {
                    directions.remove(Direction.NORTH);
                } else if (i == height - 1) {
                    directions.remove(Direction.SOUTH);
                }
                if (j == 0) {
                    directions.remove(Direction.WEST);
                } else if (j == width - 1) {
                    directions.remove(Direction.EAST);
                }

                // Remove walls of cells that won't have walls
                // after the removal
                if (i > 0 && grid[i - 1][j].walls().size() == 1) {
                    directions.remove(Direction.NORTH);
                }
                if (i < height - 1 && grid[i + 1][j].walls().size() == 1) {
                    directions.remove(Direction.SOUTH);
                }
                if (j > 0 && grid[i][j - 1].walls().size() == 1) {
                    directions.remove(Direction.EAST);
                }
                if (j < width - 1 && grid[i][j + 1].walls().size() == 1) {
                    directions.remove(Direction.WEST);
                }

                if (directions.isEmpty()) {
                    continue;
                }

                // Carve the way in a random direction
                Direction direction = directions.get(RANDOM.nextInt(directions.size()));
                MazeBuilder.carveWay(grid, i, j, direction);
            }
        }
    }

    /**
     * The method puts modifiers on the maze grid
     * with some probability. Probability should
     * be better an even number.
     *
     * @param prob probability in percent
     */
    public void addModifiers(int prob) {
        for (int i = 0; i != height; ++i) {
            for (int j = 0; j != width; ++j) {
                int randInt = RANDOM.nextInt(MAX_PROBABILITY);
                if (randInt < prob) {
                    if (randInt % 2 == 0) {
                        grid[i][j].mazeCellModifier(MazeCellModifier.JUNGLE);
                    } else {
                        grid[i][j].mazeCellModifier(MazeCellModifier.DIAMOND);
                    }
                }
            }
        }
    }

    public void addEntrance(Coordinate coordinate) {
        if (isNotBoundaryCell(coordinate)) {
            return;
        }

        entrance = coordinate;
    }

    public void addExit(Coordinate coordinate) {
        if (isNotBoundaryCell(coordinate)) {
            return;
        }

        exit = coordinate;
    }

    private boolean isNotBoundaryCell(Coordinate coordinate) {
        return !(coordinate.row() == 0 || coordinate.row() == height - 1
                || coordinate.col() == 0 || coordinate.col() == width);
    }
}
