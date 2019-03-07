package fdoom.gfx;

import fdoom.Renderer;

import java.awt.*;

public class Screen {

    public static final int w = Renderer.WIDTH;
    public static final int h = Renderer.HEIGHT;
    public static final Point center = new Point(w/2, h/2);

    private int xOffset;
    private int yOffset;

    public int[] pixels;

    private SpriteSheet sheet;

    public Screen(SpriteSheet sheet) {
        this.sheet = sheet;

        // Screen will only ever be as big as the window.
        pixels = new int[Screen.w * Screen.h];
    }

    public Screen(Screen model) { this(model.sheet); }

    /**
     * Clears the screen by changing every pixel to the same color.
     * @param color
     */
    public void clear(int color) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = color; // turns each pixel into a single color (clearing the screen).
        }
    }

    public void render(int[] pixelColors) {
        System.arraycopy(pixelColors, 0, pixels, 0, Math.min(pixelColors.length, pixels.length));
    }

}
