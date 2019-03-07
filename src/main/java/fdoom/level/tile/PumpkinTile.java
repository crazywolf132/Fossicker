package fdoom.level.tile;

import fdoom.entity.Entity;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.level.Level;

public class PumpkinTile extends Tile {

    private static Sprite sprite = new Sprite(22, 8, 2, 2, Color.get(-1, 210, 530, 550), 0);
    private boolean isJacko = false;

    protected PumpkinTile(String name, boolean isJacko) {
        super(name, sprite);
        this.isJacko = isJacko;
        connectsToGrass = true;
    }

    public void render(Screen screen, Level level, int x, int y) {
        Tiles.get("grass").render(screen, level, x, y);
        sprite.render(screen, x*16, y*16, Color.get(-1, 210, 530, 550));
    }

    @Override
    public boolean mayPass(Level level, int x, int y, Entity e) {
        return false;
    }

    @Override
    public int getLightRadius(Level level, int x, int y) {
        return 3;
    }
}
