package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.InfinityIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.Optional;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Supplier;

public class InfiniteDoubleTabularStream extends DoubleTabularStream {
	private final Supplier<double[]> supplier;

	public InfiniteDoubleTabularStream(final int numberOfColumns, final Supplier<double[]> supplier) {
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
	public Optional<double[]> aggregateRows(final DoubleBinaryOperator... binaryOperators) {
		throw new UnsupportedOperationException("Stream is infinite. Only special cases can be computed in finite time.");
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new InfinityIterator(supplier);
	}
}
