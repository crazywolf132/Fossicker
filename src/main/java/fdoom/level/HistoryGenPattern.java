package fdoom.level;

import fdoom.level.tile.Tiles;

public class HistoryGenPattern {

	private static final byte O = -1; // zer0 (transparent)
	private static final byte G = Tiles.get("grass").id; // Grass
	private static final byte D = Tiles.get("dirt").id; // Dirt
	private static final byte W = Tiles.get(32).id; // Wooden Wall
	//TODO: Add fence
	private static final byte F = Tiles.get("fence").id; // Fence
	private static final byte A = Tiles.get("farm").id; // Farm land
	private static final byte S = Tiles.get(33).id; // Stone wall
	private static final byte P = Tiles.get(30).id; // Stone floor
	private static final byte GR = Tiles.get("Grave stone").id; // Grave stone
	
	//=========================================================================
	//		BUILDINGS
	//=========================================================================
	
	public static byte[][] hut1 = new byte[][] {
		{ W, W, W },
		{ W, D, W }
	};
	
	public static byte[][] graveyard1 = new byte[][] {
		{ F, F, F, F, F },
		{ F, GR, GR, GR, F },
		{ F, GR, GR, GR, F },
		{ F, GR, GR, GR, F },
		{ F, F, D, F, F },
	};
	
	//=========================================================================
	//		LISTS
	//========================================================================
	
	public static byte[][][] buildings = new byte[][][] {
		graveyard1
	};
	
	public static byte[][][] sceneryForest = new byte[][][] {
		graveyard1
	};
	
	
	/**
	 * Transposes a pattern.
	 * 
	 * @param x
	 * @return
	 */
	
	public static byte[][] transpose(byte[][] x) {
		byte[][] r = new byte[x[0].length][x.length];
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[i].length; j++) {
				r[j][i] = x[i][j];
			}
		}
		return r;
	}
	
}
