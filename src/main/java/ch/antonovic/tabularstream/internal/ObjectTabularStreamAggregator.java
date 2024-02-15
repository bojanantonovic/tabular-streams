package ch.antonovic.tabularstream.internal;

import ch.antonovic.tabularstream.ObjectTabularStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

public class ObjectTabularStreamAggregator {

	private static final Logger LOGGER = LogManager.getLogger(ObjectTabularStreamAggregator.class);

	private ObjectTabularStreamAggregator() {

	}

	public static <T> Optional<T[]> aggregateRowsWithSameOperator(final ObjectTabularStream<T> stream, final BinaryOperator<T> binaryOperator) {
		return aggregateRows(stream, i -> binaryOperator);
	}

	@SafeVarargs
	public static <T> Optional<T[]> aggregateRows(final ObjectTabularStream<T> stream, final BinaryOperator<T>... binaryOperators) {
		final var numberOfColumns = stream.getNumberOfColumns();
		if (binaryOperators.length != numberOfColumns) {
			throw new IllegalArgumentException("Number of binary operators for aggregation doesn't match the number of columns. Expected are " + numberOfColumns + ", but given are " + binaryOperators.length);
		}
		return aggregateRows(stream, i -> binaryOperators[i]);
	}

	private static <T> Optional<T[]> aggregateRows(final ObjectTabularStream<T> stream, final IntFunction<BinaryOperator<T>> binaryOperatorIntFunction) {
		final var numberOfColumns = stream.getNumberOfColumns();

		final var length = stream.count();
		if (length.isEmpty() || length.getAsLong() == 0L) {
			return Optional.empty();
		}
		final var iterator = stream.iterator();
		final var resultRow = iterator.next().clone();
		LOGGER.debug("first value: {}", () -> Arrays.toString(resultRow));
		for (var position = 1L; iterator.hasNext(); position++) {
			final var currentValue = iterator.next();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("current ({}) value: {}", position, Arrays.toString(currentValue));
			}
			for (var i = 0; i < numberOfColumns; i++) {
				resultRow[i] = binaryOperatorIntFunction.apply(i).apply(resultRow[i], currentValue[i]);
			}
			LOGGER.debug("actual result value: {}", () -> Arrays.toString(resultRow));
		}

		return Optional.of(resultRow);
	}
}
