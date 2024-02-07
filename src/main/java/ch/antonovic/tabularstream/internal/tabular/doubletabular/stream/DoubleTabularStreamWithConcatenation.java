package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.ConcatenationIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

public class DoubleTabularStreamWithConcatenation extends DoubleTabularStream {

	private final DoubleTabularStream[] streams;

	public DoubleTabularStreamWithConcatenation(final int numberOfColumns, final DoubleTabularStream... streams) {
		super(numberOfColumns);
		this.streams = streams;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new ConcatenationIterator(streams);
	}

	@Override
	public boolean isInfinite() {
		return Arrays.stream(streams).anyMatch(DoubleTabularStream::isInfinite);
	}

	@Override
	public boolean isFiltered() {
		return Arrays.stream(streams).anyMatch(DoubleTabularStream::isInfinite);
	}

	@Override
	public int numberOfLayers() {
		final var max = Arrays.stream(streams).mapToInt(DoubleTabularStream::numberOfLayers).max();
		return 1 + max.orElse(0);
	}

	@Override
	public long count() {
		return Arrays.stream(streams).mapToLong(TabularStream::count).sum();
	}

	@Override
	public long estimatedGrossLength() {
		return Arrays.stream(streams).mapToLong(DoubleTabularStream::estimatedGrossLength).sum();
	}

	@Override
	public Optional<double[]> aggregateRows(final DoubleBinaryOperator... binaryOperators) {
		final var tableWithOptionals = Arrays.stream(streams) //
				.map(s -> s.aggregateRows(binaryOperators)) //
				.toList();
		if (tableWithOptionals.stream().anyMatch(Optional::isEmpty)) {
			return Optional.empty();
		}
		final var table = tableWithOptionals.stream() //
				.map(o -> o.orElseThrow(() -> new IllegalStateException("Optional is empty"))) //
				.toArray(double[][]::new);
		final var numberOfColumns = table.length;
		if (numberOfColumns == 0) {
			return Optional.empty();
		}
		final var result = table[0];
		for (var k = 0; k < numberOfColumns; k++) {
			for (var i = 1; i < table.length; i++) {
				final var array = table[i];
				if (array.length > 0) {
					result[k] = binaryOperators[k].applyAsDouble(result[k], array[k]);
				}
			}
		}

		return Optional.of(result);
	}
}
