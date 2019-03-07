package fdoom.level.tile;

import fdoom.core.io.Sound;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.level.Level;

public class TallGrassTile extends Tile {

	private static Sprite small = new Sprite(30, 8, 2, 2, Color.get(-1, 30, 40, -1), 0);
	private static Sprite medium = new Sprite(28, 8, 2, 2, Color.get(-1, 30, 40, -1), 0);
	private static Sprite tall = new Sprite(26, 8, 2, 2, Color.get(-1, 30, 40, -1), 0);

	private Tile onType;
	private int lifeStage = 0;

	protected TallGrassTile(String name, Tile onType) {
		super(name, small);
		connectsToGrass = true;
		this.onType = onType;
		connectsToSand = onType.connectsToSand;
		connectsToGrass = onType.connectsToGrass;
		connectsToSnow = onType.connectsToSnow;
		connectsToWater = onType.connectsToWater;
		connectsToLava = onType.connectsToLava;
		maySpawn = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		onType.render(screen, level, x, y);
		switch(this.lifeStage) {
			case 0:
				small.render(screen, x * 16, y * 16);
				break;
			case 1:
				medium.render(screen, x * 16, y * 16);
				break;
			case 2:
				tall.render(screen, x * 16, y * 16, Color.get(-1, 210, 530, 550));
				break;
		}
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		System.err.println(System.currentTimeMillis());
		if (player.payStamina(3)) {
			level.setTile(xt, yt, Tiles.get("grass"));
			Sound.monsterHurt.play();
			if (random.nextInt(5) == 0) {
				level.dropItem(xt* 16 + 8, yt * 16 + 8, 2, Items.get("Grass Fibers"));
				return true;
			}
		}
		return false;
	}

	public void tick(Level level, int xt, int yt) {
		if (this.lifeStage != 2) {
			if (random.nextInt((10 - 1) + 1) + 1 == 4) {
				this.lifeStage++;
			}
		}
	}

	@Override
	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		System.err.println(System.currentTimeMillis());
		level.setTile(x, y, Tiles.get("grass"));
		Sound.monsterHurt.play();
		if (random.nextInt(4) == 0) {
			level.dropItem(x* 16 + 8, y * 16 + 8, 1, Items.get("Stone"));
		}
		level.dropItem(x * 16 + 8, y * 16 + 8, 2, Items.get("grass fibers"));

		return true;
	}


	@Override
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return lifeStage == 2 ? false : true;
	}
}
