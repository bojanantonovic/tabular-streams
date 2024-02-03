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

	// TODO
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
			iterator.next(); // TODO side effect based
			for (var i = 0; i < numberOfColumns; i++) {
				result[i][counter] = iterator.valueFromColumn(i);
			}
		}

		iterator.reset();
		return result;
	}

	public static float[] mapColumnsUnary(final float[] row, final FloatUnaryOperator operator) {
		final float[] result = new float[row.length];
		for (var i = 0; i < row.length; i++) {
			result[i] = operator.applyAsFloat(row[i]);
		}
		return result;
	}
/*
	public static Optional<float[]> squaredLengthsOfVectors(final FloatTabularStream stream) {
		final FloatUnaryOperator sqrt = x -> (float) Math.sqrt(x);
		final FloatUnaryOperator reciproke = x -> 1f / x;

		final var floats = stream.mapAllValuesUnary(v -> v * v) //
				.aggregateRowsWithSameOperator(Float::sum) //
				.orElseThrow(IllegalStateException::new);
		final var y2 = mapColumnsUnary(floats, sqrt);
		final var y3 = mapColumnsUnary(y2, reciproke);

		FloatTabularStream.of(floats).mapAllValuesUnary(sqrt).mapAllValuesUnary(reciproke).
				.map(FloatTabularStream::of) //
	}*/

	//	public Optional<float[]> lengthsOfVectors(final FloatTabularStream stream) {
	//		final FloatUnaryOperator sqrt = x -> (float) Math.sqrt(x);
	//		final FloatUnaryOperator reciproke = x -> 1f / x;
	///*
	//		return squaredLengthsOfVectors(stream) //
	//				.map(x->mapColumnsUnary(x,sqrt)) //
	//				.map(x->mapColumnsUnary(x->reciproke))*/
	//
	//		final var squaredLengths = stream.mapAllValuesUnary(v -> v * v) //
	//				.aggregateRowsWithSameOperator(Float::sum) //
	//				.orElseThrow(IllegalStateException::new);
	//		final var y2 = mapColumnsUnary(squaredLengths, sqrt);
	//		final var y3 = mapColumnsUnary(y2, reciproke);
	//
	//		FloatTabularStream.of(squaredLengthd).mapAllValuesUnary(sqrt).mapAllValuesUnary(reciproke).
	//				.map(FloatTabularStream::of) //
	//	}
}
