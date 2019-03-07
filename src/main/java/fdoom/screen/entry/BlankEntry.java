package fdoom.screen.entry;

import fdoom.core.io.InputHandler;
import fdoom.gfx.Screen;
import fdoom.gfx.SpriteSheet;

public class BlankEntry extends ListEntry {
	
	public BlankEntry() {
		setSelectable(false);
	}
	
	@Override
	public void tick(InputHandler input) {}
	
	@Override
	public void render(Screen screen, int x, int y, boolean isSelected) {}
	
	@Override
	public int getWidth() {
		return SpriteSheet.boxWidth;
	}
	
	@Override
	public String toString() { return " "; }
}
