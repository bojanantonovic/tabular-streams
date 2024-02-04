package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamIteratorWrapper implements FloatTabularStreamIterator {
	protected final FloatTabularStreamIterator parentIterator;

	public FloatTabularStreamIteratorWrapper(final FloatTabularStreamIterator parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public float cachedValueFromColumn(final int index) {
		return parentIterator.cachedValueFromColumn(index);
	}

	@Override
	public float valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public float[] current() {
		return parentIterator.current();
	}

	@Override
	public float[] next() {
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
	public void incrementPositionWithoutReading() {
		parentIterator.incrementPositionWithoutReading();
	}

	@Override
	public float mapUnary(final FloatUnaryOperator operator) {
		return parentIterator.mapUnary(operator);
	}

	@Override
	public float mapBinary(final FloatBinaryOperator operator) {
		return parentIterator.mapBinary(operator);
	}
}
