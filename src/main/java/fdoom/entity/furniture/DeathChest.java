package fdoom.entity.furniture;

import fdoom.core.Game;
import fdoom.core.Updater;
import fdoom.core.io.Settings;
import fdoom.entity.Entity;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Font;
import fdoom.gfx.Screen;

public class DeathChest extends Chest {
	
	public int time; // time passed (used for death chest despawn)
	private int redtick = 0; // this is used to determine the shade of red when the chest is about to expire.
	private boolean reverse; // what direction the red shade (redtick) is changing.
	
	/**
	 * Creates a custom chest with the name Death Chest
	 */
	public DeathChest() {
		super("Death Chest", Color.get(-1, 220, 331, 552));
		
		/// set the expiration time based on the world difficulty.
		if (Settings.get("diff").equals("Easy")) {
			time = 300*Updater.normSpeed;
		} else if (Settings.get("diff").equals("Normal")) {
			time = 120*Updater.normSpeed;
		} else if (Settings.get("diff").equals("Hard")) {
			time = 30*Updater.normSpeed;
		}
	}
	
	public DeathChest(Player player) {
		this();
		this.x = player.x;
		this.y = player.y;
		getInventory().addAll(player.getInventory());
	}
	
	// for death chest time count, I imagine.
	@Override
	public void tick() {
		super.tick();
		//name = "Death Chest:"; // add the current
		
		if (getInventory().invSize() == 0) {
			remove();
		}
		
		if (time < 30*Updater.normSpeed) { // if there is less than 30 seconds left...
			redtick += reverse ? -1 : 1; // inc/dec-rement redtick, changing the red shading.
			
			// set the chest color based on redtick's value
			int expcol = 100 * (redtick / 5 + 1);
			sprite.color = Color.get(-1, expcol, expcol+100, expcol+200);
			
			/// these two statements keep the red color oscillating.
			if (redtick > 13) {
				reverse = true;
			}
			if (redtick < 0) {
				reverse = false;
			}
		}
		
		if (time > 0) {
			time--; // decrement the time if it is not already zero.
		}
		
		if (time == 0) {
			die(); // remove the death chest when the time expires, spilling all the contents.
		}
	}
	
	public void render(Screen screen) {
		super.render(screen);
		String timeString = (time / Updater.normSpeed) + "S";
		Font.draw(timeString, screen, x - Font.textWidth(timeString)/2, y - Font.textHeight() - getBounds().getHeight()/2, Color.WHITE);
	}
	
	public boolean use(Player player) { return false; } // can't open it, just walk into it.
	
	public void take(Player player) {} // can't grab a death chest.
	
	@Override
	public void touchedBy(Entity other) {
		if(other instanceof Player) {
			if(!Game.ISONLINE) {
				((Player)other).getInventory().addAll(getInventory());
				remove();
				Game.notifications.add("Death chest retrieved!");
			}
			else if(Game.isValidClient()) {
				Game.client.touchDeathChest((Player)other, this);
				remove();
			}
		}
	}
	
	protected String getUpdateString() {
		String updates = super.getUpdateString() + ";";
		updates += "time,"+time;
		
		return updates;
	}
	
	@Override
	protected boolean updateField(String field, String val) {
		if(super.updateField(field, val)) return true;
		switch(field) {
			case "time":
				time = Integer.parseInt(val);
				return true;
		}
		
		return false;
	}
}
