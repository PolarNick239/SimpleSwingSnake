package com.snake.logic;

public class TilePosition {

    public int x;
    public int y;

    public TilePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TilePosition[" + "x=" + x + ", y=" + y + ']';
    }
}
