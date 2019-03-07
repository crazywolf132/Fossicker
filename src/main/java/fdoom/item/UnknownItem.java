package fdoom.item;

import fdoom.gfx.Sprite;

public class UnknownItem extends StackableItem {
	
	protected UnknownItem(String reqName) {
		super(reqName, Sprite.missingTexture(1, 1));
	}
	
	public UnknownItem clone() {
		return new UnknownItem(getName());
	}
}
