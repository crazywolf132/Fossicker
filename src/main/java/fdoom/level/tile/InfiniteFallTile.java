package fdoom.level.tile;

import fdoom.entity.Entity;
import fdoom.entity.mob.AirWizard;
import fdoom.entity.mob.Player;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.level.Level;

public class InfiniteFallTile extends Tile {
	
	protected InfiniteFallTile(String name) {
		super(name, (Sprite)null);
	}

	public void render(Screen screen, Level level, int x, int y) {}

	public void tick(Level level, int xt, int yt) {}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return e instanceof AirWizard || e instanceof Player && ((Player) e).skinon;
	}
}
