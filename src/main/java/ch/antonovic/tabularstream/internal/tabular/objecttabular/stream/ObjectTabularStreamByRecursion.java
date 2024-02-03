package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;

import java.util.Optional;
import java.util.function.BinaryOperator;

public abstract class ObjectTabularStreamByRecursion<T> extends ObjectTabularStream<T> {

	protected ObjectTabularStreamByRecursion(final int numberOfColumns, final Class<T> type) {
		super(numberOfColumns, type);
	}

	@Override
	public boolean isInfinite() {
		return true;
	}

	@Override
	public boolean isFiltered() {
		return false;
	}

	@Override
	public int numberOfLayers() {
		return 1;
	}

	@Override
	public long estimatedGrossLength() {
		return Long.MAX_VALUE;
	}

	@Override
	public Optional<T[]> aggregateRows(final BinaryOperator<T>... binaryOperators) {
		throw new UnsupportedOperationException("Stream is infinite. Only special cases can be computed in finite time.");
	}
}
