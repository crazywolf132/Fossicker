package fdoom.entity.particle;

import fdoom.core.io.Sound;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;

public class SmashParticle extends Particle {
	static int[][] mirrors = {{2, 3}, {0, 1}};
	
	/**
	 * Creates a smash particle at the givin postion. Has a lifetime of 10 ticks.
	 * Will also play a monsterhurt sound when created.
	 * 
	 * @param x X map position
	 * @param y Y map position
	 */
	public SmashParticle(int x, int y) {
		super(x, y, 10, new Sprite(5, 12, 2, 2, Color.WHITE, true, mirrors));
		Sound.monsterHurt.play(); // plays the sound of a monster getting hit.
	}
}
