package fdoom.level.tile;

import fdoom.entity.Direction;
import fdoom.entity.mob.Mob;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.level.Level;

public class SaplingTile extends Tile {
	private static Sprite sprite = new Sprite(11, 3, Color.get(20, 40, 50, -1));
	
	private Tile onType;
	private Tile growsTo;
	
	protected SaplingTile(String name, Tile onType, Tile growsTo) {
		super(name, sprite);
		this.onType = onType;
		this.growsTo = growsTo;
		connectsToSand = onType.connectsToSand;
		connectsToGrass = onType.connectsToGrass;
		connectsToWater = onType.connectsToWater;
		connectsToLava = onType.connectsToLava;
		maySpawn = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		onType.render(screen, level, x, y);
		
		sprite.render(screen, x*16, y*16);
	}

	public void tick(Level level, int x, int y) {
		int age = level.getData(x, y) + 1;
		if (age > 100) {
			level.setTile(x, y, growsTo);
		} else {
			level.setData(x, y, age);
		}
	}

	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		level.setTile(x, y, onType);
		return true;
	}
}
