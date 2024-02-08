package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.RowsIterator;
import jdk.incubator.vector.FloatVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class SimdHelper {

	private static final Logger LOGGER = LogManager.getLogger(FloatUnaryTabularStreamWithColumn.class);

	private SimdHelper() {

	}

	public static float[] fusedMapUnaryAndThenToArray(final FloatTabularStream stream, final UnaryOperator<FloatVector> unaryOperator, final FloatUnaryOperator floatUnaryOperator) {
		final var countedLength = stream.count();
		LOGGER.debug("counted length: {}", countedLength);
		LOGGER.debug("number of columns: {}", stream.getNumberOfColumns());
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var result = new float[(int) countedLength];

		final var iterator1 = (RowsIterator) stream.iterator();
		final var species = FloatVector.SPECIES_PREFERRED;
		final var stepWidth = species.length();
		while (iterator1.hasNext(stepWidth)) {
			iterator1.nextChunkWithUnaryMapping(species, unaryOperator, result);
			iterator1.moveCursorToNextPosition(stepWidth);
		}
		final var iterator2 = stream.mapUnary(floatUnaryOperator).iterator();
		var counter2 = (int) iterator1.numberOfDeliveredElements();
		iterator2.moveCursorToNextPosition(counter2);
		for (; iterator2.hasNext(); iterator2.moveCursorToNextPosition()) {
			result[counter2++] = iterator2.valueFromColumn(0);
		}
		return result;
	}

	public static float[] fusedMapBinaryAndThenToArray(final FloatTabularStream stream, final BinaryOperator<FloatVector> unaryOperator, final FloatBinaryOperator floatBinaryOperator) {
		final var countedLength = stream.count();
		LOGGER.debug("counted length: {}", countedLength);
		LOGGER.debug("number of columns: {}", stream.getNumberOfColumns());
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var result = new float[(int) countedLength];

		final var iterator1 = (RowsIterator) stream.iterator();
		final var species = FloatVector.SPECIES_PREFERRED;
		final var stepWidth = species.length();
		while (iterator1.hasNext(stepWidth)) {
			iterator1.nextChunkWithBinaryMapping(species, unaryOperator, result);
			iterator1.moveCursorToNextPosition(stepWidth);
		}
		final var iterator2 = stream.mapBinary(floatBinaryOperator).iterator();
		var counter2 = (int) iterator1.numberOfDeliveredElements();
		iterator2.moveCursorToNextPosition(counter2);
		for (; iterator2.hasNext(); iterator2.moveCursorToNextPosition()) {
			result[counter2++] = iterator2.valueFromColumn(0);
		}
		return result;
	}

	public static float[] fusedMapTernaryAndThenToArray(final FloatTabularStream stream, final TernaryOperator<FloatVector> ternaryOperator, final FloatTernaryOperator floatTernaryOperator) {
		final var countedLength = stream.count();
		LOGGER.debug("counted length: {}", countedLength);
		LOGGER.debug("number of columns: {}", stream.getNumberOfColumns());
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var result = new float[(int) countedLength];

		final var iterator1 = (RowsIterator) stream.iterator();
		final var species = FloatVector.SPECIES_PREFERRED;
		final var stepWidth = species.length();
		while (iterator1.hasNext(stepWidth)) {
			iterator1.nextChunkWithTernaryMapping(species, ternaryOperator, result);
			iterator1.moveCursorToNextPosition(stepWidth);
		}
		final var iterator2 = stream.mapTernary(floatTernaryOperator).iterator();
		var counter2 = (int) iterator1.numberOfDeliveredElements();
		iterator2.moveCursorToNextPosition(counter2);
		for (; iterator2.hasNext(); iterator2.moveCursorToNextPosition()) {
			result[counter2++] = iterator2.valueFromColumn(0);
		}
		return result;
	}
}
