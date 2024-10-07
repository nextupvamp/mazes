package ru.nextupvamp.maze;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;

@Getter
public class Cell {
    private final Coordinate coordinate;
    private final EnumSet<Direction> walls;
    @Setter
    private boolean visited;
    @Setter private Direction direction;
    @Setter private Modifier modifier;

    public Cell(int row, int col) {
        coordinate = new Coordinate(row, col);
        walls = EnumSet.allOf(Direction.class);
        direction = Direction.NORTH;
        modifier = Modifier.DEFAULT;
    }
}