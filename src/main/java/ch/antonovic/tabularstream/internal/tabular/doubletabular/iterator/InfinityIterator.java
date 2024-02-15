package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.function.Supplier;

public class InfinityIterator implements DoubleTabularStreamIterator {
	private final Supplier<double[]> supplier;

	private int actualPosition = 0;
	private double[] cachedValue;
	private double[][] currentChunk;

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
	public boolean hasNext(final long stepWidth) {
		return true;
	}

	@Override
	public double valueFromColumn(final int index) {
		return cachedValue[index];
	}

	@Override
	public DoubleVector valueFromColumn(final int column, final VectorSpecies<Double> species) {
		throw new UnsupportedOperationException("No efficient native support possible.");
	}

	@Override
	public void moveCursorToNextPosition() {
		cachedValue = supplier.get();
		actualPosition++;
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		currentChunk = new double[(int) stepWidth][];
		for (int i = 0; i < stepWidth; i++) {
			moveCursorToNextPosition();
			currentChunk[i] = cachedValue;
		}
	}

	@Override
	public int skip(final int amount) {
		moveCursorToNextPosition(amount);

		return amount;
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
