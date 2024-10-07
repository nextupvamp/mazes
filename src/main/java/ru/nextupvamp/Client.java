package ru.nextupvamp;

import lombok.experimental.UtilityClass;
import ru.nextupvamp.factory.Builder;
import ru.nextupvamp.factory.MazeFactory;
import ru.nextupvamp.io.InputHandler;
import ru.nextupvamp.io.Printer;
import ru.nextupvamp.maze.Maze;
import ru.nextupvamp.maze.MazeParamsDTO;
import ru.nextupvamp.pathfinders.Pathfinder;

@UtilityClass
public class Client {
    public static void main(String[] args) {
        Printer printer = new Printer(System.out);
        InputHandler inputHandler = new InputHandler(System.in, printer);

        MazeParamsDTO mazeParams = inputHandler.inputMazeParams();
        Builder builder = inputHandler.inputBuilderType();
        Pathfinder pathfinder = inputHandler.inputPathfinderType();

        Maze maze = MazeFactory.getMaze(builder, mazeParams.height(), mazeParams.width());
        maze.addEntrance(mazeParams.entrance());
        maze.addExit(mazeParams.exit());
        if (mazeParams.braid()) {
            maze.makeBraid();
            if (mazeParams.modifiers()) {
                maze.addModifiers(mazeParams.modifiersProbability());
            }
        }

        printer.printStrings("Исходный лабиринт");
        printer.printMaze(maze);
        printer.printStrings("Найденный путь");
        printer.printMaze(maze, pathfinder);
    }
}