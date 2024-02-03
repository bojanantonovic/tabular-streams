package ch.antonovic.tabularstream.function;

public interface BooleanUnaryOperator {

	boolean applyAsBoolean(boolean a);

	BooleanUnaryOperator NOT = x -> !x;
}
