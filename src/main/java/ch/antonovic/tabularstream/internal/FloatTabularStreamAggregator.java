package ch.antonovic.tabularstream.internal;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.IntFunction;

public class FloatTabularStreamAggregator {

	private static final Logger LOGGER = LogManager.getLogger(FloatTabularStreamAggregator.class);

	private FloatTabularStreamAggregator() {

	}

	public static Optional<float[]> aggregateRowsWithSameOperator(final FloatTabularStream stream, final FloatBinaryOperator binaryOperator) {
		return aggregateRows(stream, i -> binaryOperator);
	}

	public static Optional<float[]> aggregateRows(final FloatTabularStream stream, final FloatBinaryOperator... binaryOperators) {
		final var numberOfColumns = stream.getNumberOfColumns();
		if (binaryOperators.length != numberOfColumns) {
			throw new IllegalArgumentException("Number of binary operators for aggregation doesn't match the number of columns. Expected are " + numberOfColumns + ", but given are " + binaryOperators.length);
		}
		return aggregateRows(stream, i -> binaryOperators[i]);
	}

	private static Optional<float[]> aggregateRows(final FloatTabularStream stream, final IntFunction<FloatBinaryOperator> floatBinaryOperatorIntFunction) {
		final var numberOfColumns = stream.getNumberOfColumns();
		final var length = stream.count();
		if (length == 0) {
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
				resultRow[i] = floatBinaryOperatorIntFunction.apply(i).applyAsFloat(resultRow[i], currentValue[i]);
			}
			LOGGER.debug("actual result value: {}", () -> Arrays.toString(resultRow));
		}

		return Optional.of(resultRow);
	}
}
