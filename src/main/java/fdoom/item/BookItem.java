package fdoom.item;

import java.util.ArrayList;

import fdoom.core.Game;
import fdoom.entity.Direction;
import fdoom.entity.mob.Player;
import fdoom.gfx.Color;
import fdoom.gfx.Sprite;
import fdoom.level.Level;
import fdoom.level.tile.Tile;
import fdoom.screen.BookData;
import fdoom.screen.BookDisplay;

public class BookItem extends Item {
	
	protected static ArrayList<Item> getAllInstances() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new BookItem("Book", Color.get(-1, 200, 531, 430), null));
		items.add(new BookItem("Antidious", Color.get(-1, 100, 300, 500), BookData.antVenomBook, true));
		return items;
	}
	
	protected String book; // TODO this is not saved yet; it could be, for editable books.
	private final boolean hasTitlePage;
	
	private BookItem(String title, int color, String book) { this(title, color, book, false); }
	private BookItem(String title, int color, String book, boolean hasTitlePage) {
		super(title, new Sprite(14, 4, color));
		this.book = book;
		this.hasTitlePage = hasTitlePage;
	}
	
	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, Direction attackDir) {
		Game.setMenu(new BookDisplay(book, hasTitlePage));
		return true;
	}
	
	@Override
	public boolean interactsWithWorld() { return false; }
	
	public BookItem clone() {
		return new BookItem(getName(), sprite.color, book, hasTitlePage);
	}
}
