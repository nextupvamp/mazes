package ru.nextupvamp;

import lombok.experimental.UtilityClass;
import ru.nextupvamp.builder.MazeBuilder;
import ru.nextupvamp.builder.MazeBuilderFactory;
import ru.nextupvamp.builder.MazeBuilderType;
import ru.nextupvamp.io.InputHandler;
import ru.nextupvamp.io.Printer;
import ru.nextupvamp.maze.Maze;
import ru.nextupvamp.maze.MazeParamsDTO;
import ru.nextupvamp.pathfinder.PathFinder;
import ru.nextupvamp.pathfinder.PathFinderFactory;
import ru.nextupvamp.pathfinder.PathFinderType;

@UtilityClass
public class Applicaton {
    @SuppressWarnings("uncommentedmain")
    public static void main(String[] args) {
        Printer printer = new Printer(System.out);
        InputHandler inputHandler = new InputHandler(System.in, printer);


        MazeParamsDTO mazeParams = inputHandler.inputMazeParams();
        MazeBuilderType mazeBuilderType = inputHandler.inputBuilderType();
        PathFinderType pathfinderType = inputHandler.inputPathFinderType();


        MazeBuilder mazeBuilder = MazeBuilderFactory
                .getMazeBuilder(mazeBuilderType, mazeParams.height(), mazeParams.width());
        PathFinder pathFinder = PathFinderFactory.getPathFinder(pathfinderType);


        Maze maze = mazeBuilder.build();
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
        printer.printMaze(maze, pathFinder);
    }
}
