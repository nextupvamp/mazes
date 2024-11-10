package ru.nextupvamp;

import lombok.experimental.UtilityClass;
import ru.nextupvamp.builder.MazeBuilder;
import ru.nextupvamp.maze.Coordinate;
import ru.nextupvamp.maze.Direction;
import ru.nextupvamp.maze.Maze;
import ru.nextupvamp.maze.MazeCellModifier;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TestSourceData {
    public static String LINE_SEPARATOR = System.lineSeparator();

    /*
    ━━━━━━┓
          ┃
    ┃ ━━┓ ┃
    ┃   ┃ ┃
    ┃ ┏━┛ ┃
    ┃ ┃
    ┗━┻━━━━
     */
    public static final String UNICURSAL_MAZE_DEPICTION =
        "━━━━━━┓" + LINE_SEPARATOR +
        "      ┃" + LINE_SEPARATOR +
        "┃ ━━┓ ┃" + LINE_SEPARATOR +
        "┃   ┃ ┃" + LINE_SEPARATOR +
        "┃ ┏━┛ ┃" + LINE_SEPARATOR +
        "┃ ┃    " + LINE_SEPARATOR +
        "┗━┻━━━━" + LINE_SEPARATOR;
    public static final String UNICURSAL_MAZE_WITH_PATH_DEPICTION =
        "Количество шагов: 5" + LINE_SEPARATOR +
        "Длина пути: 5" + LINE_SEPARATOR +
        "━━━━━━┓" + LINE_SEPARATOR +
        " * * *┃" + LINE_SEPARATOR +
        "┃ ━━┓ ┃" + LINE_SEPARATOR +
        "┃   ┃*┃" + LINE_SEPARATOR +
        "┃ ┏━┛ ┃" + LINE_SEPARATOR +
        "┃ ┃  * " + LINE_SEPARATOR +
        "┗━┻━━━━" + LINE_SEPARATOR;
    public static final Maze UNICURSAL_MAZE;

    static {
        MazeBuilder mazeBuilder = new MazeBuilder(3, 3) {
            @Override
            public Maze build() {
                initGrid();
                carveWay(grid, 0, 0, Direction.EAST);
                carveWay(grid, 0, 1, Direction.EAST);
                carveWay(grid, 0, 2, Direction.SOUTH);
                carveWay(grid, 1, 2, Direction.SOUTH);
                carveWay(grid, 2, 2, Direction.WEST);
                carveWay(grid, 0, 0, Direction.SOUTH);
                carveWay(grid, 1, 0, Direction.EAST);
                carveWay(grid, 1, 0, Direction.SOUTH);
                return new Maze(height, width, grid);
            }
        };
        UNICURSAL_MAZE = mazeBuilder.build();
        UNICURSAL_MAZE.addEntrance(new Coordinate(0, 0));
        UNICURSAL_MAZE.addExit(new Coordinate(2, 2));
    }

    /*
    ┏━━━━━━
    ┃  ◈
    ┃ ━━━ ┃
    ┃◈   ░┃
    ┃ ━━━ ┃
    ┃
    ┗━━━━━━
     */
    public static final String BRAID_MAZE_WITH_MODIFIERS_DEPICTION =
        "┏━━━━━━" + LINE_SEPARATOR +
        "┃  ◈   " + LINE_SEPARATOR +
        "┃ ━━━ ┃" + LINE_SEPARATOR +
        "┃◈   ░┃" + LINE_SEPARATOR +
        "┃ ━━━ ┃" + LINE_SEPARATOR +
        "┃  ◈   " + LINE_SEPARATOR +
        "┗━━━━━━" + LINE_SEPARATOR;

    public static final Maze BRAID_MAZE_WITH_MODIFIERS;

    static {
        MazeBuilder mazeBuilder = new MazeBuilder(3, 3) {
            @Override
            public Maze build() {
                initGrid();
                carveWay(grid, 0, 0, Direction.EAST);
                carveWay(grid, 0, 1, Direction.EAST);
                carveWay(grid, 0, 0, Direction.SOUTH);
                carveWay(grid, 0, 2, Direction.SOUTH);
                carveWay(grid, 1, 0, Direction.EAST);
                carveWay(grid, 1, 1, Direction.EAST);
                carveWay(grid, 1, 0, Direction.SOUTH);
                carveWay(grid, 1, 2, Direction.SOUTH);
                carveWay(grid, 2, 0, Direction.EAST);
                carveWay(grid, 2, 1, Direction.EAST);
                grid[0][1].mazeCellModifier(MazeCellModifier.DIAMOND);
                grid[1][0].mazeCellModifier(MazeCellModifier.DIAMOND);
                grid[2][1].mazeCellModifier(MazeCellModifier.DIAMOND);
                grid[1][2].mazeCellModifier(MazeCellModifier.JUNGLE);
                return new Maze(height, width, grid);
            }
        };
        BRAID_MAZE_WITH_MODIFIERS = mazeBuilder.build();
        BRAID_MAZE_WITH_MODIFIERS.addEntrance(new Coordinate(0, 2));
        BRAID_MAZE_WITH_MODIFIERS.addExit(new Coordinate(2, 2));
    }

    public static List<Coordinate> UNICURSAL_MAZE_DFS_PATH = new ArrayList<>();

    static {
        UNICURSAL_MAZE_DFS_PATH.add(new Coordinate(0, 0));
        UNICURSAL_MAZE_DFS_PATH.add(new Coordinate(0, 1));
        UNICURSAL_MAZE_DFS_PATH.add(new Coordinate(0, 2));
        UNICURSAL_MAZE_DFS_PATH.add(new Coordinate(1, 2));
        UNICURSAL_MAZE_DFS_PATH.add(new Coordinate(2, 2));
    }

    public static List<Coordinate> BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH = new ArrayList<>();

    static {
        BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH.add(new Coordinate(0, 2));
        BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH.add(new Coordinate(0, 1));
        BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH.add(new Coordinate(0, 0));
        BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH.add(new Coordinate(1, 0));
        BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH.add(new Coordinate(2, 0));
        BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH.add(new Coordinate(2, 1));
        BRAID_WITH_MODIFIERS_MAZE_A_STAR_PATH.add(new Coordinate(2, 2));
    }
}
