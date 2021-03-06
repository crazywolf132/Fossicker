package fdoom.entity.mob;

import fdoom.core.io.Settings;
import fdoom.gfx.Color;
import fdoom.gfx.MobSprite;
import fdoom.item.Items;

public class Cow extends PassiveMob {
	private static MobSprite[][] sprites = MobSprite.compileMobSpriteAnimations(16, 16);
	
	/**
	 * Creates the cow with the right sprites and color.
	 */
	public Cow() {
		super(sprites, Color.get(-1, 000, 333, 322), 5);
		col = Color.get(-1, 000, 333, 322);
	}
	
	public void die() {
		int min = 0, max = 0;
		if (Settings.get("diff").equals("Easy")) {min = 1; max = 3;}
		if (Settings.get("diff").equals("Normal")) {min = 1; max = 2;}
		if (Settings.get("diff").equals("Hard")) {min = 0; max = 1;}
		
		dropItem(min, max, Items.get("leather"), Items.get("raw beef"));
		
		super.die();
	}
}
