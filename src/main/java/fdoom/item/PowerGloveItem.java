package fdoom.item;

import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.furniture.Furniture;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;

public class PowerGloveItem extends Item {
	
	public PowerGloveItem() {
		super("Power Glove", new Sprite(7, 4, Color.get(-1, 100, 320, 430)));
	}
	
	public boolean interact(Player player, Entity entity, Direction attackDir) {
		if (entity instanceof Furniture) { // If the power glove is used on a piece of furniture...
			Furniture f = (Furniture) entity;
			f.take(player); // Takes (picks up) the furniture
			return true;
		}
		return false; // method returns false if we were not given a furniture entity.
	}
	
	public PowerGloveItem clone() {
		return new PowerGloveItem();
	}
}
