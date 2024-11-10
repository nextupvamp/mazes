package ru.nextupvamp.io;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import ru.nextupvamp.builder.MazeBuilderType;
import ru.nextupvamp.maze.Coordinate;
import ru.nextupvamp.maze.Maze;
import ru.nextupvamp.maze.MazeParamsDTO;
import ru.nextupvamp.pathfinder.PathFinderType;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputHandler {
    public static final int QUADRANTS_AMOUNT = 4;
    public static final int FIRST_QUADRANT = 1;
    public static final int SECOND_QUADRANT = 2;
    public static final int THIRD_QUADRANT = 3;
    public static final int FORTH_QUADRANT = 4;
    public static final int INPUT_METHODS = 2;
    public static final int QUADRANTS_METHOD_ORDINAL = 1;
    public static final int COORDS_METHOD_ORDINAL = 2;
    private final Scanner scanner;
    private final Printer printer;

    public InputHandler(InputStream inputStream, Printer printer) {
        scanner = new Scanner(inputStream, Charset.defaultCharset());
        this.printer = printer;
    }

    public PathFinderType inputPathFinderType() {
        printer.printStrings(
                "Выберите алгоритм поиска пути:",
                "1. DFS",
                "2. A-star"
        );

        PathFinderType[] values = PathFinderType.values();

        return values[inputNumber(1, values.length) - 1];
    }

    public MazeBuilderType inputBuilderType() {
        printer.printStrings(
                "Выберите алгоритм создания лабиринта",
                "1. Алгоритм Прима",
                "2. Алгоритм Уилсона"
        );

        MazeBuilderType[] values = MazeBuilderType.values();

        return values[inputNumber(1, values.length) - 1];
    }

    public MazeParamsDTO inputMazeParams() {
        printer.printStrings("Выберите высоту лабиринта");
        int height = inputNumber(Maze.MIN_SIZE, Maze.MAX_SIZE);

        printer.printStrings("Выберите ширину лабиринта");
        int width = inputNumber(Maze.MIN_SIZE, Maze.MAX_SIZE);

        if (height < Maze.MIN_SIZE || height > Maze.MAX_SIZE
                || width < Maze.MIN_SIZE || width > Maze.MAX_SIZE) {
            throw new IllegalStateException("Unexpected maze parameters: " + height + "x" + width);
        }

        Pair<Coordinate, Coordinate> entrancePoints = inputEntrancePoints(height, width);

        printer.printStrings("Сделать лабиринт плетеным? (1 - Да, 2 - Нет)");
        boolean braid = inputNumber(1, 2) == 1; // true if Да

        boolean modifiers = false;
        int prob = 0;
        if (braid) {
            printer.printStrings("Добавить модификаторы? (1 - Да, 2 - Нет)");
            modifiers = inputNumber(1, 2) == 1;

            printer.printStrings("Выберите вероятность появления модификатора в процентах");
            prob = inputNumber(0, Maze.MAX_PROBABILITY);
        }

        return new MazeParamsDTO(height, width, braid, modifiers,
                prob, entrancePoints.getLeft(), entrancePoints.getRight());
    }

    public Pair<Coordinate, Coordinate> inputEntrancePoints(int height, int width) {
        printer.printStrings(
                "Выберите метод ввода точек входа и выхода",
                "1. По квадрантам",
                "2. По координатам"
        );

        int choice = inputNumber(1, INPUT_METHODS);

        return switch (choice) {
            case QUADRANTS_METHOD_ORDINAL -> inputByQuadrants(height, width);
            case COORDS_METHOD_ORDINAL -> inputByCoords(height, width);
            default -> throw new IllegalStateException("Unexpected input method: " + choice);
        };
    }

    private Pair<Coordinate, Coordinate> inputByCoords(int height, int width) {
        printer.printStrings("Вход и выход должны быть на границах лабиринта");
        // 1-indexation is easier for user
        printer.printStrings("Выберите строку клетки входа (начиная с 1)");
        int entranceRow = inputNumber(1, height) - 1;
        printer.printStrings("Выберите колонку клетки входа (начиная с 1)");
        int entranceCol;
        if (entranceRow == 0 || entranceRow == height - 1) {
            entranceCol = inputNumber(1, width) - 1;
        } else {
            entranceCol = inputNumber(n -> n == 1 || n == width) - 1;
        }
        // there are no checks if these cells are different because
        // program won't break if they aren't
        printer.printStrings("Выберите строку клетки выхода (начиная с 1)");
        int exitRow = inputNumber(1, height) - 1;
        printer.printStrings("Выберите колонку клетки выхода (начиная с 1)");
        int exitCol;
        if (exitRow == 0 || exitRow == height - 1) {
            exitCol = inputNumber(1, width) - 1;
        } else {
            exitCol = inputNumber(n -> n == 1 || n == width) - 1;
        }

        if (entranceRow < 0 || entranceCol < 0 || entranceRow > height || entranceCol > width) {
            throw new IllegalStateException("Unexpected entrance point: " + entranceRow + "x" + entranceCol);
        }
        if (exitCol < 0 || exitRow > height || exitCol > width || exitRow < 0) {
            throw new IllegalStateException("Unexpected exit point: " + exitRow + "x" + exitCol);
        }

        return new ImmutablePair<>(new Coordinate(entranceRow, entranceCol),
                new Coordinate(exitRow, exitCol));
    }

    private Pair<Coordinate, Coordinate> inputByQuadrants(int height, int width) {
        printer.printStrings("Выберите номер квадранта, в углу которого будет вход");
        printQuadrants();
        int entranceQuadrant = inputNumber(1, QUADRANTS_AMOUNT);
        // there are no checks if these cells are different because
        // program won't break if they aren't
        printer.printStrings("Выберите номер квадранта, в углу которого будет выход");
        printQuadrants();
        int exitQuadrant = inputNumber(1, QUADRANTS_AMOUNT);

        Coordinate entrance = getQuadrantCornerCoordinates(entranceQuadrant, height, width);
        Coordinate exit = getQuadrantCornerCoordinates(exitQuadrant, height, width);

        return new ImmutablePair<>(entrance, exit);
    }

    private void printQuadrants() {
        printer.printStrings(
                "2 | 1",
                "--|--",
                "3 | 4"
        );
    }

    private Coordinate getQuadrantCornerCoordinates(int quadrant, int height, int width) {
        return switch (quadrant) {
            case FIRST_QUADRANT -> new Coordinate(0, width - 1);
            case SECOND_QUADRANT -> new Coordinate(0, 0);
            case THIRD_QUADRANT -> new Coordinate(height - 1, 0);
            case FORTH_QUADRANT -> new Coordinate(height - 1, width - 1);
            default -> throw new IllegalStateException("Unexpected quadrant: " + quadrant);
        };
    }

    /**
     * Method handles user number input according to a bounds
     *
     * @param from lower bound of number
     * @param to   upper bound of number
     * @return entered number
     */
    public int inputNumber(int from, int to) {
        while (true) {
            printer.printInputNumberPrompt();
            String input = scanner.nextLine();
            Scanner stringScanner = new Scanner(input);
            if (stringScanner.hasNextInt()) {
                int number = stringScanner.nextInt();
                if (number < from || number > to) {
                    printer.printInputCorrectValuePrompt();
                } else {
                    return number;
                }
            }
        }
    }

    /**
     * Method handles user number input according to a predicate
     *
     * @param predicate number conditions
     * @return entered number
     */
    public int inputNumber(Predicate<Integer> predicate) {
        while (true) {
            printer.printInputNumberPrompt();
            String input = scanner.nextLine();
            Scanner stringScanner = new Scanner(input);
            if (stringScanner.hasNextInt()) {
                int number = stringScanner.nextInt();
                if (!predicate.test(number)) {
                    printer.printInputCorrectValuePrompt();
                } else {
                    return number;
                }
            }
        }
    }
}
