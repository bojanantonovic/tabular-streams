package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.InfinityIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.Optional;
import java.util.function.Supplier;

public class InfiniteFloatTabularStream extends FloatTabularStream {
	private final Supplier<float[]> supplier;

	public InfiniteFloatTabularStream(final int numberOfColumns, final Supplier<float[]> supplier) {
		super(numberOfColumns);
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
	public Optional<float[]> aggregateRows(final FloatBinaryOperator... binaryOperators) {
		throw new UnsupportedOperationException("Stream is infinite. Only special cases can be computed in finite time.");
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new InfinityIterator(supplier);
	}
}
