package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface ObjectAndDoubleBiOperator<T> {

	double applyAsDouble(T object, double value);
}
