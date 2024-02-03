package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface ShortBinaryOperator {
	ShortBinaryOperator SUM = (x, y) -> (short) (x + y);

	short applyAsShort(short a, short b);
}
