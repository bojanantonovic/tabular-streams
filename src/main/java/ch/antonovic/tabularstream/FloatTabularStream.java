package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.internal.FloatTabularStreamAggregator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.stream.*;
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

	/*
	public FloatTabularStream mapAllValuesUnary(final FloatUnaryOperator operator) {
		return new FloatTabularStreamWithAllValuesUnaryMapping(this, operator);
	}*/

	public final FloatUnaryTabularStream mapUnary(final FloatUnaryOperator operator) {
		if (getNumberOfColumns() != 1) {
			throw new IllegalArgumentException("Wrong arity for this operation! Is " + getNumberOfColumns() + ", required 1");
		}
		return new FloatTabularStreamWithUnaryMapping(this, operator);
	}

	public final FloatUnaryTabularStream mapBinary(final FloatBinaryOperator operator) {
		if (getNumberOfColumns() != 2) {
			throw new IllegalArgumentException("Wrong arity for this operation! Is " + getNumberOfColumns() + ", required 2");
		}
		return new FloatTabularStreamWithBinaryMapping(this, operator);
	}

	public Optional<float[]> aggregateRows(final FloatBinaryOperator... binaryOperators) {
		return FloatTabularStreamAggregator.aggregateRows(this, binaryOperators);
	}

	public Optional<float[]> aggregateRowsWithSameOperator(final FloatBinaryOperator binaryOperator) {
		return FloatTabularStreamAggregator.aggregateRowsWithSameOperator(this, binaryOperator);
	}

	public float[][] toArraysColumStored(final IntFunction<float[][]> tableGenerator, final IntFunction<float[]> columnGenerator) {
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
}
