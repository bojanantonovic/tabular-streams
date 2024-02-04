package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

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
	public long numberOfDeliveredElements() {
		return actualPosition;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public float valueFromColumn(int index) {
		return currentValue[index];
	}

	@Override
	public void moveCursorToNextPosition() {
		currentValue = supplier.get();
		actualPosition++;
	}

	@Override
	public float[] next() {
		moveCursorToNextPosition();
		return currentValue;
	}

	@Override
	public void reset() {
		actualPosition = 0;
	}
}
