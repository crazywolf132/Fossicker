package fdoom.core;

@FunctionalInterface
public interface MonoCondition<T> {
	boolean check(T arg);
}
