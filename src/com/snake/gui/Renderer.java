package com.snake.gui;

import com.snake.logic.Snake;
import com.snake.logic.TilePosition;
import com.snake.logic.World;

public class Renderer {

    private final World world;
    private final int tileWidth;
    private final int tileHeight;

    private final int backgroundColor;
    private final int borderColor;
    private final int tileColor;
    private final int snakeHeadColor;
    private final int snakeColor;
    private final int appleColor;

    public Renderer(World world, int tileSize) {
        this.world = world;
        this.tileWidth = tileSize;
        this.tileHeight = tileSize;

        this.backgroundColor = color(0, 0, 50);
        this.borderColor = color(0, 0, 0);
        this.tileColor = color(230, 230, 255);
        this.snakeHeadColor = color(100, 100, 255);
        this.snakeColor = color(255, 0, 0);
        this.appleColor = color(0, 255, 0);
    }

    public void render(int[] pixels, int pixelsWidth, int pixelsHeight) {
        int screenCenterPixelX = world.getSnake().getParts().peekFirst().x * tileWidth + tileWidth / 2;
        int screenCenterPixelY = world.getSnake().getParts().peekFirst().y * tileHeight + tileHeight / 2;

        if (pixelsWidth >= tileWidth * world.getWidth() && pixelsHeight >= tileHeight * world.getHeight()) {
            screenCenterPixelX = tileWidth * world.getWidth() / 2;
            screenCenterPixelY = tileHeight * world.getHeight() / 2;
        }

        int pixelOffsetX = screenCenterPixelX - pixelsWidth / 2;
        int pixelOffsetY = screenCenterPixelY - pixelsHeight / 2;

        renderBackground(world.getWidth(), world.getHeight(),
                pixels, pixelOffsetX, pixelOffsetY, pixelsWidth, pixelsHeight);
        renderSnake(world.getSnake(),
                pixels, pixelOffsetX, pixelOffsetY, pixelsWidth, pixelsHeight);
        renderApple(world.getApple(),
                pixels, pixelOffsetX, pixelOffsetY, pixelsWidth, pixelsHeight);
    }

    public void renderBackground(int tilesX, int tilesY, int[] pixels, int pixelOffsetX, int pixelOffsetY, int pixelsWidth, int pixelsHeight) {
        for (int i = 0; i < pixels.length; ++i) {
            pixels[i] = backgroundColor;
        }

        int fromTileX = Math.max(0, pixelOffsetX / tileWidth);
        int fromTileY = Math.max(0, pixelOffsetY / tileHeight);
        int toTileX = Math.min(tilesX, (pixelOffsetX + pixelsWidth) / tileWidth + 1);
        int toTileY = Math.min(tilesY, (pixelOffsetY + pixelsHeight) / tileHeight + 1);

        for (int tileY = fromTileY; tileY < toTileY; ++tileY) {
            for (int tileX = fromTileX; tileX < toTileX; ++tileX) {
                for (int dy = 0; dy < tileHeight; ++dy) {
                    for (int dx = 0; dx < tileWidth; ++dx) {
                        int x = tileX * tileWidth + dx;
                        int y = tileY * tileHeight + dy;

                        if (y < pixelOffsetY || y >= pixelOffsetY + pixelsHeight || x < pixelOffsetX || x >= pixelOffsetX + pixelsWidth) {
                            continue;
                        }

                        if (dy == 0 || dy == tileHeight - 1 || dx == 0 || dx == tileWidth - 1) {
                            pixels[(y - pixelOffsetY) * pixelsWidth + (x - pixelOffsetX)] = this.borderColor;
                        } else {
                            pixels[(y - pixelOffsetY) * pixelsWidth + (x - pixelOffsetX)] = this.tileColor;
                        }
                    }
                }
            }
        }
    }

    public void renderSnake(Snake snake, int[] pixels, int pixelOffsetX, int pixelOffsetY, int pixelsWidth, int pixelsHeight) {
        for (int i = snake.getParts().size() - 1; i >= 0; --i) {
            TilePosition part = snake.getParts().get(i);
            for (int dy = 1; dy < tileHeight - 1; ++dy) {
                for (int dx = 1; dx < tileWidth - 1; ++dx) {
                    int x = part.x * tileWidth + dx;
                    int y = part.y * tileHeight + dy;

                    if (y < pixelOffsetY || y >= pixelOffsetY + pixelsHeight || x < pixelOffsetX || x >= pixelOffsetX + pixelsWidth) {
                        continue;
                    }

                    pixels[(y - pixelOffsetY) * pixelsWidth + (x - pixelOffsetX)] = (i == 0) ? this.snakeHeadColor : this.snakeColor;
                }
            }
        }
    }

    public void renderApple(TilePosition apple, int[] pixels, int pixelOffsetX, int pixelOffsetY, int pixelsWidth, int pixelsHeight) {
        for (int dy = 1; dy < tileHeight - 1; ++dy) {
            for (int dx = 1; dx < tileWidth - 1; ++dx) {
                int x = apple.x * tileWidth + dx;
                int y = apple.y * tileHeight + dy;

                if (y < pixelOffsetY || y >= pixelOffsetY + pixelsHeight || x < pixelOffsetX || x >= pixelOffsetX + pixelsWidth) {
                    continue;
                }

                pixels[(y - pixelOffsetY) * pixelsWidth + (x - pixelOffsetX)] = this.appleColor;
            }
        }
    }

    private static int color(int r, int g, int b) {
        return color(r, g, b, 255);
    }

    private static int color(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

}
