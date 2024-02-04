package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public abstract class AbstractObjectTabularStreamIterator<T> implements ObjectTabularStreamIterator<T> {

	@Override
	public final void next(final T[] target) {
		for (int i = 0; i < target.length; i++) {
			target[i] = valueFromColumn(i);
		}
	}
}
