package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatUnaryTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.RowsIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FloatUnaryTabularStreamWithColumn extends FloatUnaryTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(FloatUnaryTabularStreamWithColumn.class);

	private final float[] column;

	public FloatUnaryTabularStreamWithColumn(final float[] column) {
		this.column = column;
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
	public FloatTabularStreamIterator iterator() {
		return new RowsIterator(new float[][] {column}, column.length);
	}
}
