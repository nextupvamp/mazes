package ru.nextupvamp.factory;

import ru.nextupvamp.maze.Maze;

import java.security.SecureRandom;

public interface Builder {
    SecureRandom RANDOM = new SecureRandom();
    int IMPLEMENTATIONS = 2;

    Maze build(int height, int width);
}
