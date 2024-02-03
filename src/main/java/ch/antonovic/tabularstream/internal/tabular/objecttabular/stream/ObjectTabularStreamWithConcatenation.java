package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.ConcatenationIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class ObjectTabularStreamWithConcatenation<T> extends ObjectTabularStream<T> {

	private final ObjectTabularStream<T>[] streams;

	public ObjectTabularStreamWithConcatenation(final int numberOfColumns, final Class<T> type, final ObjectTabularStream<T>... streams) {
		super(numberOfColumns, type);
		this.streams = streams;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new ConcatenationIterator<>(streams);
	}

	@Override
	public boolean isInfinite() {
		return Arrays.stream(streams).anyMatch(ObjectTabularStream::isInfinite);
	}

	@Override
	public boolean isFiltered() {
		return Arrays.stream(streams).anyMatch(ObjectTabularStream::isInfinite);
	}

	@Override
	public int numberOfLayers() {
		final var max = Arrays.stream(streams).mapToInt(ObjectTabularStream::numberOfLayers).max();
		return 1 + max.orElse(0);
	}

	@Override
	public long estimatedGrossLength() {
		return Arrays.stream(streams).mapToLong(ObjectTabularStream::estimatedGrossLength).sum();
	}

	@Override
	public Optional<T[]> aggregateRows(final BinaryOperator<T>... binaryOperators) {
		final var tableWithOptionals = Arrays.stream(streams) //
				.map(s -> s.aggregateRows(binaryOperators)) //
				.toList();
		if (tableWithOptionals.stream().anyMatch(Optional::isEmpty)) {
			return Optional.empty();
		}
		final var table = tableWithOptionals.stream() //
				.map(o -> o.orElseThrow(() -> new IllegalStateException("Optional is empty"))) //
				.toList();
		final var numberOfColumns = table.size();
		if (numberOfColumns == 0) {
			return Optional.empty();
		}
		final var result = table.getFirst().clone();
		for (var k = 0; k < numberOfColumns; k++) {
			for (var i = 1; i < table.size(); i++) {
				final var array = table.get(i);
				if (array.length > 0) {
					result[k] = binaryOperators[k].apply(result[k], array[k]);
				}
			}
		}

		return Optional.of(result);
	}
}
