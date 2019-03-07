package fdoom.screen;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

import fdoom.core.Game;
import fdoom.core.Network;
import fdoom.core.Renderer;
import fdoom.core.VersionInfo;
import fdoom.core.World;
import fdoom.core.io.InputHandler;
import fdoom.core.io.Localization;
import fdoom.entity.mob.RemotePlayer;
import fdoom.gfx.Color;
import fdoom.gfx.Font;
import fdoom.gfx.Point;
import fdoom.gfx.Screen;
import fdoom.gfx.SpriteSheet;
import fdoom.level.Level;
import fdoom.level.tile.Tile;
import fdoom.screen.entry.BlankEntry;
import fdoom.screen.entry.LinkEntry;
import fdoom.screen.entry.ListEntry;
import fdoom.screen.entry.SelectEntry;
import fdoom.screen.entry.StringEntry;

import org.jetbrains.annotations.NotNull;

public class TestDisplay extends Display {
	private static final Random random = new Random();
	
	private int rand;
	private int count = 0; // this and reverse are for the logo; they produce the fade-in/out effect.
	private boolean reverse = false;
	
	private AtomicBoolean miniLoaded;
	public static final int MINIGAME_WIDTH = Renderer.WIDTH >> 4;
	public static final int MINIGAME_HEIGHT = Renderer.HEIGHT >> 4;
	private Level miniGame;
	private Screen miniScreen;
	private int tickCount;

	public TestDisplay() {
		
	}
	
