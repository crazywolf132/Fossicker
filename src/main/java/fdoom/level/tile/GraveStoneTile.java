package fdoom.level.tile;

import fdoom.core.Updater;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.MobAi;
import fdoom.entity.mob.Zombie;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.level.Level;

public class GraveStoneTile extends Tile {

    private static Sprite sprite = new Sprite(11, 11, 2, 2, Color.get(-1, 300, Color.rgb(60, 63, 65), 550), 0);
    private static Sprite broken = new Sprite(13, 11, 2, 2, Color.get(-1, 300, 300, 300), 0);
    private boolean isBroken = false;
    private boolean hasRunTonight = false;
    private boolean hasSpawnedZombie = false;

    protected GraveStoneTile(String name, boolean isBroken) {
        super(name, sprite);
        this.isBroken = isBroken;
        connectsToGrass = true;
    }

    public void render(Screen screen, Level level, int x, int y) {
        Tiles.get("grass").render(screen, level, x, y);

        if (!isBroken) {
            sprite.render(screen, x * 16, y * 16, Color.get(-1, Color.hex("#515151"), Color.hex("#808080"), Color.hex("#515151")));
        } else {
            broken.render(screen, x * 16, y * 16, Color.get(-1, Color.hex("#515151"), Color.hex("#808080"), 321));
        }

    }

    public void tick(Level level, int xt, int yt) {
        if (!hasSpawnedZombie && isBroken && Updater.getTime() == Updater.Time.Night) {
            MobAi newMob = new Zombie(1);
            newMob.x = xt;
            newMob.y = yt;

            level.add(newMob);

            hasSpawnedZombie = true;
        }
        if (isBroken|| (Updater.getTime() == Updater.Time.Night && hasRunTonight)) {
            // As the grave is already broken, no need to run this.
            return;
        }
        if (Updater.getTime() == Updater.Time.Morning) {
            // We are going to assume that because it is morning... it has just been night.
            // So we will reset the (hasRunTonight) variable aslong as the grave is not broken.
            hasRunTonight = false;
        }

        if (!hasRunTonight && Updater.getTime() == Updater.Time.Night) {
            if (random.nextBoolean()) {
                level.setTile(xt, yt, Tiles.get(44));
            }
            hasRunTonight = true;
        }
    }



    @Override
    public boolean mayPass(Level level, int x, int y, Entity e) {
        return false;
    }

    @Override
    public int getLightRadius(Level level, int x, int y) {
        return isBroken ? 2 : 0;
    }

    @Override
    public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
        level.add(new Zombie(1));
        return true;
    }

}
