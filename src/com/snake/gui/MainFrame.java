package com.snake.gui;

import com.snake.logic.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame implements KeyListener {

    private static final int WORLD_TILE_SIZE_X = 15;
    private static final int WORLD_TILE_SIZE_Y = 15;
    private static final int TILE_SIZE = 40;
    private static final int STEP_TIME_MS = 400;

    private final World world;

    public MainFrame(World world) throws HeadlessException {
        this.world = world;

        setPreferredSize(new Dimension(
                Math.min(800, WORLD_TILE_SIZE_X * TILE_SIZE + 2 * TILE_SIZE),
                Math.min(800, WORLD_TILE_SIZE_Y * TILE_SIZE + 2 * TILE_SIZE)));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        createBufferStrategy(2);
        setVisible(true);

        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            world.onUp();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            world.onRight();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            world.onDown();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            world.onLeft();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void paint(BufferedImage image) {
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();

        bs.show();
    }

    public static void main(String[] args) {
        World world = new World(WORLD_TILE_SIZE_X, WORLD_TILE_SIZE_Y, STEP_TIME_MS);
        Renderer renderer = new Renderer(world, TILE_SIZE);
        MainFrame frame = new MainFrame(world);

        UpdateLoop loop = new UpdateLoop(frame, world, renderer);
        loop.run();
    }

}
