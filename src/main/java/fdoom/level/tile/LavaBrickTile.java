package fdoom.level.tile;

import fdoom.core.io.Sound;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class LavaBrickTile extends Tile {
	private static Sprite sprite = new Sprite(19, 2, 2, 2, Color.get(300, 300, 400, 400));
	
	protected LavaBrickTile(String name) {
		super(name, sprite);
	}
	
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Pickaxe) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					level.setTile(xt, yt, Tiles.get("lava"));
					Sound.monsterHurt.play();
					return true;
				}
			}
		}
		return false;
	}

	public void bumpedInto(Level level, int x, int y, Entity entity) {
		if(entity instanceof Mob)
			((Mob)entity).hurt(this, x, y, 3);
	}

	public boolean mayPass(Level level, int x, int y, Entity e) { return e.canWool(); }
}
