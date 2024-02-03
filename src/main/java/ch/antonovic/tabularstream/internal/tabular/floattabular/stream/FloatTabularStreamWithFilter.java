package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.PredicateIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.function.Predicate;

public class FloatTabularStreamWithFilter extends FloatTabularStreamWrapper {

	private final Predicate<float[]> predicate;

	public FloatTabularStreamWithFilter(final FloatTabularStream parent, final Predicate<float[]> predicate) {
		super(parent);
		this.predicate = predicate;
	}

	@Override
	public boolean isFiltered() {
		return true;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new PredicateIterator(parent.iterator(), predicate);
	}
}
