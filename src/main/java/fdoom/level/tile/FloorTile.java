package fdoom.level.tile;

import fdoom.core.io.Sound;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class FloorTile extends Tile {
	private Sprite sprite = new Sprite(19, 2, 2, 2, 0, 0, true);
	
	protected Material type;
	
	protected FloorTile(Material type) {
		super((type == Material.Wood ? "Wood Planks" : type == Material.Obsidian ? "Obsidian" : type.name()+" Bricks"), (Sprite)null);
		this.type = type;
		maySpawn = true;
		switch(type) {
			case Wood: sprite.color = Color.get(210, 210, 430, 320);
			break;
			case Stone: sprite.color = Color.get(333, 333, 444, 444);
			break;
			case Obsidian: sprite.color = Color.get(102, 102, 203, 203);
			break;
		}
		super.sprite = sprite;
	}
	
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Pickaxe) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					level.setTile(xt, yt, Tiles.get("hole"));
					Item drop;
					switch(type) {
						case Wood: drop = Items.get("Plank"); break;
						default: drop = Items.get(type.name()+" Brick"); break;
					}
					level.dropItem(xt*16+8, yt*16+8, drop);
					Sound.monsterHurt.play();
					return true;
				}
			}
		}
		return false;
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}
}
