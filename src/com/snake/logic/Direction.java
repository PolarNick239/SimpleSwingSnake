package com.snake.logic;

public enum Direction {

    Up(0, -1),
    Right(1, 0),
    Down(0, 1),
    Left(-1, 0);

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public final int dx;
    public final int dy;

}
