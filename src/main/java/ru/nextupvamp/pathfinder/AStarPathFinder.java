package ru.nextupvamp.pathfinder;

import ru.nextupvamp.maze.*;

import java.util.*;

public class AStarPathFinder implements PathFinder {

    @Override
    public List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end) {
        Cell[][] grid = maze.getGridCopy();
        Set<Coordinate> closedSet = new HashSet<>(maze.height() * maze.width());
        PriorityQueue<Node> openSetQueue = new PriorityQueue<>(Comparator.comparingDouble(Node::getFScore));
        // Path tracing map
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        Map<Coordinate, Double> gScore = new HashMap<>();
        Map<Coordinate, Double> fScore = new HashMap<>();

        // Maps initialization
        for (int i = 0; i != grid.length; ++i) {
            for (int j = 0; j != grid[i].length; ++j) {
                gScore.put(new Coordinate(i, j), Double.MAX_VALUE);
                fScore.put(new Coordinate(i, j), Double.MAX_VALUE);
            }
        }

        // First node initialization
        gScore.put(start, 0.0);
        fScore.put(start, heuristic(start, end));
        openSetQueue.add(new Node(start, fScore.get(start)));

        while (!openSetQueue.isEmpty()) {
            Node currentNode = openSetQueue.poll();
            Coordinate current = currentNode.coordinate;

            if (current.equals(end)) {
                return buildPath(cameFrom, current);
            }
            closedSet.add(current);

            for (Coordinate neighbour : getNeighbours(current, grid)) {
                if (closedSet.contains(neighbour)) {
                    continue;
                }

                // Determination of transition cost
                int modifier = switch (grid[current.row()][current.col()].mazeCellModifier()) {
                    case DEFAULT -> MazeCellModifier.DEFAULT_MODIFIER_VALUE;
                    case JUNGLE -> MazeCellModifier.JUNGLE_MODIFIER_VALUE;
                    case DIAMOND -> MazeCellModifier.DIAMOND_MODIFIER_VALUE;
                };
                double tentativeGScore = gScore.get(current) + modifier;

                if (!openSetQueue.contains(new Node(neighbour, 0)) || tentativeGScore < gScore.get(neighbour)) {
                    cameFrom.put(neighbour, current);
                    gScore.put(neighbour, tentativeGScore);
                    fScore.put(neighbour, tentativeGScore + heuristic(neighbour, end));
                    openSetQueue.add(new Node(neighbour, fScore.get(neighbour)));
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Builds path from transitions map
     *
     * @param cameFrom transitions map
     * @param from     initial cell
     * @return list of path cells coordinates
     */
    private List<Coordinate> buildPath(Map<Coordinate, Coordinate> cameFrom, Coordinate from) {
        List<Coordinate> totalPath = new ArrayList<>(cameFrom.size());
        Coordinate current = from;
        do {
            totalPath.add(current);
            current = cameFrom.get(current);
        } while (current != null);

        // unnecessary but reversed list will be
        // proper for directed paths
        Collections.reverse(totalPath);
        return totalPath;
    }

    private List<Coordinate> getNeighbours(Coordinate current, Cell[][] grid) {
        List<Coordinate> neighbours = new ArrayList<>();
        int row = current.row();
        int col = current.col();

        if (row > 0 && !grid[row][col].walls().contains(Direction.NORTH)) {
            neighbours.add(new Coordinate(row - 1, col));
        }
        if (row < grid.length - 1 && !grid[row][col].walls().contains(Direction.SOUTH)) {
            neighbours.add(new Coordinate(row + 1, col));
        }
        if (col > 0 && !grid[row][col].walls().contains(Direction.WEST)) {
            neighbours.add(new Coordinate(row, col - 1));
        }
        if (col < grid[0].length - 1 && !grid[row][col].walls().contains(Direction.EAST)) {
            neighbours.add(new Coordinate(row, col + 1));
        }

        return neighbours;
    }

    /**
     * Computes manhattan distance between coordinates
     *
     * @param a coordinate a
     * @param b coordinate b
     * @return manhattan distance between a and b
     */
    private double heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private static class Node {
        Coordinate coordinate;
        double fScore;

        Node(Coordinate coordinate, double fScore) {
            this.coordinate = coordinate;
            this.fScore = fScore;
        }

        double getFScore() {
            return fScore;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Node node = (Node) obj;
            return coordinate.equals(node.coordinate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coordinate);
        }
    }
}
