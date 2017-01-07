package com.snake.logic;

import java.util.Random;

public class World {

    private final int width;
    private final int height;
    private final int stepTimeMs;
    private long previousUpdate = -1;

    private final Snake snake;
    private TilePosition apple;
    private boolean isSleeping;

    private Direction targetDirection;

    private final Random random;

    public World(int width, int height, int stepTimeMs) {
        this.width = width;
        this.height = height;
        this.stepTimeMs = stepTimeMs;

        this.snake = new Snake(new TilePosition(width / 2, height / 2), Direction.Up, 3);
        this.targetDirection = snake.getDirection();

        this.random = new Random(239);
    }

    public void init() {
        previousUpdate = System.currentTimeMillis();
        isSleeping = false;

        apple = generateApple();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        long passedTime = currentTime - previousUpdate;

        if (passedTime < stepTimeMs || isSleeping) {
            return;
        }

        if (snake.getDirection() != targetDirection) {
            System.out.println("Direction changed from " + snake.getDirection() + " to " + targetDirection);
            snake.setDirection(targetDirection);
        }

        snake.makeStep();
        if (checkApple()) {
            snake.eatApple();
            apple = generateApple();
        }
        if (checkCollisions(snake.getParts().peekFirst())) {
            isSleeping = true;
            System.out.println("Snake is sleeping!");
        }

        previousUpdate = currentTime;
    }

    public boolean checkApple() {
        if (apple == null) {
            return false;
        }

        TilePosition head = snake.getParts().peekFirst();
        if (head.x == apple.x && head.y == apple.y) {
            System.out.println("Snake found apple!");
            return true;
        }
        return false;
    }

    public boolean checkCollisions(TilePosition tile) {
        for (int i = 0; i < snake.getParts().size(); ++i) {
            TilePosition part = snake.getParts().get(i);
            if (tile == part) {
                continue;
            }

            if (tile.x == part.x && tile.y == part.y) {
                return true;
            }
        }

        if (tile.x < 0 || tile.x >= width || tile.y < 0 || tile.y >= height) {
            return true;
        }

        return false;
    }

    public TilePosition generateApple() {
        int freePlaces = 0;

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (!checkCollisions(new TilePosition(x, y))) {
                    freePlaces++;
                }
            }
        }

        if (freePlaces == 0) {
            System.out.println("There are no place for apple!");
            return null;
        }

        int indexForApple = random.nextInt(freePlaces);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (!checkCollisions(new TilePosition(x, y))) {
                    if (indexForApple == 0) {
                        TilePosition newApple = new TilePosition(x, y);
                        System.out.println("Apple generated at " + newApple);
                        return newApple;
                    }
                    indexForApple--;
                }
            }
        }

        throw new IllegalStateException("Place was found, but somehow was lost!");
    }

    public Snake getSnake() {
        return snake;
    }

    public TilePosition getApple() {
        return apple;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void onUp() {
        if (snake.getDirection() == Direction.Down) {
            System.out.println("Can't move backward!");
        } else {
            targetDirection = Direction.Up;
        }
    }

    public void onRight() {
        if (snake.getDirection() == Direction.Left) {
            System.out.println("Can't move backward!");
        } else {
            targetDirection = Direction.Right;
        }
    }

    public void onDown() {
        if (snake.getDirection() == Direction.Up) {
            System.out.println("Can't move backward!");
        } else {
            targetDirection = Direction.Down;
        }
    }

    public void onLeft() {
        if (snake.getDirection() == Direction.Right) {
            System.out.println("Can't move backward!");
        } else {
            targetDirection = Direction.Left;
        }
    }
}
