package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.CountingHelper;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.ConcatenationIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class FloatTabularStreamWithConcatenation extends FloatTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(FloatUnaryTabularStreamWithColumn.class);

	private final FloatTabularStream[] streams;

	public FloatTabularStreamWithConcatenation(final int numberOfColumns, final FloatTabularStream... streams) {
		super(numberOfColumns);
		this.streams = streams;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new ConcatenationIterator(streams);
	}

	@Override
	public boolean isInfinite() {
		return Arrays.stream(streams).anyMatch(FloatTabularStream::isInfinite);
	}

	@Override
	public boolean isFiltered() {
		return Arrays.stream(streams).anyMatch(FloatTabularStream::isInfinite);
	}

	@Override
	public int numberOfLayers() {
		final var max = Arrays.stream(streams).mapToInt(FloatTabularStream::numberOfLayers).max();
		return 1 + max.orElse(0);
	}

	@Override
	public OptionalLong count() {
		return CountingHelper.countForConcatenation(streams);
	}

	@Override
	public long estimatedGrossLength() {
		return Arrays.stream(streams).mapToLong(FloatTabularStream::estimatedGrossLength).sum();
	}

	@Override
	public Optional<float[]> aggregateRows(final FloatBinaryOperator... binaryOperators) {
		final var tableWithOptionals = Arrays.stream(streams) //
				.map(s -> s.aggregateRows(binaryOperators)) //
				.toList();
		if (tableWithOptionals.stream().anyMatch(Optional::isEmpty)) {
			return Optional.empty();
		}
		final var table = tableWithOptionals.stream() //
				.map(o -> o.orElseThrow(() -> new IllegalStateException("Optional is empty"))) //
				.toArray(float[][]::new);
		final var numberOfColumns = table.length;
		if (numberOfColumns == 0) {
			return Optional.empty();
		}
		final var result = table[0];
		for (var k = 0; k < numberOfColumns; k++) {
			for (var i = 1; i < table.length; i++) {
				final var array = table[i];
				if (array.length > 0) {
					result[k] = binaryOperators[k].applyAsFloat(result[k], array[k]);
				}
			}
		}

		return Optional.of(result);
	}

	@Override
	public float[] fusedMapUnaryAndThenToArray(final UnaryOperator<FloatVector> unaryOperator, final FloatUnaryOperator floatUnaryOperator) {
		final var result = new float[(int) CountingHelper.computeLengthAndVerifyIt(this)];
		var counter = 0;
		final var floatBuffer = FloatBuffer.wrap(result);
		for (final var stream : streams) {
			final var array = stream.fusedMapUnaryAndThenToArray(unaryOperator, floatUnaryOperator);
			floatBuffer.put(counter, array);
			counter += array.length;
		}
		return result;
	}

	@Override
	public float[] fusedMapBinaryAndThenToArray(final BinaryOperator<FloatVector> binaryOperator, final FloatBinaryOperator floatBinaryOperator) {
		final var result = new float[(int) CountingHelper.computeLengthAndVerifyIt(this)];
		var counter = 0;
		final var floatBuffer = FloatBuffer.wrap(result);
		for (final var stream : streams) {
			final var array = stream.fusedMapBinaryAndThenToArray(binaryOperator, floatBinaryOperator);
			floatBuffer.put(counter, array);
			counter += array.length;
		}
		return result;
	}

	@Override
	public float[] fusedMapTernaryAndThenToArray(final TernaryOperator<FloatVector> ternaryOperator, final FloatTernaryOperator floatTernaryOperator) {
		final var result = new float[(int) CountingHelper.computeLengthAndVerifyIt(this)];
		var counter = 0;
		final var floatBuffer = FloatBuffer.wrap(result);
		for (final var stream : streams) {
			final var array = stream.fusedMapTernaryAndThenToArray(ternaryOperator, floatTernaryOperator);
			floatBuffer.put(counter, array);
			counter += array.length;
		}
		return result;
	}
}
