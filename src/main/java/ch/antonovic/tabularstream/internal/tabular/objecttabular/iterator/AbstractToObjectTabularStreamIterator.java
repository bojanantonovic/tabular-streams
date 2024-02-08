package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

public abstract class AbstractToObjectTabularStreamIterator<U/*, I extends TabularStreamIterator<U>*/, T> extends AbstractObjectTabularStreamIterator<T> {
	protected final TabularStreamIterator<?> sourceIterator;

	protected final Class<T> type;

	protected AbstractToObjectTabularStreamIterator(final TabularStreamIterator<?> sourceIterator, final Class<T> type) {
		this.sourceIterator = sourceIterator;
		this.type = type;
	}

	@Override
	public void moveCursorToNextPosition() {
		sourceIterator.moveCursorToNextPosition();
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		sourceIterator.moveCursorToNextPosition(stepWidth);
	}

	@Override
	public long numberOfDeliveredElements() {
		return sourceIterator.numberOfDeliveredElements();
	}

	@Override
	public void reset() {
		sourceIterator.reset();
	}

	@Override
	public boolean hasNext() {
		return sourceIterator.hasNext();
	}

	@Override
	public boolean hasNext(final long stepWidth) {
		return sourceIterator.hasNext(stepWidth);
	}
}
