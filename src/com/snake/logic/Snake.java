package com.snake.logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake {

    private final LinkedList<TilePosition> parts;

    private Direction direction;
    private int length;

    public Snake(TilePosition head, Direction direction, int length) {
        this.parts = new LinkedList<>();
        for (int i = 0; i < length; ++i) {
            this.parts.add(head);
        }

        this.direction = direction;
        this.length = length;
    }

    public void eatApple() {
        TilePosition last = parts.peekLast();
        this.parts.addLast(new TilePosition(last.x, last.y));
        System.out.println("Snake is now " + parts.size() + " tiles long!");
    }

    public void makeStep() {
        TilePosition oldHead = parts.peekFirst();
        TilePosition newHead = new TilePosition(oldHead.x + direction.dx, oldHead.y + direction.dy);

        parts.pollLast();
        parts.addFirst(newHead);
    }

    public LinkedList<TilePosition> getParts() {
        return parts;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
