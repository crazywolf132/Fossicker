package fdoom.level.tile;

import fdoom.core.Game;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.Player;
import fdoom.entity.particle.SmashParticle;
import fdoom.entity.particle.TextParticle;
import fdoom.gfx.Color;
import fdoom.gfx.ConnectorSprite;
import fdoom.gfx.Screen;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class SnowTreeTile extends Tile {
	//private ConnectorSprite sprite = new ConnectorSprite();

	public static int col;
	public static int col1;
	public static int col2;

	protected SnowTreeTile(String name) {
		super(name, (ConnectorSprite)null);
		connectsToSnow = true;

		col = Color.get(10, 30, 151, -1);
		col1 = Color.get(10, 30, 430, -1);
		col2 = Color.get(10, 30, 320, -1);
	}

	
	public void render(Screen screen, Level level, int x, int y) {

		Tiles.get("snow").render(screen, level, x, y);

		int barkCol1 = col1;
		int barkCol2 = col2;
		
		boolean u = level.getTile(x, y - 1) == this;
		boolean l = level.getTile(x - 1, y) == this;
		boolean r = level.getTile(x + 1, y) == this;
		boolean d = level.getTile(x, y + 1) == this;
		boolean ul = level.getTile(x - 1, y - 1) == this;
		boolean ur = level.getTile(x + 1, y - 1) == this;
		boolean dl = level.getTile(x - 1, y + 1) == this;
		boolean dr = level.getTile(x + 1, y + 1) == this;

		if (u && ul && l) {
			screen.render(x * 16 + 0, y * 16 + 0, 10 + 1 * 32, col, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 0, 9 + 0 * 32, col, 0);
		}
		if (u && ur && r) {
			screen.render(x * 16 + 8, y * 16 + 0, 10 + 2 * 32, barkCol2, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 0, 10 + 0 * 32, col, 0);
		}
		if (d && dl && l) {
			screen.render(x * 16 + 0, y * 16 + 8, 10 + 2 * 32, barkCol2, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 8, 9 + 1 * 32, barkCol1, 0);
		}
		if (d && dr && r) {
			screen.render(x * 16 + 8, y * 16 + 8, 10 + 1 * 32, col, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 8, 10 + 3 * 32, barkCol2, 0);
		}
	}

	public void tick(Level level, int xt, int yt) {
		int damage = level.getData(xt, yt);
		if (damage > 0) level.setData(xt, yt, damage - 1);
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		/// make arrow fly through trees!
		/*if(Game.debug && e instanceof fdoom.entity.Arrow && ((fdoom.entity.Arrow)e).owner instanceof Player) {
			hurt(level, x, y, 25);
			return true;
		}*/
		return false;
	}
	
	@Override
	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		hurt(level, x, y, dmg);
		return true;
	}
	
	@Override
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Axe) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					hurt(level, xt, yt, random.nextInt(10) + (tool.level) * 5 + 10);
					return true;
				}
			}
		}
		return false;
	}

	public void hurt(Level level, int x, int y, int dmg) {
		if(random.nextInt(100) == 0)
			level.dropItem(x*16+8, y*16+8, Items.get("Apple"));
		
		int damage = level.getData(x, y) + dmg;
		int treeHealth = 20;
		if (Game.isMode("creative")) dmg = damage = treeHealth;
		
		level.add(new SmashParticle(x*16, y*16));
		level.add(new TextParticle("" + dmg, x*16+8, y*16+8, Color.RED));
		if (damage >= treeHealth) {
			level.dropItem(x*16+8, y*16+8, 1, 2, Items.get("Wood"));
			level.dropItem(x*16+8, y*16+8, 1, 2, Items.get("Acorn"));
			level.setTile(x, y, Tiles.get("snow"));
		} else {
			level.setData(x, y, damage);
		}
	}
}
