package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.PredicateIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.Predicate;

public class DoubleTabularStreamWithFilter extends DoubleTabularStreamWrapper {

	private final Predicate<double[]> predicate;

	public DoubleTabularStreamWithFilter(final DoubleTabularStream parent, final Predicate<double[]> predicate) {
		super(parent);
		this.predicate = predicate;
	}

	@Override
	public boolean isFiltered() {
		return true;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new PredicateIterator(parent.iterator(), predicate);
	}
}
