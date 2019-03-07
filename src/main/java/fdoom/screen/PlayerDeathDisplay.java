package fdoom.screen;

import java.util.ArrayList;
import java.util.Arrays;

import fdoom.core.Game;
import fdoom.core.World;
import fdoom.core.io.Settings;
import fdoom.gfx.Point;
import fdoom.gfx.SpriteSheet;
import fdoom.screen.entry.BlankEntry;
import fdoom.screen.entry.ListEntry;
import fdoom.screen.entry.SelectEntry;
import fdoom.screen.entry.StringEntry;

public class PlayerDeathDisplay extends Display {
	//private int inputDelay = 60;
	// this is an IMPORTANT bool, determines if the user should respawn or not. :)
	public static boolean shouldRespawn = true;
	
	public PlayerDeathDisplay() {
		super(false, false);
		
		ArrayList<ListEntry> entries = new ArrayList<>();
		entries.addAll(Arrays.asList(
			new StringEntry("Time: " + InfoDisplay.getTimeString()),
			new StringEntry("Score: " + Game.player.getScore()),
			new BlankEntry()
		));
		
		if(!Settings.get("mode").equals("hardcore")) {
			entries.add(new SelectEntry("Respawn", () -> {
				World.resetGame();
				if (!Game.isValidClient())
					Game.setMenu(null); //sets the menu to nothing
			}));
		}
		
		if(Settings.get("mode").equals("hardcore") || !Game.isValidClient())
			entries.add(new SelectEntry("Quit", () -> Game.setMenu(new TitleDisplay())));
		
		menus = new Menu[]{
			new Menu.Builder(true, 0, RelPos.LEFT, entries)
				.setPositioning(new Point(SpriteSheet.boxWidth, SpriteSheet.boxWidth * 3), RelPos.BOTTOM_RIGHT)
				.setTitle("You died! Aww!")
				.setTitlePos(RelPos.TOP_LEFT)
				.createMenu()
		};
		
	}
}
