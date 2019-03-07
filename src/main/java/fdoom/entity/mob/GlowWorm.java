package fdoom.entity.mob;

import fdoom.core.Updater;
import fdoom.core.io.Settings;
import fdoom.gfx.Color;
import fdoom.gfx.MobSprite;
import fdoom.gfx.Screen;
import fdoom.item.Items;
import fdoom.level.Level;

public class GlowWorm extends PassiveMob {

    private static final MobSprite[][] sprites;
    private static final MobSprite[] standing;

    public static int glowwormCol = Color.get(-1, -1, 222, 550);

    static {
        MobSprite[] list = MobSprite.compileSpriteList(26, 19, 1, 1, 0, 1);
        standing = new MobSprite[] {list[0], list[0]};
        sprites = new MobSprite[1][1];
        sprites[0] = standing;
    }

    /**
     * Creates a glowWorm
     */
    public GlowWorm() {
        super(sprites, glowwormCol);
        col = Color.get(-1, -1, 222, 550);
    }

    public int getLightRadius() {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();

        if (!(Updater.getTime() == Updater.Time.Night || Updater.getTime() == Updater.Time.Evening)) {
            this.remove();
        }
    }

    @Override
    public void render(Screen screen) {
        int xo = x - 8;
        int yo = y - 11;

        int color = col;
        if (hurtTime > 0) {
            color = Color.WHITE;
        }

        MobSprite curSprite = sprites[0][0];
        curSprite.render(screen, xo, yo, color);
    }
}
