package fdoom.entity;

public interface ClientTickable extends Tickable {
	
	default void clientTick() {
		tick();
	}
	
}
