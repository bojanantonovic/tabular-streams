package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.function.Supplier;

public class InfinityIterator implements FloatTabularStreamIterator {
	private final Supplier<float[]> supplier;

	private int actualPosition = 0;

	private float[] currentValue;
	private float[][] currentChunk;

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
	public boolean hasNext(final long stepWidth) {
		return true;
	}

	@Override
	public float valueFromColumn(final int index) {
		return currentValue[index];
	}

	@Override
	public FloatVector valueFromColumn(final int column, final VectorSpecies<Float> species) {
		throw new UnsupportedOperationException("No efficient native support possible.");
	}

	@Override
	public void moveCursorToNextPosition() {
		currentValue = supplier.get();
		actualPosition++;
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		currentChunk = new float[(int) stepWidth][];
		for (int i = 0; i < stepWidth; i++) {
			moveCursorToNextPosition();
			currentChunk[i] = currentValue;
		}
	}

	@Override
	public int skip(final int amount) {
		moveCursorToNextPosition(amount);

		return amount;
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
