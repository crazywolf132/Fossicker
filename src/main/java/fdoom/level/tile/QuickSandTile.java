package fdoom.level.tile;

import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.level.Level;

public class QuickSandTile extends Tile {

    private static Sprite qstile = new Sprite(22, 1, 2, 2, Color.get(222, 000, 333, 444), 0);

    protected QuickSandTile(String name) {
        super(name, qstile);
        connectsToSand = true;
    }

    private int getDirtColor(int depth) {
        switch(depth) {
            case 0: return 550;
            case 1: return 444;
            case -4: return 59;
            default: return 222;
        }
    }

    public void render(Screen screen, Level level, int x, int y) {
        int col;
        if (level.depth < 0)
            col = Color.get(getDirtColor(level.depth), 000, 333, Color.hex("#f4a460"));
        else
            col = Color.get(getDirtColor(level.depth), Color.rgb(217, 218, 104), Color.rgb(188, 190, 78), Color.rgb(	159, 163, 51));

        sprite.render(screen, x*16, y*16, col);
    }
}
