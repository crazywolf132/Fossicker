package fdoom.level.tile;

import fdoom.entity.Entity;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.level.Level;

public class FenceTile extends Tile {

	private static Sprite su = new Sprite(24, 11, Color.get(-1, 30, 40, -1));
	private static Sprite sd = new Sprite(2, 2, 2, 2, Color.get(-1, 30, 40, -1), 0);
	private static Sprite sr = new Sprite(25, 12, Color.get(-1, 30, 40, -1));
	private static Sprite sl = new Sprite(23, 12, Color.get(-1, 30, 40, -1));
	private static Sprite sul = new Sprite(23, 11, Color.get(-1, 30, 40, -1));
	private static Sprite sdl = new Sprite(23, 13, Color.get(-1, 30, 40, -1));
	private static Sprite sur = new Sprite(25, 11, Color.get(-1, 30, 40, -1));
	private static Sprite sdr = new Sprite(25, 13, Color.get(-1, 30, 40, -1));
	
	protected FenceTile(String name) {
		super(name);
	}
	
	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(310, 420, 530, 333);
		int transitionColor = Color.get(310, 420, 530, -1);
		
		/*boolean u = level.getTile(x, y - 1) != this;
		boolean d = level.getTile(x, y + 1) != this;
		boolean l = level.getTile(x - 1, y) != this;
		boolean r = level.getTile(x + 1, y) != this;
		
		boolean ul = level.getTile(x - 1, y - 1) != this;
		boolean dl = level.getTile(x - 1, y + 1) != this;
		boolean ur = level.getTile(x + 1, y - 1) != this;
		boolean dr = level.getTile(x + 1, y + 1) != this;*/
		
		boolean u = level.getTile(x, y - 1) == this;
		boolean d = level.getTile(x, y + 1) == this;
		boolean l = level.getTile(x - 1, y) == this;
		boolean r = level.getTile(x + 1, y) == this;
		
		boolean ul = level.getTile(x - 1, y - 1) == this;
		boolean dl = level.getTile(x - 1, y + 1) == this;
		boolean ur = level.getTile(x + 1, y - 1) == this;
		boolean dr = level.getTile(x + 1, y + 1) == this;
		
		if (ul) {
			new Sprite(6, 4, transitionColor).render(screen, x, y);
		} else {
			if (u) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
			if (l) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
		}
		if (dl) {
			new Sprite(6, 4, transitionColor).render(screen, x, y);
		} else {
			if (d) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
			
			if (l) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
		}
		if (ur) {
			new Sprite(6, 4, transitionColor).render(screen, x, y);
		} else {
			if (u) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
			
			if (r) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
		}
		if (dr) {
			new Sprite(6, 4, transitionColor).render(screen, x, y);
		} else {
			if (d) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
			
			if (r) {
				new Sprite(6, 4, transitionColor).render(screen, x, y);
			}
		}
		
		Tiles.get("dirt").render(screen, level, x, y);
//		if (!u && !l) {
//			if (!ul) {
//				sul.render(screen,  x + 16, y * 16, transitionColor);
//			} else {
//				sul.render(screen, x + 16, y * 16, transitionColor);
//			}
//		}
		
		/*if (!u && !l) {
			if (!ul) {
				screen.render(x * 16 + 0, y * 16 + 0, 24 + 3 * 32, col, 0);
				//sul.render(screen, x * 16, y * 16);
			} else {
				screen.render(x * 16 + 0, y * 16 + 0, 28 + 3 * 32, transitionColor, 3);
				//sul.render(screen, x * 16, y * 16, transitionColor);
			}
		} else {
			screen.render(x * 16 + 0, y * 16 + 0, (l ? 23 : 22) + (u ? 2 + 3 : 1 + 3) * 32, transitionColor, 3);
			//sul.render(screen, x * 16, y * 16, transitionColor);
		}
		
		if (!u && !r) {
			if (!ur) {
				screen.render(x * 16 + 8, y * 16 + 0, 25 + 3*32, col, 0);
			} else {
				screen.render(x * 16 + 8, y * 16 + 0, 29 + 3 * 32, transitionColor, 3);
				//sur.render(screen, x * 16, y * 16, transitionColor);
			}
		} else {
			screen.render(x * 16 + 8, y * 16 + 0, (r ? 21 : 22) + (u ? 2+3 : 1+3) * 32, transitionColor, 3);
		}
		
		if (!d && !l) {
			if (!dl) {
				//screen.render(x * 16 + 0, y * 16 + 8, 26 + 3*32, col, 0);
//				sur.render(screen, x * 16 + 0, y * 16 + 8, col);
			} else {
				//screen.render(x * 16 + 0, y * 16 + 8, 28 + (1+3) * 32, transitionColor, 3);
				
//				sdl.render(screen, x * 16, y * 16 + 8, transitionColor);
			}
		} else {
//			screen.render(x * 16 + 0, y * 16 + 8, (l ? 23 : 22) + (d ? 0+3 : 1+3) * 32, transitionColor, 3);
			if (d) {
//				sd.render(screen, x * 16 + 0, y * 16 + 8, transitionColor);
			}
		}
		
		if (!d && !r) {
			if (!dr) {
//				screen.render(x * 16 + 8, y * 16 + 8, 27 + 3*32, col, 0);
//				sul.render(screen, x * 16 + 8, y * 16 + 8, col);
			} else {
//				screen.render(x * 16 + 8, y * 16 + 8, 29 + (1+3) * 32, transitionColor, 3);
//				sdr.render(screen, x * 16 + 8, y * 16 + 8, transitionColor);
			}
		} else {
//			screen.render(x * 16 + 8, y * 16 + 8, (r ? 21 : 22) + (d ? 0+3 : 1+3) * 32, transitionColor, 3);
//			su.render(screen, x * 16 + 8, y * 16 + 8, transitionColor);
			if (r) {
//				sr.render(screen, x * 16 + 8, y * 16 + 8, transitionColor);
			}
			if (d) {
				sd.render(screen, x * 16, y * 16, transitionColor);
			}
		}*/
		
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}

}
