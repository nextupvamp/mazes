package ru.nextupvamp.builder;

import ru.nextupvamp.maze.Cell;
import ru.nextupvamp.maze.Direction;
import ru.nextupvamp.maze.Maze;

public abstract class MazeBuilder {
    protected final int height;
    protected final int width;
    protected final Cell[][] grid;

    public MazeBuilder(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
    }

    @SuppressWarnings("checkstyle:MissingSwitchDefault")
    public static void carveWay(Cell[][] grid, int row, int col, Direction direction) {
        switch (direction) {
            case NORTH:
                grid[row][col].walls().remove(Direction.NORTH);
                grid[row - 1][col].walls().remove(Direction.SOUTH);
                break;
            case SOUTH:
                grid[row][col].walls().remove(Direction.SOUTH);
                grid[row + 1][col].walls().remove(Direction.NORTH);
                break;
            case EAST:
                grid[row][col].walls().remove(Direction.EAST);
                grid[row][col + 1].walls().remove(Direction.WEST);
                break;
            case WEST:
                grid[row][col].walls().remove(Direction.WEST);
                grid[row][col - 1].walls().remove(Direction.EAST);
                break;
        }
    }

    protected void initGrid() {
        for (int i = 0; i != height; ++i) {
            for (int j = 0; j != width; ++j) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public abstract Maze build();
}
