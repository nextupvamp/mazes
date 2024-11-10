package ru.nextupvamp.io;

import ru.nextupvamp.maze.*;
import ru.nextupvamp.pathfinder.PathFinder;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class Printer {
    private static final char HORIZONTAL_LINE = '━';
    private static final char SPACE = ' ';
    private static final char VERTICAL_LINE = '┃';
    private static final char NSEW_CROSS = '╋';
    private static final char NSE_CROSS = '┣';
    private static final char NWS_CROSS = '┫';
    private static final char NWE_CROSS = '┻';
    private static final char SWE_CROSS = '┳';
    private static final char NW_CORNER = '┛';
    private static final char NE_CORNER = '┗';
    private static final char SW_CORNER = '┓';
    private static final char SE_CORNER = '┏';
    private static final char PATH = '*';
    private static final char DIAMOND = '◈';
    private static final char JUNGLE = '░';

    private final PrintStream out;

    public Printer(PrintStream out) {
        this.out = out;
    }

    public void printMaze(Maze maze, PathFinder pathfinder) {
        char[][] depiction = buildMazeDepiction(maze);

        if (pathfinder != null) {
            List<Coordinate> path = pathfinder.findPath(maze, maze.entrance(), maze.exit());
            if (path.isEmpty()) {
                out.println("Путь не найден");
                return;
            } else {
                addPath(depiction, path);

                int pathLength = 0;
                Cell[][] gridCopy = maze.getGridCopy();
                for (Coordinate c : path) {
                    pathLength += switch (gridCopy[c.row()][c.col()].mazeCellModifier()) {
                        case DIAMOND -> MazeCellModifier.DIAMOND_MODIFIER_VALUE;
                        case JUNGLE -> MazeCellModifier.JUNGLE_MODIFIER_VALUE;
                        case DEFAULT -> MazeCellModifier.DEFAULT_MODIFIER_VALUE;
                    };
                }
                out.printf("Количество шагов: %d%nДлина пути: %d%n", path.size(), pathLength);
            }
        }

        for (int i = 0; i != depiction.length; ++i) {
            for (int j = 0; j != depiction[i].length; ++j) {
                out.print(depiction[i][j]);
            }
            out.println();
        }
    }

    public void printMaze(Maze maze) {
        printMaze(maze, null);
    }

    /**
     * Creates the char matrix with maze depiction.
     * Every single cell is surrounded with walls and
     * intersection. So it uses 6 or 9 chars per one cell.
     *
     * @param maze maze to build
     * @return char matrix with maze depiction
     */
    @SuppressWarnings("checkstyle:missingswitchdefault")
    private char[][] buildMazeDepiction(Maze maze) {
        Cell[][] grid = maze.getGridCopy();
        char[][] depiction = new char[grid.length * 2 + 1][grid[0].length * 2 + 1];
        for (int i = 0; i != depiction.length; ++i) {
            Arrays.fill(depiction[i], SPACE);
        }

        for (int i = 0; i != grid.length; ++i) {
            for (int j = 0; j != grid[i].length; ++j) {
                int row = i * 2 + 1;
                int col = j * 2 + 1;

                // walls
                if (grid[i][j].walls().contains(Direction.NORTH)) {
                    depiction[row - 1][col] = HORIZONTAL_LINE;
                } else {
                    depiction[row - 1][col] = SPACE;
                }
                if (grid[i][j].walls().contains(Direction.SOUTH)) {
                    depiction[row + 1][col] = HORIZONTAL_LINE;
                } else {
                    depiction[row + 1][col] = SPACE;
                }
                if (grid[i][j].walls().contains(Direction.EAST)) {
                    depiction[row][col + 1] = VERTICAL_LINE;
                } else {
                    depiction[row][col + 1] = SPACE;
                }
                if (grid[i][j].walls().contains(Direction.WEST)) {
                    depiction[row][col - 1] = VERTICAL_LINE;
                } else {
                    depiction[row][col - 1] = SPACE;
                }

                // modifiers
                switch (grid[i][j].mazeCellModifier()) {
                    case DIAMOND:
                        depiction[row][col] = DIAMOND;
                        break;
                    case JUNGLE:
                        depiction[row][col] = JUNGLE;
                        break;
                    case DEFAULT:
                        depiction[row][col] = SPACE;
                        break;
                }

                // entrance points
                if (grid[i][j].coordinate().equals(maze.entrance())
                        || grid[i][j].coordinate().equals(maze.exit())) {
                    if (grid[i][j].coordinate().col() == 0) {
                        depiction[row][col - 1] = SPACE;
                    } else {
                        depiction[row][col + 1] = SPACE;
                    }
                }
            }
        }

        handleIntersections(depiction);
        return depiction;
    }

    /**
     * The method handles intersections of walls according
     * to its directions.
     *
     * @param depiction char matrix with maze depiction
     */
    private void handleIntersections(char[][] depiction) {
        for (int i = 0; i != depiction.length; ++i) {
            for (int j = 0; j != depiction[i].length; ++j) {
                if (i % 2 == 0 && j % 2 == 0) {
                    boolean north = false;
                    boolean south = false;
                    boolean east = false;
                    boolean west = false;
                    if (i > 0) {
                        north = depiction[i - 1][j] != SPACE;
                    }
                    if (i < depiction.length - 1) {
                        south = depiction[i + 1][j] != SPACE;
                    }
                    if (j > 0) {
                        west = depiction[i][j - 1] != SPACE;
                    }
                    if (j < depiction[0].length - 1) {
                        east = depiction[i][j + 1] != SPACE;
                    }

                    depiction[i][j] = determineIntersectionChar(north, south, east, west);
                }
            }
        }
    }

    @SuppressWarnings("checkstyle:cyclomaticcomplexity")
    private char determineIntersectionChar(
            boolean north,
            boolean south,
            boolean east,
            boolean west
    ) {
        char intersectionChar;
        if (north && south && east && west) {
            intersectionChar = NSEW_CROSS;
        } else if (north && south && east) {
            intersectionChar = NSE_CROSS;
        } else if (north && south && west) {
            intersectionChar = NWS_CROSS;
        } else if (north && east && west) {
            intersectionChar = NWE_CROSS;
        } else if (south && east && west) {
            intersectionChar = SWE_CROSS;
        } else if (north && west) {
            intersectionChar = NW_CORNER;
        } else if (north && east) {
            intersectionChar = NE_CORNER;
        } else if (south && west) {
            intersectionChar = SW_CORNER;
        } else if (south && east) {
            intersectionChar = SE_CORNER;
        } else if (north || south) {
            intersectionChar = VERTICAL_LINE;
        } else {
            intersectionChar = HORIZONTAL_LINE;
        }

        return intersectionChar;
    }

    /**
     * Methods puts path on a maze depiction
     *
     * @param depiction maze depiction
     * @param path      list of cells of the path
     */
    public void addPath(char[][] depiction, List<Coordinate> path) {
        for (Coordinate coordinate : path) {
            depiction[coordinate.row() * 2 + 1][coordinate.col() * 2 + 1] = PATH;
        }
    }

    public void printStrings(String... strings) {
        for (String string : strings) {
            out.println(string);
        }
    }

    public void printInputNumberPrompt() {
        out.println("Введите число");
    }

    public void printInputCorrectValuePrompt() {
        out.println("Введите корректное значение");
    }
}
