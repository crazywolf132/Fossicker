package fdoom.level.tile;

import fdoom.entity.Entity;
import fdoom.entity.mob.Mob;
import fdoom.gfx.Color;
import fdoom.gfx.ConnectorSprite;
import fdoom.gfx.Sprite;
import fdoom.level.Level;

/// This class is for tiles WHILE THEY ARE EXPLODING
public class ExplodedTile extends Tile {
	private static ConnectorSprite sprite = new ConnectorSprite(ExplodedTile.class, new Sprite(14, 0, 3, 3, 0, 3), Sprite.dots(Color.get(555, 555, 555, 550)))
	{
		public boolean connectsTo(Tile tile, boolean isSide) {
			return !isSide || tile.connectsToLiquid();
		}
	};
	
	protected ExplodedTile(String name) {
		super(name, sprite);
		connectsToSand = true;
		connectsToWater = true;
		connectsToLava = true;
	}
	
	public void steppedOn(Level level, int x, int y, Entity entity) {
		if(entity instanceof Mob)
			((Mob)entity).hurt(this, x, y, 50);
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}
}
