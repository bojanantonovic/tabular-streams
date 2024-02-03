package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface FloatUnaryOperator {
	static FloatUnaryOperator identity() {
		return x -> x;
	}
	float applyAsFloat(float value);

	default FloatUnaryOperator andThen(final FloatUnaryOperator other) {
		return x -> other.applyAsFloat(FloatUnaryOperator.this.applyAsFloat(x));
	}
}
