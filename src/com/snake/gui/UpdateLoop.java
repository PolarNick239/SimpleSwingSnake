package com.snake.gui;

import com.snake.logic.World;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class UpdateLoop implements Runnable {

    private final MainFrame frame;
    private final World world;
    private final Renderer renderer;

    private BufferedImage img = null;

    private final int fpsLimit;

    public UpdateLoop(MainFrame frame, World world, Renderer renderer) {
        this.frame = frame;
        this.world = world;
        this.renderer = renderer;

        this.fpsLimit = 60;
    }

    @Override
    public void run() {
        long previousRenderTime = 0;
        world.init();

        while (true) {
            boolean shouldRender = false;

            // Если холст для рисования еще не готов или его размер отличается от размера окна - создаем новый холст (BufferedImage)
            if (img == null || img.getWidth() != frame.getWidth() || img.getHeight() != frame.getHeight()) {
                img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
                shouldRender = true;
            }

            world.update();

            long currentTime = System.currentTimeMillis();
            long passedTime = System.currentTimeMillis() - previousRenderTime;

            if (passedTime > 1000 / fpsLimit) {
                shouldRender = true;
            }

            if (shouldRender) {
                int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
                renderer.render(pixels, img.getWidth(), img.getHeight());
                frame.paint(img);

                previousRenderTime = currentTime;
            }
        }
    }

}
