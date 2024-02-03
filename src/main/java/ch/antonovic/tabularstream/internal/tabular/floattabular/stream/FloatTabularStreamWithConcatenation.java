package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.ConcatenationIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.Arrays;
import java.util.Optional;

public class FloatTabularStreamWithConcatenation extends FloatTabularStream {

	private final FloatTabularStream[] streams;

	public FloatTabularStreamWithConcatenation(final int numberOfColumns, final FloatTabularStream... streams) {
		super(numberOfColumns);
		this.streams = streams;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new ConcatenationIterator(streams);
	}

	@Override
	public boolean isInfinite() {
		return Arrays.stream(streams).anyMatch(FloatTabularStream::isInfinite);
	}

	@Override
	public boolean isFiltered() {
		return Arrays.stream(streams).anyMatch(FloatTabularStream::isInfinite);
	}

	@Override
	public int numberOfLayers() {
		final var max = Arrays.stream(streams).mapToInt(FloatTabularStream::numberOfLayers).max();
		return 1 + max.orElse(0);
	}

	@Override
	public long estimatedGrossLength() {
		return Arrays.stream(streams).mapToLong(FloatTabularStream::estimatedGrossLength).sum();
	}

	@Override
	public Optional<float[]> aggregateRows(final FloatBinaryOperator... binaryOperators) {
		final var tableWithOptionals = Arrays.stream(streams) //
				.map(s -> s.aggregateRows(binaryOperators)) //
				.toList();
		if (tableWithOptionals.stream().anyMatch(Optional::isEmpty)) {
			return Optional.empty();
		}
		final var table = tableWithOptionals.stream() //
				.map(o -> o.orElseThrow(() -> new IllegalStateException("Optional is empty"))) //
				.toArray(float[][]::new);
		final var numberOfColumns = table.length;
		if (numberOfColumns == 0) {
			return Optional.empty();
		}
		final var result = table[0];
		for (var k = 0; k < numberOfColumns; k++) {
			for (var i = 1; i < table.length; i++) {
				final var array = table[i];
				if (array.length > 0) {
					result[k] = binaryOperators[k].applyAsFloat(result[k], array[k]);
				}
			}
		}

		return Optional.of(result);
	}
}
