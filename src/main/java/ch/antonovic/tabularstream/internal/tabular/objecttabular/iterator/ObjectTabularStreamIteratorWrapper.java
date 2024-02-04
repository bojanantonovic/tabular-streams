package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class ObjectTabularStreamIteratorWrapper<T> implements ObjectTabularStreamIterator<T> {
	protected final ObjectTabularStreamIterator<T> parentIterator;

	public ObjectTabularStreamIteratorWrapper(final ObjectTabularStreamIterator<T> parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public T valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public T cachedValueFromColumn(final int index) {
		return parentIterator.cachedValueFromColumn(index);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public T[] current() {
		return parentIterator.current();
	}

	@Override
	public void moveCursorToNextPosition() {
		parentIterator.moveCursorToNextPosition();
	}

	@Override
	public T[] next() {
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
	public void incrementPositionWithoutReading() {
		parentIterator.incrementPositionWithoutReading();
	}

	@Override
	public T mapUnary(final UnaryOperator<T> operator) {
		return parentIterator.mapUnary(operator);
	}

	@Override
	public T mapBinary(final BinaryOperator<T> operator) {
		return parentIterator.mapBinary(operator);
	}
}
