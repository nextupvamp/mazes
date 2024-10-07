package ru.nextupvamp;

import lombok.experimental.UtilityClass;
import ru.nextupvamp.maze.Coordinate;
import ru.nextupvamp.maze.Direction;
import ru.nextupvamp.maze.Maze;
import ru.nextupvamp.maze.Modifier;

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
    public static final Maze UNICURSAL_MAZE = new Maze(3, 3);

    static {
        UNICURSAL_MAZE.initGrid();
        UNICURSAL_MAZE.carveWay(0, 0, Direction.EAST);
        UNICURSAL_MAZE.carveWay(0, 1, Direction.EAST);
        UNICURSAL_MAZE.carveWay(0, 2, Direction.SOUTH);
        UNICURSAL_MAZE.carveWay(1, 2, Direction.SOUTH);
        UNICURSAL_MAZE.carveWay(2, 2, Direction.WEST);
        UNICURSAL_MAZE.carveWay(0, 0, Direction.SOUTH);
        UNICURSAL_MAZE.carveWay(1, 0, Direction.EAST);
        UNICURSAL_MAZE.carveWay(1, 0, Direction.SOUTH);
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

    public static final Maze BRAID_MAZE_WITH_MODIFIERS = new Maze(3, 3);

    static {
        BRAID_MAZE_WITH_MODIFIERS.initGrid();
        BRAID_MAZE_WITH_MODIFIERS.carveWay(0, 0, Direction.EAST);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(0, 1, Direction.EAST);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(0, 0, Direction.SOUTH);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(0, 2, Direction.SOUTH);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(1, 0, Direction.EAST);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(1, 1, Direction.EAST);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(1, 0, Direction.SOUTH);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(1, 2, Direction.SOUTH);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(2, 0, Direction.EAST);
        BRAID_MAZE_WITH_MODIFIERS.carveWay(2, 1, Direction.EAST);
        BRAID_MAZE_WITH_MODIFIERS.addEntrance(new Coordinate(0, 2));
        BRAID_MAZE_WITH_MODIFIERS.addExit(new Coordinate(2, 2));
        BRAID_MAZE_WITH_MODIFIERS.grid()[0][1].modifier(Modifier.DIAMOND);
        BRAID_MAZE_WITH_MODIFIERS.grid()[1][0].modifier(Modifier.DIAMOND);
        BRAID_MAZE_WITH_MODIFIERS.grid()[2][1].modifier(Modifier.DIAMOND);
        BRAID_MAZE_WITH_MODIFIERS.grid()[1][2].modifier(Modifier.JUNGLE);
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