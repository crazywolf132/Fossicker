package fdoom.entity.particle;

import fdoom.gfx.Color;
import fdoom.gfx.Sprite;

public class FireParticle extends Particle {
	/// This is used for Spawners, when they spawn an entity.
	
	/**
	 * Creates a new particle at the given position. It has a lifetime of 30 ticks
	 * and a fire looking sprite.
	 * 
	 * @param x X map position
	 * @param y Y map position
	 */
	public FireParticle(int x, int y) {
		super(x, y, 30, new Sprite(9, 19, Color.get(-1, 520, 550, 500)));
	}
}
