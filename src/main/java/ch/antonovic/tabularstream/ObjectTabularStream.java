package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.ObjectTabularStreamAggregator;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.stream.*;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.*;

public abstract class ObjectTabularStream<T> extends TabularStream<T[], ObjectTabularStreamIterator<T>>/*extends AbstractStream*/ /*implements TableStreamIterator<T[]>*/ {

	private static final Logger LOGGER = LogManager.getLogger(ObjectTabularStream.class);

	protected final Class<T> type;

	protected ObjectTabularStream(final int numberOfColumns, final Class<T> type) {
		super(numberOfColumns);
		this.type = type;
	}

	public static <T> ObjectTabularStream<T> of(final Class<T> type, final T[]... columns) {
		return switch (columns.length) {
			case 1 -> new ObjectUnaryTabularStreamWithColumn<>(type, columns[0]);
			case 2 -> new ObjectBinaryTabularStreamWithColumns<>(type, columns[0], columns[1]);
			default -> new ObjectTabularStreamWithColumns<>(type, columns);
		};
	}

	public static <T> ObjectTabularStream<T> generator(final int numberOfColumns, final Class<T> type, final Supplier<T[]> supplier) {
		return new InfiniteObjectTabularStream<>(numberOfColumns, type, supplier);
	}

	public static <T> ObjectTabularStream<T> generateRecursiveStream(final T[] first, final UnaryOperator<T> unaryOperator) {
		return new RecursiveOneStepObjectTabularStream<>(first, unaryOperator);
	}

	public static <T> ObjectTabularStream<T> generateRecursiveStream(final Class<T> type, final T[] first, final T[] second, final BinaryOperator<T> binaryOperator) {
		return new RecursiveTwoStepObjectTabularStream<>(type, first, second, binaryOperator);
	}

	public static <T> ObjectTabularStream<T> generateRecursiveStream(final Class<T> type, final T[] first, final T[] second, final T[] third, final TernaryOperator<T> ternaryOperator) {
		return new RecursiveThreeStepObjectTabularStream<>(type, first, second, third, ternaryOperator);
	}

	public static <T> ObjectTabularStream<T> concat(final Class<T> type, final ObjectTabularStream<T>... streams) {
		final var numberOfColumns = getNumberOfColumns(streams);
		return new ObjectTabularStreamWithConcatenation<>(numberOfColumns, type, streams);
	}

	public Class<T> getType() {
		return type;
	}

	@Override
	public abstract ObjectTabularStreamIterator<T> iterator();

	@Override
	public ObjectTabularStream<T> limit(final int length) {
		return new ObjectTabularStreamWithLengthLimit<>(this, length);
	}

	@Override
	public ObjectTabularStream<T> filter(final Predicate<T[]> predicate) {
		return new ObjectTabularStreamWithFilter<>(this, predicate);
	}

	public final ObjectUnaryTabularStream<T> mapUnary(final UnaryOperator<T> operator) {
		checkRequiredArity(this, 1);
		return new ObjectTabularStreamWithUnaryMapping<>(this, operator);
	}
	// TODO
/*
	public final <U> ObjectTabularStream<T> mapToObject(final Function<T,U> operator, final Class<U> type) {
		checkRequiredArity(this, 1);
		return new ObjectTabularStreamWithObjectFunctionMapping<>(this, operator, type);
	}*/

	public final ObjectUnaryTabularStream<T> mapBinary(final BinaryOperator<T> operator) {
		checkRequiredArity(this, 2);
		return new ObjectTabularStreamWithBinaryMapping<>(this, operator);
	}

	public ObjectTabularStream<T> mapAllValuesUnary(final UnaryOperator<T> operator) {
		return new ObjectTabularStreamWithAllValuesUnaryMapping(this, operator);
	}

	public ObjectTabularStream<T> mapColumnsUnary(final UnaryOperator<T>... operators) {
		return new ObjectTabularStreamWithAllColumnsUnaryMapping<>(this, operators);
	}

	public Optional<T[]> aggregateRows(final BinaryOperator<T>... binaryOperators) {
		return ObjectTabularStreamAggregator.aggregateRows(this, binaryOperators);
	}

	public Optional<T[]> aggregateRowsWithSameOperator(final BinaryOperator<T> binaryOperator) {
		return ObjectTabularStreamAggregator.aggregateRowsWithSameOperator(this, binaryOperator);
	}

	@Override
	public T[][] toArrayColumnStored(final IntFunction<T[][]> tableGenerator, final IntFunction<T[]> columnGenerator) {
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
