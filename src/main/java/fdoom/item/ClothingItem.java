package fdoom.item;

import java.util.ArrayList;

import fdoom.core.Game;
import fdoom.entity.Direction;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;
import fdoom.level.Level;
import fdoom.level.tile.Tile;

public class ClothingItem extends StackableItem {
	
	protected static ArrayList<Item> getAllInstances() {
		ArrayList<Item> items = new ArrayList<>();
		
		items.add(new ClothingItem("Red Clothes", Color.get(-1, 100, 400, 500), 400));
		items.add(new ClothingItem("Blue Clothes", Color.get(-1, 001, 004, 005), 004));
		items.add(new ClothingItem("Green Clothes", Color.get(-1, 10, 40, 50), 40));
		items.add(new ClothingItem("Yellow Clothes", Color.get(-1, 110, 440, 550), 440));
		items.add(new ClothingItem("Black Clothes", Color.get(-1, 000, 111, 222), 111));
		items.add(new ClothingItem("Orange Clothes", Color.get(-1, 210, 520, 530), 520));
		items.add(new ClothingItem("Purple Clothes", Color.get(-1, 102, 203, 405), 203));
		items.add(new ClothingItem("Cyan Clothes", Color.get(-1, 12, 23, 45), 23));
		items.add(new ClothingItem("Reg Clothes", Color.get(-1, 111, 110, 210), 110));
		
		return items;
	}
	
	private int playerCol;
	
	private ClothingItem(String name, int color, int pcol) { this(name, 1, color, pcol); }
	private ClothingItem(String name, int count, int color, int pcol) {
		super(name, new Sprite(6, 12, color), count);
		playerCol = pcol;
	}
	
	// put on clothes
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, Direction attackDir) {
		if(player.shirtColor == playerCol) {
			return false;
		} else {
			player.shirtColor = playerCol;
			if(Game.isValidClient())
				Game.client.sendShirtColor();
			return super.interactOn(true);
		}
	}
	
	@Override
	public boolean interactsWithWorld() { return false; }
	
	public ClothingItem clone() {
		return new ClothingItem(getName(), count, sprite.color, playerCol);
	}
}
