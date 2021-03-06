package fdoom.screen;

import java.util.List;

import fdoom.entity.mob.Player;
import fdoom.item.Recipe;
import fdoom.screen.entry.RecipeEntry;

class RecipeMenu extends ItemListMenu {
	
	private static RecipeEntry[] getAndSortRecipes(List<Recipe> recipes, Player player) {
		recipes.sort((r1, r2) -> {
			boolean craft1 = r1.checkCanCraft(player);
			boolean craft2 = r2.checkCanCraft(player);
			if(craft1 == craft2)
				return 0;//r1.getProduct().name.compareToIgnoreCase(r2.getProduct().name);
			if(craft1) return -1;
			if(craft2) return 1;
			
			return 0; // should never actually be reached
		});
		
		return RecipeEntry.useRecipes(recipes);
	}
	
	RecipeMenu(List<Recipe> recipes, String title, Player player) {
		super(getAndSortRecipes(recipes, player), title);
	}
	
	RecipeMenu(List<Recipe> recipes, String title, Player player, int fillCol, int edgeStrokeCol, int edgeFillCol) {
		super(
			ItemListMenu.getBuilder().setFrame(fillCol, edgeStrokeCol, edgeFillCol),
			getAndSortRecipes(recipes, player),
			title
		);
	}
}
