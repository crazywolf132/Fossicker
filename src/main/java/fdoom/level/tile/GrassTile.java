package fdoom.level.tile;

import fdoom.core.io.Sound;
import fdoom.entity.Direction;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.ConnectorSprite;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class GrassTile extends Tile {
	private static ConnectorSprite sprite = new ConnectorSprite(GrassTile.class, new Sprite(11, 0, 3, 3, Color.get(141, 141, 252, 321), 3), Sprite.dots(Color.get(141, 141, 252, 321)))
	{
		public boolean connectsTo(Tile tile, boolean isSide) {
			if(!isSide) return true;
			return tile.connectsToGrass;
		}
	};
	
	protected GrassTile(String name) {
		super(name, sprite);
		csprite.sides = csprite.sparse;
		connectsToGrass = true;
		maySpawn = true;
	}

	public void tick(Level level, int xt, int yt) {
		// TODO revise this method.
		if (random.nextInt(40) != 0) return;
		
		int xn = xt;
		int yn = yt;
		
		if (random.nextBoolean()) xn += random.nextInt(2) * 2 - 1;
		else yn += random.nextInt(2) * 2 - 1;

		if (level.getTile(xn, yn) == Tiles.get("dirt")) {
			level.setTile(xn, yn, this);
		}

		if (random.nextInt((10 - 1) + 1) + 1 == 4) {
			level.setTile(xt, yt, "Small Grass");
		}
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Shovel) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					level.setTile(xt, yt, Tiles.get("dirt"));
					Sound.monsterHurt.play();
					if (random.nextInt(5) == 0) {
						level.dropItem(xt*16+8, yt*16+8, 2, Items.get("seeds"));
						return true;
					}
				}
			}
			if (tool.type == ToolType.Hoe) {
				if (player.payStamina(4 - tool.level) && tool.payDurability()) {
					Sound.monsterHurt.play();
					if (random.nextInt(5) == 0) {
						level.dropItem(xt*16+8, yt*16+8, Items.get("seeds"));
						return true;
					}
					level.setTile(xt, yt, Tiles.get("farmland"));
					return true;
				}
			}
		}
		return false;
	}
}
