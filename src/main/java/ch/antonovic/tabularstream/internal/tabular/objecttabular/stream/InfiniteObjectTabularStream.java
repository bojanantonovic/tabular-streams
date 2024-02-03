package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.InfinityIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class InfiniteObjectTabularStream<T> extends ObjectTabularStream<T> {
	private final Supplier<T[]> supplier;

	public InfiniteObjectTabularStream(final int numberOfColumns, final Class<T> type, final Supplier<T[]> supplier) {
		super(numberOfColumns, type);
		this.supplier = supplier;
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

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new InfinityIterator<>(supplier);
	}
}
