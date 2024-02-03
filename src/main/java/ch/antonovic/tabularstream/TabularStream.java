package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public abstract class TabularStream<R, I extends TabularStreamIterator<R>> {

	protected final int numberOfColumns;

	protected TabularStream(final int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public final int getNumberOfColumns() {
		return numberOfColumns;
	}

	public abstract boolean isInfinite();
	public abstract boolean isFiltered();
	public abstract int numberOfLayers();

	public abstract long estimatedGrossLength();

	public abstract I iterator();

	public abstract TabularStream<R, I> limit(int length);

	public abstract TabularStream<R, I> filter(Predicate<R> predicate);

	public abstract R[] toArrayColumnStored(IntFunction<R[]> tableGenerator, IntFunction<R> columnGenerator);

	public long count() {
		final var iterator = iterator();
		var counter = 0L;
		while (iterator.hasNext()) {
			iterator.incrementPositionWithoutReading();
			counter++;
		}

		iterator.reset();

		return counter;
	}

	public R[] toArray(final IntFunction<R[]> generator) {
		final var countedLength = count();
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var validLength = (int) countedLength;
		final var result = generator.apply(validLength);
		final var iterator = iterator();
		for (var counter = 0; iterator.hasNext(); counter++) {
			final var next = iterator.next();
			result[counter] = next;
		}

		iterator.reset();
		return result;
	}

	public static int getNumberOfColumns(final TabularStream<?, ?>[] streams) {
		final int numberOfColumns;
		switch (streams.length) {
			case 0 -> numberOfColumns = 0;
			case 1 -> numberOfColumns = streams[0].getNumberOfColumns();
			default -> {
				final var intSummaryStatistics = Arrays.stream(streams) //
						.mapToInt(TabularStream::getNumberOfColumns) //
						.summaryStatistics();
				if (intSummaryStatistics.getMax() != intSummaryStatistics.getMin()) {
					throw new IllegalArgumentException("Streams do not have same number of columns (cardinality)");
				}
				numberOfColumns = intSummaryStatistics.getMax();
			}
		}
		return numberOfColumns;
	}

	protected static void checkRequiredArity(final TabularStream<?, ?> stream, final int requiredArity) {
		if (stream.getNumberOfColumns() != requiredArity) {
			throw new IllegalArgumentException("Wrong arity for this operation! Is " + stream.getNumberOfColumns() + ", required " + requiredArity);
		}
	}
}
