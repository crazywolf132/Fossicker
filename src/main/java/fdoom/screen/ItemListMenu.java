package fdoom.screen;

import fdoom.core.Renderer;
import fdoom.gfx.Point;
import fdoom.screen.entry.ItemEntry;

class ItemListMenu extends Menu {
	
	static Builder getBuilder() {
		return new Builder(true, 0, RelPos.LEFT)
			.setPositioning(new Point((Renderer.WIDTH - 8) / 2, (Renderer.HEIGHT - 8) / 2), RelPos.CENTER)
			.setDisplayLength(9)
			.setSelectable(true)
			.setScrollPolicies(1, false);
	}
	
	ItemListMenu(Builder b, ItemEntry[] entries, String title) {
		super(b
			.setEntries(entries)
			.setTitle(title)
			.createMenu()
		);
	}
	
	ItemListMenu(ItemEntry[] entries, String title) {
		this(getBuilder(), entries, title);
	}
}
