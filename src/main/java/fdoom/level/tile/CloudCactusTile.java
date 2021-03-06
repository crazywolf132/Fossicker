package fdoom.level.tile;

import fdoom.core.Game;
import fdoom.core.io.Settings;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.AirWizard;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.Player;
import fdoom.entity.particle.SmashParticle;
import fdoom.entity.particle.TextParticle;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class CloudCactusTile extends Tile {
	private static Sprite sprite = new Sprite(17, 1, 2, 2, Color.get(444, 111, 333, 555));
	
	protected CloudCactusTile(String name) {
		super(name, sprite);
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return e instanceof AirWizard;
	}

	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		hurt(level, x, y, 0);
		return true;
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Pickaxe) {
				if (player.payStamina(6 - tool.level) && tool.payDurability()) {
					hurt(level, xt, yt, 1);
					return true;
				}
			}
		}
		return false;
	}
	
	public void hurt(Level level, int x, int y, int dmg) {
		int damage = level.getData(x, y) + dmg;
		int health = 10;
		if(Game.isMode("creative")) dmg = damage = health;
		level.add(new SmashParticle(x * 16, y * 16));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.RED));
		if (damage >= health)
			level.setTile(x, y, Tiles.get("cloud"));
		else
			level.setData(x, y, damage);
	}

	public void bumpedInto(Level level, int x, int y, Entity entity) {
		if (entity instanceof AirWizard) return;
		
		if(entity instanceof Mob)
			((Mob)entity).hurt(this, x, y, 1+Settings.getIdx("diff"));
	}
}
