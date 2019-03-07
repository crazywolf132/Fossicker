package fdoom.level.tile;

import fdoom.core.Game;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.AirWizard;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.Player;
import fdoom.entity.particle.SmashParticle;
import fdoom.entity.particle.TextParticle;
import fdoom.gfx.Color;
import fdoom.gfx.ConnectorSprite;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

public class WallTile extends Tile {
	
	private static final String obrickMsg = "The airwizard must be defeated first.";
	
	private ConnectorSprite sprite;
	
	protected Material type;
	
	protected WallTile(Material type) {
		super(type.name()+" Wall", (ConnectorSprite)null);
		this.type = type;
		switch(type) {
			case Wood: sprite = new ConnectorSprite(WallTile.class, new Sprite(4, 22, 3, 3, Color.get(100, 430, 320, 540), 3), new Sprite(7, 22, 2, 2, Color.get(100, 430, 320, 540), 3), new Sprite(5, 23, 2, 2, Color.get(430, 430, 320, 320), 0, true));
			break;
			case Stone: sprite = new ConnectorSprite(WallTile.class, new Sprite(4, 25, 3, 3, Color.get(111, 333, 444, 444), 3), new Sprite(7, 24, 2, 2, Color.get(111, 444), 3), Sprite.blank(2, 2, 444));
			break;
			case Obsidian: sprite = new ConnectorSprite(WallTile.class, new Sprite(4, 25, 3, 3, Color.get(000, 203, 103, 103), 3), new Sprite(7, 24, 2, 2, Color.get(000, 103), 3), Sprite.blank(2, 2, 103));
			break;
		}
		csprite = sprite;
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}
	
	@Override
	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		if(level.depth != -3 || type != Material.Obsidian || AirWizard.beaten) {
			hurt(level, x, y, random.nextInt(6) / 6 * dmg / 2);
			return true;
		} else {
			Game.notifications.add(obrickMsg);
			return false;
		}
	}
	
	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Pickaxe) {
				if(level.depth != -3 || type != Material.Obsidian || AirWizard.beaten) {
					if (player.payStamina(4 - tool.level) && tool.payDurability()) {
						hurt(level, xt, yt, random.nextInt(10) + (tool.level) * 5 + 10);
						return true;
					}
				} else
					Game.notifications.add(obrickMsg);
			}
		}
		return false;
	}
	
	public void hurt(Level level, int x, int y, int dmg) {
		int damage = level.getData(x, y) + dmg;
		int sbwHealth = 100;
		if (Game.isMode("creative")) dmg = damage = sbwHealth;
		
		level.add(new SmashParticle(x * 16, y * 16));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.RED));
		if (damage >= sbwHealth) {
			String itemName = "", tilename = "";
			switch(type) {
				case Wood: itemName = "Plank"; tilename = "Wood Planks"; break;
				case Stone: itemName = "Stone Brick"; tilename = "Stone Bricks"; break;
				case Obsidian: itemName = "Obsidian Brick"; tilename = "Obsidian"; break;
			}
			
			level.dropItem(x*16+8, y*16+8, 1, 3-type.ordinal(), Items.get(itemName));
			level.setTile(x, y, Tiles.get(tilename));
		}
		else {
			level.setData(x, y, damage);
		}
	}
	
	public void tick(Level level, int xt, int yt) {
		int damage = level.getData(xt, yt);
		if (damage > 0) level.setData(xt, yt, damage - 1);
	}
	
	public String getName(int data) {
		return Material.values[data].name() + " Wall";
	}
}
