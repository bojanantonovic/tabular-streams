package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.PredicateIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.Predicate;

public class ObjectTabularStreamWithFilter<T> extends ObjectTabularStreamWrapper<T> {

	private final Predicate<T[]> predicate;

	public ObjectTabularStreamWithFilter(final ObjectTabularStream<T> parent, final Predicate<T[]> predicate) {
		super(parent);
		this.predicate = predicate;
	}

	@Override
	public boolean isFiltered() {
		return true;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new PredicateIterator<>(parent.iterator(), predicate);
	}
}
