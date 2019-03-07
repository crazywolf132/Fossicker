package fdoom.level.tile;

import fdoom.core.io.Sound;
import fdoom.entity.Direction;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class DirtTile extends Tile {
	private static Sprite sprite = Sprite.dots(getColor(0));
	
	protected DirtTile(String name) {
		super(name, sprite);
		maySpawn = true;
	}

	protected static int dCol(int depth) {
		switch(depth) {
			case 1: return 444; // sky.
			case 0: return 321; // surface.
			case -4: return 203; // dungeons.
			default: return 222; // caves.
		}
	}
	
	private static int getColor(int depth) {
		int dcol = dCol(depth);
		return Color.get(dcol, dcol, dcol-111, dcol-111);
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		sprite.render(screen, x*16, y*16, getColor(level.depth));
	}
	
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Shovel) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					level.setTile(xt, yt, Tiles.get("hole"));
					level.dropItem(xt*16+8, yt*16+8, Items.get("dirt"));
					Sound.monsterHurt.play();
					return true;
				}
			}
			if (tool.type == ToolType.Hoe) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					level.setTile(xt, yt, Tiles.get("farmland"));
					Sound.monsterHurt.play();
					return true;
				}
			}
		}
		return false;
	}
}
