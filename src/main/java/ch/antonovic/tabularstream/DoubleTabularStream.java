package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.internal.DoubleTabularStreamAggregator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.stream.*;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.stream.ObjectTabularStreamWithDoubleFunctionMapping;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.*;

public abstract class DoubleTabularStream extends TabularStream<double[], DoubleTabularStreamIterator>/*extends AbstractStream*/ /*implements TableStreamIterator<double[]>*/ {

	private static final Logger LOGGER = LogManager.getLogger(DoubleTabularStream.class);

	protected DoubleTabularStream(final int numberOfColumns) {
		super(numberOfColumns);
	}

	public static DoubleTabularStream of(final double[]... columns) {
		return switch (columns.length) {
			case 1 -> new DoubleUnaryTabularStreamWithColumn(columns[0]);
			case 2 -> new DoubleBinaryTabularStreamWithColumns(columns[0], columns[1]);
			default -> new DoubleTabularStreamWithColumns(columns);
		};
	}

	public static DoubleTabularStream generator(final int numberOfColumns, final Supplier<double[]> supplier) {
		return new InfiniteDoubleTabularStream(numberOfColumns, supplier);
	}

	public static DoubleTabularStream generateRecursiveStream(final double[] first, final DoubleUnaryOperator unaryOperator) {
		return new RecursiveOneStepDoubleTabularStream(first, unaryOperator);
	}

	public static DoubleTabularStream generateRecursiveStream(final double[] first, final double[] second, final DoubleBinaryOperator binaryOperator) {
		return new RecursiveTwoStepDoubleTabularStream(first, second, binaryOperator);
	}

	public static DoubleTabularStream generateRecursiveStream(final double[] first, final double[] second, final double[] third, final DoubleTernaryOperator ternaryOperator) {
		return new RecursiveThreeStepDoubleTabularStream(first, second, third, ternaryOperator);
	}

	public static DoubleTabularStream concat(final DoubleTabularStream... streams) {
		final int numberOfColumns = getNumberOfColumns(streams);
		return new DoubleTabularStreamWithConcatenation(numberOfColumns, streams);
	}

	@Override
	public abstract DoubleTabularStreamIterator iterator();

	@Override
	public DoubleTabularStream limit(final int length) {
		return new DoubleTabularStreamWithLengthLimit(this, length);
	}

	@Override
	public DoubleTabularStream filter(final Predicate<double[]> predicate) {
		return new DoubleTabularStreamWithFilter(this, predicate);
	}

	public final DoubleUnaryTabularStream mapUnary(final DoubleUnaryOperator operator) {
		checkRequiredArity(this, 1);
		return new DoubleTabularStreamWithUnaryMapping(this, operator);
	}

	public final <T> ObjectTabularStream<T> mapToObject(final DoubleFunction<T> operator, final Class<T> type) {
		checkRequiredArity(this, 1);
		return new ObjectTabularStreamWithDoubleFunctionMapping<>(this, operator, type);
	}

	public final DoubleUnaryTabularStream mapBinary(final DoubleBinaryOperator operator) {
		checkRequiredArity(this, 2);
		return new DoubleTabularStreamWithBinaryMapping(this, operator);
	}

	public DoubleTabularStream mapAllValuesUnary(final DoubleUnaryOperator operator) {
		return new DoubleTabularStreamWithAllValuesUnaryMapping(this, operator);
	}

	public DoubleTabularStream mapColumnsUnary(final DoubleUnaryOperator... operators) {
		return new DoubleTabularStreamWithAllColumnsUnaryMapping(this, operators);
	}

	public Optional<double[]> aggregateRows(final DoubleBinaryOperator... binaryOperators) {
		return DoubleTabularStreamAggregator.aggregateRows(this, binaryOperators);
	}

	public Optional<double[]> aggregateRowsWithSameOperator(final DoubleBinaryOperator binaryOperator) {
		return DoubleTabularStreamAggregator.aggregateRowsWithSameOperator(this, binaryOperator);
	}

	public double[][] toArrayColumnStored() {
		return toArrayColumnStored(double[][]::new, double[]::new);
	}

	@Override
	public double[][] toArrayColumnStored(final IntFunction<double[][]> tableGenerator, final IntFunction<double[]> columnGenerator) {
		final var countedLength = count();
		LOGGER.debug("counted length: {}", countedLength);
		LOGGER.debug("number of columns: {}", numberOfColumns);
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var validLength = (int) countedLength;
		final var result = tableGenerator.apply(getNumberOfColumns());
		for (var i = 0; i < numberOfColumns; i++) {
			result[i] = columnGenerator.apply(validLength);
		}
		final var iterator = iterator();
		for (var counter = 0; iterator.hasNext(); counter++) {
			final var value = iterator.next();
			LOGGER.debug("value to map: {}", () -> Arrays.toString(value));
			for (var i = 0; i < numberOfColumns; i++) {
				result[i][counter] = value[i];//iterator.valueFromColumn(i);
			}
		}

		iterator.reset();
		return result;
	}
}
