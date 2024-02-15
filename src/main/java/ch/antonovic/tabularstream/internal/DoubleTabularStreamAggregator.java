package ch.antonovic.tabularstream.internal;

import ch.antonovic.tabularstream.DoubleTabularStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntFunction;

public class DoubleTabularStreamAggregator {

	private static final Logger LOGGER = LogManager.getLogger(DoubleTabularStreamAggregator.class);

	private DoubleTabularStreamAggregator() {

	}

	public static Optional<double[]> aggregateRowsWithSameOperator(final DoubleTabularStream stream, final DoubleBinaryOperator binaryOperator) {
		return aggregateRows(stream, i -> binaryOperator);
	}

	public static Optional<double[]> aggregateRows(final DoubleTabularStream stream, final DoubleBinaryOperator... binaryOperators) {
		final var numberOfColumns = stream.getNumberOfColumns();
		if (binaryOperators.length != numberOfColumns) {
			throw new IllegalArgumentException("Number of binary operators for aggregation doesn't match the number of columns. Expected are " + numberOfColumns + ", but given are " + binaryOperators.length);
		}
		return aggregateRows(stream, i -> binaryOperators[i]);
	}

	private static Optional<double[]> aggregateRows(final DoubleTabularStream stream, final IntFunction<DoubleBinaryOperator> doubleBinaryOperatorIntFunction) {
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
				resultRow[i] = doubleBinaryOperatorIntFunction.apply(i).applyAsDouble(resultRow[i], currentValue[i]);
			}
			LOGGER.debug("actual result value: {}", () -> Arrays.toString(resultRow));
		}

		return Optional.of(resultRow);
	}
}
