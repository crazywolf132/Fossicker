package fdoom.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    public static final int boxWidth = 8;

    public static int tileSize;
    public static int spritePerLine;
    public static int spriteSize = 8;
    public static int spritePerTileLine;

    public static int width;
    public static int height;
    public int[] pixels;

    static {
        spritePerLine = 44;
        spritePerTileLine = 2;
        tileSize = spriteSize * spritePerTileLine;
    }

    public SpriteSheet(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        spriteSize = width / spritePerLine;
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = (pixels[i] & 0xff) / 64;
        }
    }
}
