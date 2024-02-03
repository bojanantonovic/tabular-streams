package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface ToIntTriFunction<A, B, C> {
	int applyAsInt(A first, B second, C third);
}
