package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;

public class DoubleTabularStreamIteratorWrapper implements DoubleTabularStreamIterator {
	protected final DoubleTabularStreamIterator parentIterator;

	public DoubleTabularStreamIteratorWrapper(final DoubleTabularStreamIterator parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public double valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public DoubleVector valueFromColumn(final int column, final VectorSpecies<Double> species) {
		return parentIterator.valueFromColumn(column, species);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public boolean hasNext(final long stepWidth) {
		return parentIterator.hasNext();
	}

	@Override
	public double[] next() {
		return parentIterator.next();
	}

	@Override
	public void reset() {
		parentIterator.reset();
	}

	@Override
	public long numberOfDeliveredElements() {
		return parentIterator.numberOfDeliveredElements();
	}

	@Override
	public void moveCursorToNextPosition() {
		parentIterator.moveCursorToNextPosition();
	}

	@Override
	public void moveCursorToNextPosition(long stepWidth) {
		parentIterator.moveCursorToNextPosition(stepWidth);
	}

	@Override
	public int skip(final int amount) {
		return parentIterator.skip(amount);
	}
}
