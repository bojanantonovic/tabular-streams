package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class FloatTabularStreamIteratorWrapper implements FloatTabularStreamIterator {
	protected final FloatTabularStreamIterator parentIterator;

	public FloatTabularStreamIteratorWrapper(final FloatTabularStreamIterator parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public float valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public FloatVector valueFromColumn(final int column, final VectorSpecies<Float> species) {
		return parentIterator.valueFromColumn(column, species);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public float[] next() {
		return parentIterator.next();
	}

	@Override
	public boolean hasNext(final long stepWidth) {
		return parentIterator.hasNext();
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
	public void moveCursorToNextPosition(final long stepWidth) {
		parentIterator.moveCursorToNextPosition(stepWidth);
	}

	@Override
	public int skip(final int amount) {
		return parentIterator.skip(amount);
	}
}
