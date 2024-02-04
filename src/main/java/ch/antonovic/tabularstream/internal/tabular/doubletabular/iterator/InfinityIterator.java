package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.Supplier;

public class InfinityIterator implements DoubleTabularStreamIterator {
	private final Supplier<double[]> supplier;

	private int actualPosition = 0;
	private double[] cachedValue;

	public InfinityIterator(final Supplier<double[]> supplier) {
		this.supplier = supplier;
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
		return cachedValue[index];
	}

	@Override
	public void moveCursorToNextPosition() {
		cachedValue = supplier.get();
		actualPosition++;
	}

	@Override
	public double[] next() {
		moveCursorToNextPosition();
		return cachedValue;
	}

	@Override
	public void reset() {
		actualPosition = 0;
	}
}
