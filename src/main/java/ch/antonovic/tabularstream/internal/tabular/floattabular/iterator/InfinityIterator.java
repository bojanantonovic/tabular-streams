package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.function.Supplier;

public class InfinityIterator implements FloatTabularStreamIterator {
	private final Supplier<float[]> supplier;

	private int actualPosition = 0;

	private float[] currentValue;

	public InfinityIterator(final Supplier<float[]> supplier) {
		this.supplier = supplier;
	}

	@Override
	public void incrementPositionWithoutReading() {
		actualPosition++;
	}

	@Override
	public long numberOfDeliveredElements() {
		return actualPosition;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public float valueFromColumn(final int index) {
		return currentValue[index];
	}

	@Override
	public float[] current() {
		if (actualPosition == 0) {
			throw new IllegalStateException("next() has not been called");
		}
		return currentValue;
	}

	@Override
	public float[] next() {
		incrementPositionWithoutReading();
		currentValue = supplier.get();
		return current();
	}

	@Override
	public float mapUnary(final FloatUnaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public float mapBinary(final FloatBinaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public void reset() {
		actualPosition = 0;
	}
}
