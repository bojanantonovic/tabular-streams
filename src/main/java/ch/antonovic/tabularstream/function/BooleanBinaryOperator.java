package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface BooleanBinaryOperator {
	boolean applyAsBoolean(boolean a, boolean b);

	BooleanBinaryOperator AND = (x, y) -> x && y;
	BooleanBinaryOperator OR = (x, y) -> x || y;
}
