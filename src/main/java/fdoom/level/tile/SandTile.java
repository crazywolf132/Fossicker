package fdoom.level.tile;

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

public class SandTile extends Tile {
	static Sprite steppedOn, normal = Sprite.dots(Color.get(552, 550, 440, 440));
	static {
		Sprite.Px[][] pixels = new Sprite.Px[2][2];
		pixels[0][0] = new Sprite.Px(3, 1, 0);
		pixels[0][1] = new Sprite.Px(1, 0, 0);
		pixels[1][0] = new Sprite.Px(1, 0, 0);
		pixels[1][1] = new Sprite.Px(3, 1, 0);
		steppedOn = new Sprite(pixels, Color.get(552, 550, 440, 440));
	}
	
	private ConnectorSprite sprite = new ConnectorSprite(SandTile.class, new Sprite(11, 0, 3, 3, Color.get(440, 550, 440, 321), 3), normal)
	{
		public boolean connectsTo(Tile tile, boolean isSide) {
			if(!isSide) return true;
			return tile.connectsToSand;
		}
	};
	
	protected SandTile(String name) {
		super(name, (ConnectorSprite)null);
		csprite = sprite;
		connectsToSand = true;
		maySpawn = true;
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		boolean steppedOn = level.getData(x, y) > 0;
		
		if(steppedOn) csprite.full = SandTile.steppedOn;
		else csprite.full = Sprite.dots(Color.get(552, 550, 440, 440));
		
		csprite.render(screen, level, x, y);
	}

	public void tick(Level level, int x, int y) {
		int d = level.getData(x, y);
		if (d > 0) level.setData(x, y, d - 1);
	}

	public void steppedOn(Level level, int x, int y, Entity entity) {
		if (entity instanceof Mob) {
			level.setData(x, y, 10);
		}
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Shovel) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					level.setTile(xt, yt, Tiles.get("dirt"));
					level.dropItem(xt*16+8, yt*16+8, Items.get("sand"));
					return true;
				}
			}
		}
		return false;
	}
}
