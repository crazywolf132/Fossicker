package fdoom.level.tile;

import fdoom.core.Game;
import fdoom.entity.Direction;
import fdoom.entity.Entity;
import fdoom.entity.mob.Mob;
import fdoom.entity.mob.Player;
import fdoom.entity.particle.SmashParticle;
import fdoom.entity.particle.TextParticle;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;
import fdoom.gfx.Sprite;
import fdoom.item.Item;
import fdoom.item.Items;
import fdoom.item.ToolItem;
import fdoom.item.ToolType;
import fdoom.level.Level;

/// this is all the spikey stuff (except "cloud cactus")
public class OreTile extends Tile {
	private Sprite sprite;
	private OreType type;
	
	public enum OreType {
        Iron (Items.get("Iron Ore"), Color.get(-1, 100, 322, 544)),
		Lapis (Items.get("Lapis"), Color.get(-1, 005, 115, 115)),
		Gold (Items.get("Gold Ore"), Color.get(-1, 110, 440, 553)),
		Gem (Items.get("Gem"), Color.get(-1, 101, 404, 545));
		
		private Item drop;
		public final int color;
		
		OreType(Item drop, int color) {
			this.drop = drop;
			this.color = color;
		}
		
		protected Item getOre() {
			return drop.clone();
		}
    }
	
	protected OreTile(OreType o) {
		super((o == OreType.Lapis ? "Lapis" : o.name() + " Ore"), new Sprite(17, 1, 2, 2, o.color));
        this.type = o;
		this.sprite = super.sprite;
	}

	public void render(Screen screen, Level level, int x, int y) {
		sprite.color = (type.color & 0x00_ff_ff_ff) | (Color.get(DirtTile.dCol(level.depth)) << 24);
		sprite.render(screen, x*16, y*16);
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}

	public boolean hurt(Level level, int x, int y, Mob source, int dmg, Direction attackDir) {
		int playHurt;
		if (Game.isMode("creative")) playHurt = random.nextInt(4);
		else {
			playHurt = 0;
		}
		hurt(level, x, y, playHurt);
		return true;
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, Direction attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Pickaxe) {
				if (player.payStamina(6 - tool.level) && tool.payDurability()) {
					hurt(level, xt, yt, 1);
					return true;
				}
			}
		}
		return false;
	}
	
    public Item getOre() {
        return type.getOre();
    }
    
	public void hurt(Level level, int x, int y, int dmg) {
		int damage = level.getData(x, y) + 1;
		int oreH = random.nextInt(10) + 3;
		if (Game.isMode("creative")) dmg = damage = oreH;
		
		level.add(new SmashParticle(x * 16, y * 16));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.RED));
		if (dmg > 0) {
			int count = random.nextInt(2) + 0;
			if (damage >= oreH) {
				level.setTile(x, y, Tiles.get("dirt"));
				count += 2;
			} else {
				level.setData(x, y, damage);
			}
			level.dropItem(x*16+8, y*16+8, count, type.getOre());
		}
	}

	public void bumpedInto(Level level, int x, int y, Entity entity) {
		/// this was used at one point to hurt the player if they touched the ore; that's probably why the sprite is so spikey-looking.
	}
}
