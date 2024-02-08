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

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class FloatTabularStreamWithColumns extends FloatTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(FloatTabularStreamWithColumns.class);

	private final float[][] table;
	private final int numberOfRows;

	public FloatTabularStreamWithColumns(final float[]... table) {
		super(table.length);
		this.table = table;
		if (getNumberOfColumns() == 0) {
			this.numberOfRows = 0; // default
		} else {
			final var intSummaryStatistics = Arrays.stream(table) //
					.mapToInt(s -> s.length) //
					.summaryStatistics();
			if (intSummaryStatistics.getMax() != intSummaryStatistics.getMin()) {
				throw new IllegalArgumentException("Streams do not have same number of columns (cardinality)");
			}
			this.numberOfRows = intSummaryStatistics.getMax();
		}
	}

	@Override
	public long count() {
		return numberOfRows;
	}

	@Override
	public boolean isInfinite() {
		return false;
	}

	@Override
	public boolean isFiltered() {
		return false;
	}

	@Override
	public int numberOfLayers() {
		return 1;
	}

	@Override
	public long estimatedGrossLength() {
		return numberOfRows;
	}

	@Override
	public RowsIterator iterator() {
		return new RowsIterator(table, numberOfRows);
	}

	@Override
	public float[] fusedMapUnaryAndThenToArray(final UnaryOperator<FloatVector> unaryOperator, final FloatUnaryOperator floatUnaryOperator) {
		checkRequiredArity(this, 1);
		return SimdHelper.fusedMapUnaryAndThenToArray(this, unaryOperator, floatUnaryOperator);
	}

	@Override
	public float[] fusedMapBinaryAndThenToArray(final BinaryOperator<FloatVector> binaryOperator, final FloatBinaryOperator floatBinaryOperator) {
		checkRequiredArity(this, 2);
		return SimdHelper.fusedMapBinaryAndThenToArray(this, binaryOperator, floatBinaryOperator);
	}

	@Override
	public float[] fusedMapTernaryAndThenToArray(final TernaryOperator<FloatVector> ternaryOperator, final FloatTernaryOperator floatTernaryOperator) {
		checkRequiredArity(this, 3);
		return SimdHelper.fusedMapTernaryAndThenToArray(this, ternaryOperator, floatTernaryOperator);
	}
}
