package fdoom.level.tile;

import fdoom.core.io.Sound;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.ConnectorSprite;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class SnowTile extends Tile {

    static Sprite steppedOn, normal = Sprite.dots(Color.get(Color.hex("#2c2c2c"), Color.hex("#ffffff"), Color.hex("#d3d3d3"), 440));
    static {
        Sprite.Px[][] pixels = new Sprite.Px[2][2];
        pixels[0][0] = new Sprite.Px(3, 1, 0);
        pixels[0][1] = new Sprite.Px(1, 0, 0);
        pixels[1][0] = new Sprite.Px(1, 0, 0);
        pixels[1][1] = new Sprite.Px(3, 1, 0);
        steppedOn = new Sprite(pixels, Color.get(Color.hex("#2c2c2c"), Color.hex("#ffffff"), Color.hex("#d3d3d3"), 440));
    }

    private static ConnectorSprite sprite = new ConnectorSprite(GrassTile.class, new Sprite(11, 0, 3, 3, Color.get(Color.hex("#ffffff"), Color.hex("#ffffff"), Color.hex("#ffffff"), 321), 3), Sprite.dots(Color.get(Color.hex("#2c2c2c"), Color.hex("#ffffff"), Color.hex("#d3d3d3"), 321)))
    {
        public boolean connectsTo(Tile tile, boolean isSide) {
            if(!isSide) return true;
            return tile.connectsToSnow;
        }
    };


    protected SnowTile(String name) {
        super(name, sprite);
        maySpawn = true;
        connectsToSnow = true;
    }

    public void steppedOn(Level level, int x, int y, Entity entity) {
        if (entity instanceof Mob) {
            level.setData(x, y, 10);
        }
    }

    public void render(Screen screen, Level level, int x, int y) {
        boolean steppedOn = level.getData(x, y) > 0;

        if(steppedOn) sprite.full = SnowTile.steppedOn;
        else sprite.full = Sprite.dots(Color.get(Color.hex("#2c2c2c"), Color.hex("#ffffff"), Color.hex("#d3d3d3"), 321));

        sprite.render(screen, level, x, y);
    }

    public void tick(Level level, int xt, int yt) {
        // TODO revise this method.
        //if (random.nextInt(40) != 0) return;

        //int xn = xt;
        //int yn = yt;

       // if (random.nextBoolean()) xn += random.nextInt(2) * 2 - 1;
        //else yn += random.nextInt(2) * 2 - 1;

        //if (level.getTile(xn, yn) == Tiles.get("grass")) {
        //    level.setTile(xn, yn, this);
        //}
    }

    public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
        if (item instanceof ToolItem) {
            ToolItem tool = (ToolItem) item;
            if (tool.type == ToolType.Shovel) {
                if (player.payStamina(4 - tool.level) && tool.payDurability()) {
                    level.setTile(xt, yt, Tiles.get("grass"));
                    Sound.monsterHurt.play();
                    if (random.nextInt(5) == 0) {
                        level.dropItem(xt*16+8, yt*16+8, 2, Items.get("seeds"));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
