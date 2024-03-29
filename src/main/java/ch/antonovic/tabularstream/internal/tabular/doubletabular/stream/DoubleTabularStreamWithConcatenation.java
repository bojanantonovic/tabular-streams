package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.CountingHelper;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.ConcatenationIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public class DoubleTabularStreamWithConcatenation extends DoubleTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(DoubleTabularStreamWithConcatenation.class);

	private final DoubleTabularStream[] streams;

	public DoubleTabularStreamWithConcatenation(final int numberOfColumns, final DoubleTabularStream... streams) {
		super(numberOfColumns);
		this.streams = streams;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new ConcatenationIterator(streams);
	}

	@Override
	public boolean isInfinite() {
		return Arrays.stream(streams).anyMatch(DoubleTabularStream::isInfinite);
	}

	@Override
	public boolean isFiltered() {
		return Arrays.stream(streams).anyMatch(DoubleTabularStream::isInfinite);
	}

	@Override
	public int numberOfLayers() {
		final var max = Arrays.stream(streams).mapToInt(DoubleTabularStream::numberOfLayers).max();
		return 1 + max.orElse(0);
	}

	@Override
	public OptionalLong count() {
		return CountingHelper.countForConcatenation(streams);
	}

	@Override
	public long estimatedGrossLength() {
		return Arrays.stream(streams).mapToLong(DoubleTabularStream::estimatedGrossLength).sum();
	}

	@Override
	public Optional<double[]> aggregateRows(final DoubleBinaryOperator... binaryOperators) {
		final var tableWithOptionals = Arrays.stream(streams) //
				.map(s -> s.aggregateRows(binaryOperators)) //
				.toList();
		if (tableWithOptionals.stream().anyMatch(Optional::isEmpty)) {
			return Optional.empty();
		}
		final var table = tableWithOptionals.stream() //
				.map(o -> o.orElseThrow(() -> new IllegalStateException("Optional is empty"))) //
				.toArray(double[][]::new);
		final var numberOfColumns = table.length;
		if (numberOfColumns == 0) {
			return Optional.empty();
		}
		final var result = table[0];
		for (var k = 0; k < numberOfColumns; k++) {
			for (var i = 1; i < table.length; i++) {
				final var array = table[i];
				if (array.length > 0) {
					result[k] = binaryOperators[k].applyAsDouble(result[k], array[k]);
				}
			}
		}

		return Optional.of(result);
	}

	@Override
	public double[] fusedMapUnaryAndThenToArray(final UnaryOperator<DoubleVector> unaryOperator, final DoubleUnaryOperator doubleUnaryOperator) {
		final var result = new double[(int) CountingHelper.computeLengthAndVerifyIt(this)];
		LOGGER.debug("number of columns: {}", numberOfColumns);
		var counter = 0;
		final var doubleBuffer = DoubleBuffer.wrap(result);
		for (final var stream : streams) {
			final var array = stream.fusedMapUnaryAndThenToArray(unaryOperator, doubleUnaryOperator);
			doubleBuffer.put(counter, array);
			counter += array.length;
		}
		return result;
	}

	@Override
	public double[] fusedMapBinaryAndThenToArray(final BinaryOperator<DoubleVector> binaryOperator, final DoubleBinaryOperator doubleBinaryOperator) {
		final var result = new double[(int) CountingHelper.computeLengthAndVerifyIt(this)];
		var counter = 0;
		final var doubleBuffer = DoubleBuffer.wrap(result);
		for (final var stream : streams) {
			final var array = stream.fusedMapBinaryAndThenToArray(binaryOperator, doubleBinaryOperator);
			doubleBuffer.put(counter, array);
			counter += array.length;
		}
		return result;
	}

	@Override
	public double[] fusedMapTernaryAndThenToArray(final TernaryOperator<DoubleVector> ternaryOperator, final DoubleTernaryOperator doubleTernaryOperator) {
		final var result = new double[(int) CountingHelper.computeLengthAndVerifyIt(this)];
		var counter = 0;
		final var doubleBuffer = DoubleBuffer.wrap(result);
		for (final var stream : streams) {
			final var array = stream.fusedMapTernaryAndThenToArray(ternaryOperator, doubleTernaryOperator);
			doubleBuffer.put(counter, array);
			counter += array.length;
		}
		return result;
	}
}