	@Override
	public void init(Display parent) {
		super.init(null); // The TitleScreen never has a parent.
		miniLoaded = new AtomicBoolean();
		miniLoaded.set(false);
		Thread thread = new Thread() {
			public void run() {
				miniGame = new Level(128, 128, 0, null);
				miniGame.trySpawn();

				for (int i = 0; i < 1000; i++) {
					miniGame.tick(true);
				}
				miniLoaded.set(true);
			};
		};
		thread.start();

		try {
			miniScreen = new Screen(
					new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/resources/icons.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@NotNull
	private static SelectEntry displayFactory(String entryText, ListEntry... entries) {
		return new SelectEntry(entryText, () -> Game.setMenu(new Display(true, new Menu.Builder(false, 2, RelPos.CENTER, entries).createMenu())));
	}
	
	@Override
	public void tick(InputHandler input) {
		if (input.getKey("r").clicked) rand = random.nextInt(splashes.length);
		
		if (!reverse) {
			count++;
			if (count == 25) reverse = true;
		} else {
			count--;
			if (count == 0) reverse = false;
		}
		
		if (miniLoaded.get()) {
			miniGame.tick(true);
			Tile.tickCount++;
		}

		super.tick(input);
	}
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
		
		/*int h = 2; // Height of squares (on the spritesheet)
		int w = 14; // Width of squares (on the spritesheet)
		int titleColor = Color.get(-1, 0, Color.hex("#2c2c2c"), Color.hex("#ff0000"));
		int xo = (Screen.w - w * 8) / 2; // X location of the title
		int yo = 28; // Y location of the title
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				screen.render(xo + x * 8, yo + y * 8, x + (y + 6) * 32, titleColor, 0);
			}
		}*/
		screen.clear(0);

		if (miniLoaded.get()) {
			int xScroll = (int)(Math.cos((tickCount / 10000.0) * 2*Math.PI) * (miniGame.w * 8) / 2) + (miniGame.w * 8) / 2 + MINIGAME_WIDTH * 16 / 2;
			int yScroll = (int)(Math.sin((tickCount / 10000.0) * 2*Math.PI) * (miniGame.h * 8) / 2) + (miniGame.h * 8) / 2 + MINIGAME_HEIGHT * 16 / 2;
			miniGame.renderBackground(miniScreen, xScroll, yScroll);
			miniGame.renderSprites(miniScreen, xScroll, yScroll);
			//miniScreen.copyRect(screen, 5, Game.HEIGHT - MINIGAME_HEIGHT*16 - 5, MINIGAME_WIDTH * 16, MINIGAME_HEIGHT * 16);
		}
		
		//boolean isblue = splashes[rand].contains("blue");
		//boolean isGreen = splashes[rand].contains("Green");
		//boolean isRed = splashes[rand].contains("Red");
		
		/// this isn't as complicated as it looks. It just gets a color based off of count, which oscilates between 0 and 25.
		//int bcol = 5 - count / 5; // this number ends up being between 1 and 5, inclusive.
		//int splashColor = isblue ? Color.get(-1, bcol) : isRed ? Color.get(-1, bcol*100) : isGreen ? Color.get(-1, bcol*10) : Color.get(-1, (bcol-1)*100+5, bcol*100+bcol*10, bcol*100+bcol*10);
		// *100 means red, *10 means green; simple.
		
		//Font.drawCentered(splashes[rand], screen, 52, splashColor);
		
		/*Font.draw("Version " + Game.VERSION, screen, 1, 1, Color.get(-1, 111));
		
		
		String upString = "("+Game.input.getMapping("up")+", "+Game.input.getMapping("down")+Localization.getLocalized(" to select")+")";
		String selectString = "("+Game.input.getMapping("select")+Localization.getLocalized(" to accept")+")";
		String exitString = "("+Game.input.getMapping("exit")+ Localization.getLocalized(" to return")+")";
		
		Font.drawCentered(upString, screen, Screen.h - 32, Color.get(-1, 111));
		Font.drawCentered(selectString, screen, Screen.h - 22, Color.get(-1, 111));
		Font.drawCentered(exitString, screen, Screen.h - 12, Color.get(-1, 111));*/
	}
	
	private static final String[] splashes = {//new ArrayList<String>();
		"Multiplayer Now Included!",
		// "Also play InfinityTale!",
		// "Also play Minicraft Deluxe!",
		// "Also play Alecraft!",
		// "Also play Hackcraft!",
		// "Also play MiniCrate!",
		// "Also play MiniCraft Mob Overload!",
		"Only on PlayMinicraft.com!",
		"Playminicraft.com is the bomb!",
		// "@MinicraftPlus on Twitter",
		"MinicraftPlus on Youtube",
		//"Join the Forums!",
		"The Wiki is weak! Help it!",
		"Notch is Awesome!",
		"Dillyg10 is cool as Ice!",
		"Shylor is the man!",
		"AntVenom loves cows! Honest!",
		"You should read Antidious Venomi!",
		"Oh Hi Mark",
		"Use the force!",
		"Keep calm!",
		"Get him, Steve!",
		"Forty-Two!",
		"Kill Creeper, get Gunpowder!",
		"Kill Cow, get Beef!",
		"Kill Zombie, get Cloth!",
		"Kill Slime, get Slime!",
		"Kill Skeleton, get Bones!",
		"Kill Sheep, get Wool!",
		"Kill Pig, get Porkchop!",
		"Gold > Iron",
		"Gem > Gold",
		"Test == InDev!",
		"Story? Uhh...",
		"Infinite terrain? What's that?",
		"Redstone? What's that?",
		"Minecarts? What are those?",
		"Windows? I prefer Doors!",
		"2.5D FTW!",
		"3rd dimension not included!",
		"Mouse not included!",
		"No spiders included!",
		"No Endermen included!",
		"No chickens included!",
		"Grab your friends!",
		"Creepers included!",
		"Skeletons included!",
		"Knights included!",
		"Snakes included!",
		"Cows included!",
		"Sheep included!",
		"Pigs included!",
		"Bigger Worlds!",
		"World types!",
		"World themes!",
		"Sugarcane is a Idea!",
		"Milk is an idea!",
		"So we back in the mine,",
		"pickaxe swinging from side to side",
		"Life itself suspended by a thread",
		"In search of Gems!",
		"saying ay-oh, that creeper's KO'd!",
		"Gimmie a bucket!",
		"Farming with water!",
		"Press \"R\"!",
		"Get the High-Score!",
		"Potions ftw!",
		"Beds ftw!",
		"Defeat the Air Wizard!",
		"Conquer the Dungeon!",
		"One down, one to go...",
		"Loom + Wool = String!",
		"String + Wood = Rod!",
		"Sand + Gunpowder = TNT!",
		"Sleep at Night!",
		"Farm at Day!",
		"Explanation Mark!",
		"!sdrawkcab si sihT",
		"This is forwards!",
		"Why is this blue?",
		"Green is a nice color!",
		"Red is my favorite color!",
		"Y U NO BOAT!?",
		"Made with 10000% Vitamin Z!",
		"Too much DP!",
		"Punch the Moon!",
		"This is String qq!",
		"Why?",
		"You are null!",
		"hello down there!",
		"That guy is such a sly fox!",
		"Hola senor!",
		"Sonic Boom!",
		"Hakuna Matata!",
		"One truth prevails!",
		"Awesome!",
		"Sweet!",
		"Cool!",
		"Radical!",
		"011011000110111101101100!",
		"001100010011000000110001!",
		"011010000110110101101101?",
		"...zzz..."
	};
}
