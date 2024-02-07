package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public abstract class ObjectTabularStreamIteratorWrapper<T> extends AbstractObjectTabularStreamIterator<T> {
	protected final ObjectTabularStreamIterator<T> parentIterator;

	protected ObjectTabularStreamIteratorWrapper(final ObjectTabularStreamIterator<T> parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public T valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public boolean hasNext(final long stepWidth) {
		return parentIterator.hasNext(stepWidth);
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
}
