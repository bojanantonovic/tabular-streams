package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatFunction;
import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.internal.FloatTabularStreamAggregator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.stream.*;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.stream.ObjectTabularStreamWithFloatFunctionMapping;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class FloatTabularStream extends TabularStream<float[], FloatTabularStreamIterator>/*extends AbstractStream*/ /*implements TableStreamIterator<float[]>*/ {

	private static final Logger LOGGER = LogManager.getLogger(FloatTabularStream.class);

	protected FloatTabularStream(final int numberOfColumns) {
		super(numberOfColumns);
	}

	public static FloatTabularStream of(final float[]... columns) {
		return switch (columns.length) {
			case 1 -> new FloatUnaryTabularStreamWithColumn(columns[0]);
			case 2 -> new FloatBinaryTabularStreamWithColumns(columns[0], columns[1]);
			default -> new FloatTabularStreamWithColumns(columns);
		};
	}

	public static FloatTabularStream of(Optional<float[]> optional) {
		return optional.map(FloatUnaryTabularStream::of).orElse(new FloatUnaryTabularStreamWithColumn(new float[0]));
	}

	public static FloatTabularStream generator(final int numberOfColumns, final Supplier<float[]> supplier) {
		return new InfiniteFloatTabularStream(numberOfColumns, supplier);
	}

	public static FloatTabularStream generateRecursiveStream(final float[] first, final FloatUnaryOperator unaryOperator) {
		return new RecursiveOneStepFloatTabularStream(first, unaryOperator);
	}

	public static FloatTabularStream generateRecursiveStream(final float[] first, final float[] second, final FloatBinaryOperator binaryOperator) {
		return new RecursiveTwoStepFloatTabularStream(first, second, binaryOperator);
	}

	public static FloatTabularStream generateRecursiveStream(final float[] first, final float[] second, final float[] third, final FloatTernaryOperator ternaryOperator) {
		return new RecursiveThreeStepFloatTabularStream(first, second, third, ternaryOperator);
	}

	public static FloatTabularStream concat(final FloatTabularStream... streams) {
		final var numberOfColumns = getNumberOfColumns(streams);
		return new FloatTabularStreamWithConcatenation(numberOfColumns, streams);
	}

	@Override
	public abstract FloatTabularStreamIterator iterator();

	@Override
	public FloatTabularStream limit(final int length) {
		return new FloatTabularStreamWithLengthLimit(this, length);
	}

	@Override
	public FloatTabularStream filter(final Predicate<float[]> predicate) {
		return new FloatTabularStreamWithFilter(this, predicate);
	}

	public final FloatUnaryTabularStream mapUnary(final FloatUnaryOperator operator) {
		checkRequiredArity(this, 1);
		return new FloatTabularStreamWithUnaryMapping(this, operator);
	}

	public final <T> ObjectTabularStream<T> mapToObject(final FloatFunction<T> operator, final Class<T> type) {
		checkRequiredArity(this, 1);
		return new ObjectTabularStreamWithFloatFunctionMapping<>(this, operator, type);
	}

	public final FloatUnaryTabularStream mapBinary(final FloatBinaryOperator operator) {
		checkRequiredArity(this, 2);
		return new FloatTabularStreamWithBinaryMapping(this, operator);
	}

	public FloatTabularStream mapAllValuesUnary(final FloatUnaryOperator operator) {
		return new FloatTabularStreamWithAllValuesUnaryMapping(this, operator);
	}

	public FloatTabularStream mapColumnsUnary(final FloatUnaryOperator... operators) {
		return new FloatTabularStreamWithAllColumnsUnaryMapping(this, operators);
	}

	public Optional<float[]> aggregateRows(final FloatBinaryOperator... binaryOperators) {
		return FloatTabularStreamAggregator.aggregateRows(this, binaryOperators);
	}

	public Optional<float[]> aggregateRowsWithSameOperator(final FloatBinaryOperator binaryOperator) {
		return FloatTabularStreamAggregator.aggregateRowsWithSameOperator(this, binaryOperator);
	}

	public float[][] toArrayColumnStored() {
		return toArrayColumnStored(float[][]::new, float[]::new);
	}

	@Override
	public float[][] toArrayColumnStored(final IntFunction<float[][]> tableGenerator, final IntFunction<float[]> columnGenerator) {
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

	public Optional<float[]> lengthsOfVectors(final FloatTabularStream stream) {
		final FloatUnaryOperator sqrt = x -> (float) Math.sqrt(x);

		final var squaredLengths = stream.mapAllValuesUnary(v -> v * v) //
				.aggregateRowsWithSameOperator(Float::sum);
		if (squaredLengths.isEmpty()) {
			return Optional.empty();
		}
		final var lengths = FloatArrayMapper.mapColumnsUnary(squaredLengths.get(), sqrt);
		return Optional.of(lengths);
	}
}
