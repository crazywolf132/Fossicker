package fdoom.level.tile;

import fdoom.entity.Direction;
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

public class FlowerTile extends Tile {
	private static Sprite flowersprite = new Sprite(1, 1, Color.get(10, 141, 555, 440));
	
	protected FlowerTile(String name) {
		super(name, (ConnectorSprite)null);
		connectsToGrass = true;
		maySpawn = true;
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		Tiles.get("grass").render(screen, level, x, y);
		
		int data = level.getData(x, y);
		int shape = (data / 16) % 2;
		
		x = x << 4;
		y = y << 4;
		
		flowersprite.render(screen, x + 8*shape, y);
		flowersprite.render(screen, x + 8*(shape==0?1:0), y + 8);
	}

	public boolean interact(Level level, int x, int y, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Shovel) {
				if (player.payStamina(2 - tool.level) && tool.payDurability()) {
					level.dropItem(x*16+8, y*16+8, Items.get("Flower"));
					level.dropItem(x*16+8, y*16+8, Items.get("Rose"));
					level.setTile(x, y, Tiles.get("grass"));
					return true;
				}
			}
		}
		return false;
	}

	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		level.dropItem(x*16+8, y*16+8, 1, 2, Items.get("Flower"));
		level.dropItem(x*16+8, y*16+8, 0, 1, Items.get("Rose"));
		level.setTile(x, y, Tiles.get("grass"));
		return true;
	}
}
