package ch.antonovic.tabularstream.function;

public interface TernaryOperator<T> {
	T apply(T a, T b, T c);
}
