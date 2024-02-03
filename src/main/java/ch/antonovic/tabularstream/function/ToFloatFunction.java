package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface ToFloatFunction<T> {

	float applyAsFloat(final T value);
}
