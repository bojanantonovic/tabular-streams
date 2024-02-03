package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;

public class InfinityIterator implements DoubleTabularStreamIterator {
	private final Supplier<double[]> supplier;

	private int actualPosition = 0;
	private double[] currentValue;

	public InfinityIterator(final Supplier<double[]> supplier) {
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
	public double valueFromColumn(final int index) {
		return currentValue[index];
	}

	@Override
	public double[] current() {
		if (actualPosition == 0) {
			throw new IllegalStateException("next() has not been called");
		}
		return currentValue;
	}

	@Override
	public double[] next() {
		incrementPositionWithoutReading();
		currentValue = supplier.get();
		return current();
	}

	@Override
	public double mapUnary(final DoubleUnaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public double mapBinary(final DoubleBinaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public void reset() {
		actualPosition = 0;
	}
}
