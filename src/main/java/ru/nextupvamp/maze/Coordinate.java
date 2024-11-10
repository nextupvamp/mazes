package ru.nextupvamp.maze;

public record Coordinate(int row, int col) implements Comparable<Coordinate> {
    /**
     * Compares coordinates primarily by rows. If
     * rows are equal then it compares columns.
     * So the ascending order will be like
     * (0, 0), (0, 2), (1, 1), (1, 2)...
     *
     * @param o the object to be compared.
     * @return result of comparison
     */
    @Override
    public int compareTo(Coordinate o) {
        int rowCompare = this.row - o.row;
        if (rowCompare != 0) {
            return rowCompare;
        }
        return this.col - o.col;
    }
}
