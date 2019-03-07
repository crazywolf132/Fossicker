package fdoom.level.tile;

import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.ConnectorSprite;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class CloudTile extends Tile {
	private static ConnectorSprite sprite = new ConnectorSprite(CloudTile.class, new Sprite(4, 0, 3, 3, Color.get(333, 444, 555, -1), 3), new Sprite(7, 0, 2, 2, Color.get(333, 444, 555, -1), 3), ConnectorSprite.makeSprite(2, 2, Color.get(444, 444, 555, 444), 0, false, 19, 18, 20, 19))
	{
		public boolean connectsTo(Tile tile, boolean isSide) {
			return tile != Tiles.get("Infinite Fall");
		}
	};
	
	protected CloudTile(String name) {
		super(name, sprite);
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Shovel && player.payStamina(5)) {
				level.setTile(xt, yt, Tiles.get("Infinite Fall")); // would allow you to shovel cloud, I think.
				level.dropItem(xt*16+8, yt*16+8, 1, 3, Items.get("cloud"));
				return true;
			}
		}
		return false;
	}
}
