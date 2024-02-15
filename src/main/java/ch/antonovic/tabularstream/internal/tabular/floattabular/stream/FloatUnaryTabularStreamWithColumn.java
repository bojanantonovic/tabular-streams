package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatUnaryTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.RowsIterator;
import jdk.incubator.vector.FloatVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalLong;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class FloatUnaryTabularStreamWithColumn extends FloatUnaryTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(FloatUnaryTabularStreamWithColumn.class);

	private final float[] column;

	public FloatUnaryTabularStreamWithColumn(final float[] column) {
		this.column = column;
	}

	@Override
	public OptionalLong count() {
		return OptionalLong.of(column.length);
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
		return column.length;
	}

	@Override
	public RowsIterator iterator() {
		return new RowsIterator(new float[][] {column}, column.length);
	}

	@Override
	public float[] fusedMapUnaryAndThenToArray(final UnaryOperator<FloatVector> unaryOperator, final FloatUnaryOperator floatUnaryOperator) {
		return SimdHelper.fusedMapUnaryAndThenToArray(this, unaryOperator, floatUnaryOperator);
	}

	@Override
	public float[] fusedMapBinaryAndThenToArray(final BinaryOperator<FloatVector> binaryOperator, final FloatBinaryOperator floatBinaryOperator) {
		checkRequiredArity(this, 2);
		return new float[0]; // dummy value
	}

	@Override
	public float[] fusedMapTernaryAndThenToArray(final TernaryOperator<FloatVector> ternaryOperator, final FloatTernaryOperator floatTernaryOperator) {
		checkRequiredArity(this, 3);
		return new float[0]; // dummy value
	}
}
