package fdoom.level.tile;

import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class FarmTile extends Tile {
	private static Sprite sprite = new Sprite(2, 1, 2, 2, Color.get(301, 411, 422, 533), true, new int[][] {{1, 0}, {0, 1}});
	
	protected FarmTile(String name) {
		super(name, sprite);
	}
	
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Shovel) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					level.setTile(xt, yt, Tiles.get("dirt"));
					return true;
				}
			}
		}
		return false;
	}

	public void tick(Level level, int xt, int yt) {
		int age = level.getData(xt, yt);
		if (age < 5) level.setData(xt, yt, age + 1);
	}

	public void steppedOn(Level level, int xt, int yt, Entity entity) {
		if (random.nextInt(60) != 0) return;
		if (level.getData(xt, yt) < 5) return;
		level.setTile(xt, yt, Tiles.get("dirt"));
	}
}
