package fdoom.entity.mob;

import fdoom.core.io.Settings;
import fdoom.gfx.Color;
import fdoom.gfx.MobSprite;
import fdoom.item.Items;

public class Sheep extends PassiveMob {
	private static MobSprite[][] sprites = MobSprite.compileMobSpriteAnimations(10, 18);
	
	/**
	 * Creates a sheep entity.
	 */
	public Sheep() {
		super(sprites, Color.get(-1, 000, 555, 432));
	}
	
	public void die() {
		int min = 0, max = 0;
		if (Settings.get("diff").equals("Easy")) {min = 1; max = 3;}
		if (Settings.get("diff").equals("Normal")) {min = 1; max = 2;}
		if (Settings.get("diff").equals("Hard")) {min = 0; max = 2;}
		
		dropItem(min, max, Items.get("wool"));
		
		super.die();
	}
}