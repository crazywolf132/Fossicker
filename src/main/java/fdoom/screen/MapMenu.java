package fdoom.screen;

import fdoom.core.Game;
import fdoom.core.io.InputHandler;
import fdoom.gfx.Color;
import fdoom.gfx.Font;
import fdoom.gfx.Screen;
import fdoom.gfx.SpriteSheet;
import fdoom.level.Level;
import fdoom.level.tile.Tiles;

public class MapMenu extends Display {

    protected int[][] imgPixels = null;
    protected int currentLevel = 0;
    public static final int textColor = Color.get(5, 5, 5, 550);

    @Override
    public void init(Display parent) {
        super.init(null); // Will just go back to the game after...
        this.imgPixels = new int[Game.levels.length][];
        this.currentLevel = Game.currentLevel;
    }

    @Override
    public void tick(InputHandler input) {
        super.tick(input);
        if (input.getKey("pause").clicked)
            Game.exitMenu();
        if (this.imgPixels[this.currentLevel] == null) {
            this.imgPixels[this.currentLevel] = MapMenu.getMapImage(this.currentLevel);
        }
    }

    public static int[] getMapImage(int maplevel) {
        Level level = Game.levels[maplevel];
        int[] pixels = new int[level.w * level.h];
        int y = 0;
        while (y < level.h) {
            int x = 0;
            while(x < level.w) {
                int i = x + y * level.w;
                if (level.visible[i] || Game.isMode("Creative")) {
                    int col;
                    int data;
                    byte checkValue = level.tiles[i];
                    if (checkValue == Tiles.get("water").id) pixels[i] = 0x000080;
                    if (checkValue == Tiles.get("iron Ore").id) pixels[i] = 0x000080;
                    if (checkValue == Tiles.get("gold Ore").id) pixels[i] = 0x000080;
                    if (checkValue == Tiles.get("gem Ore").id) pixels[i] = 0x000080;
                    if (checkValue == Tiles.get("grass").id) pixels[i] = 0x00ff00;
                    if (checkValue == Tiles.get("rock").id) pixels[i] = 0xa0a0a0;
                    if (checkValue == Tiles.get("dirt").id) pixels[i] = 0x604040;
                    if (checkValue == Tiles.get("sand").id) pixels[i] = 0xa0a040;
                    if (checkValue == Tiles.get("Stone Bricks").id) pixels[i] = 0xa0a040;
                    if (checkValue == Tiles.get("tree").id) pixels[i] = 0x003000;
                    if (checkValue == Tiles.get("Obsidian Wall").id) pixels[i] = 0x0aa0a0;
                    if (checkValue == Tiles.get("Obsidian").id) pixels[i] = 0x000000;
                    if (checkValue == Tiles.get("lava").id) pixels[i] = 0xff2020;
                    if (checkValue == Tiles.get("cloud").id) pixels[i] = 0xa0a0a0;
                    if (checkValue == Tiles.get("Stairs Down").id) pixels[i] = 0xffffff;
                    if (checkValue == Tiles.get("Stairs Up").id) pixels[i] = 0xffffff;
                    if (checkValue == Tiles.get("Cloud Cactus").id) pixels[i] = 0xff00ff;

                    if (checkValue == Tiles.get("flower").id) {
                        pixels[i] = 0x208020;
                    }
                    if (checkValue == Tiles.get("cactus").id) {
                        pixels[i] = Color.get(level.sandColor - 110);
                    }
                    if (checkValue == Tiles.get("hole").id) {
                        pixels[i] = 0x604040;
                    }
                    if (checkValue == Tiles.get("treeSapling").id) {
                        pixels[i] = 0x003000;
                    }
                    if (checkValue == Tiles.get("cactusSapling").id) {
                        pixels[i] = Color.get(level.sandColor);
                    }
                    if (checkValue == Tiles.get("farmland").id) {
                        pixels[i] = 0x604040;
                    }
                    if (checkValue == Tiles.get("wheat").id) {
                        pixels[i] = 0x604040;
                    }
                    if (checkValue == Tiles.get("Infinite Fall").id) {
                        pixels[i] = Color.get(334);
                    }
                    if (checkValue == Tiles.get("reed").id) {
                        pixels[i] = Color.get(125);
                    }
                    if (checkValue == Tiles.get("tussock").id) {
                        pixels[i] = Color.get(30);
                    }
                    if (checkValue == Tiles.get("campfire").id) {
                        // TODO need to fix this once i add the campfire.
                        pixels[i] = Color.get(410);
                    }
                    if (checkValue == Tiles.get("snow").id) {
                        pixels[i] = 0xffffff;
                    }
                    if (checkValue == Tiles.get("snow tree").id) {
                        pixels[i] = 0x800080;
                    }
                    if (checkValue == Tiles.get("Tall Grass").id) {
                        pixels[i] = 0xff0000;
                    }
                }
                ++x;
            }
            ++y;
        }
        if (Game.player != null) {
            int px = Game.player.x / SpriteSheet.tileSize;
            int py = Game.player.y / SpriteSheet.tileSize;
            int y2 = py - 1;
            while (y2 <= py + 1) {
                int x = px - 1;
                while (x <= px + 1) {
                    if ((y2 == py || x == px) && x >= 0 && y2 >= 0 && x < level.w && y2 < level.h) {
                        pixels[x + y2 * level.w] = 0xff0000;
                    }
                    ++x;
                }
                ++y2;
            }
        }
        return pixels;
    }

    public void renderMap(Screen screen, int x, int y) {
        if (this.imgPixels != null && this.imgPixels[this.currentLevel] != null) {
            screen.renderPixelArray(x, y, Game.levels[this.currentLevel].w, Game.levels[this.currentLevel].h, this.imgPixels[this.currentLevel]);
        }
    }

    @Override
    public void render(Screen screen) {
        if (this.imgPixels != null && this.imgPixels[this.currentLevel] != null) {
            int x = (screen.w - Game.levels[this.currentLevel].w) / 2;
            int y = (screen.h - Game.levels[this.currentLevel].h) / 2;
            x -= x % SpriteSheet.spriteSize;
            y -= y % SpriteSheet.spriteSize;
            Font.renderFrame(screen, "Map", x / SpriteSheet.spriteSize - 1, y / SpriteSheet.spriteSize - 1, (x + Game.levels[this.currentLevel].w) / SpriteSheet.spriteSize, (y + Game.levels[this.currentLevel].h) / SpriteSheet.spriteSize + 1);
            this.renderMap(screen, x, y);

        }
    }
}
