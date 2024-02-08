package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RowsIterator;
import jdk.incubator.vector.DoubleVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public class SimdHelper {

	private static final Logger LOGGER = LogManager.getLogger(DoubleUnaryTabularStreamWithColumn.class);

	private SimdHelper() {

	}

	public static double[] fusedMapUnaryAndThenToArray(final DoubleTabularStream stream, final UnaryOperator<DoubleVector> unaryOperator, final DoubleUnaryOperator doubleUnaryOperator) {
		final var countedLength = stream.count();
		LOGGER.debug("counted length: {}", countedLength);
		LOGGER.debug("number of columns: {}", stream.getNumberOfColumns());
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var result = new double[(int) countedLength];

		final var iterator1 = (RowsIterator) stream.iterator();
		final var species = DoubleVector.SPECIES_PREFERRED;
		final var stepWidth = species.length();
		while (iterator1.hasNext(stepWidth)) {
			iterator1.nextChunkWithUnaryMapping(species, unaryOperator, result);
			iterator1.moveCursorToNextPosition(stepWidth);
		}
		final var iterator2 = stream.mapUnary(doubleUnaryOperator).iterator();
		var counter2 = (int) iterator1.numberOfDeliveredElements();
		iterator2.moveCursorToNextPosition(counter2);
		for (; iterator2.hasNext(); iterator2.moveCursorToNextPosition()) {
			result[counter2++] = iterator2.valueFromColumn(0);
		}
		return result;
	}

	public static double[] fusedMapBinaryAndThenToArray(final DoubleTabularStream stream, final BinaryOperator<DoubleVector> unaryOperator, final DoubleBinaryOperator doubleBinaryOperator) {
		final var countedLength = stream.count();
		LOGGER.debug("counted length: {}", countedLength);
		LOGGER.debug("number of columns: {}", stream.getNumberOfColumns());
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var result = new double[(int) countedLength];

		final var iterator1 = (RowsIterator) stream.iterator();
		final var species = DoubleVector.SPECIES_PREFERRED;
		final var stepWidth = species.length();
		while (iterator1.hasNext(stepWidth)) {
			iterator1.nextChunkWithBinaryMapping(species, unaryOperator, result);
			iterator1.moveCursorToNextPosition(stepWidth);
		}
		final var iterator2 = stream.mapBinary(doubleBinaryOperator).iterator();
		var counter2 = (int) iterator1.numberOfDeliveredElements();
		iterator2.moveCursorToNextPosition(counter2);
		for (; iterator2.hasNext(); iterator2.moveCursorToNextPosition()) {
			result[counter2++] = iterator2.valueFromColumn(0);
		}
		return result;
	}

	public static double[] fusedMapTernaryAndThenToArray(final DoubleTabularStream stream, final TernaryOperator<DoubleVector> ternaryOperator, final DoubleTernaryOperator doubleTernaryOperator) {
		final var countedLength = stream.count();
		LOGGER.debug("counted length: {}", countedLength);
		LOGGER.debug("number of columns: {}", stream.getNumberOfColumns());
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		final var result = new double[(int) countedLength];

		final var iterator1 = (RowsIterator) stream.iterator();
		final var species = DoubleVector.SPECIES_PREFERRED;
		final var stepWidth = species.length();
		while (iterator1.hasNext(stepWidth)) {
			iterator1.nextChunkWithTernaryMapping(species, ternaryOperator, result);
			iterator1.moveCursorToNextPosition(stepWidth);
		}
		final var iterator2 = stream.mapTernary(doubleTernaryOperator).iterator();
		var counter2 = (int) iterator1.numberOfDeliveredElements();
		iterator2.moveCursorToNextPosition(counter2);
		for (; iterator2.hasNext(); iterator2.moveCursorToNextPosition()) {
			result[counter2++] = iterator2.valueFromColumn(0);
		}
		return result;
	}
}
