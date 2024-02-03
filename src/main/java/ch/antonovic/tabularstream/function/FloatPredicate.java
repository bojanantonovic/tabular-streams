package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface FloatPredicate {

	FloatPredicate ALWAYS_TRUE = x -> true;
	FloatPredicate ALWAYS_FALSE = x -> false;
	boolean test(float value);

	default FloatPredicate not() {
		return value -> !FloatPredicate.this.not().test(value);
	}

	default FloatPredicate and(final FloatPredicate other) {
		return value -> FloatPredicate.this.test(value) && other.test(value);
	}

	default FloatPredicate or(final FloatPredicate other) {
		return value -> FloatPredicate.this.test(value) || other.test(value);
	}
}
