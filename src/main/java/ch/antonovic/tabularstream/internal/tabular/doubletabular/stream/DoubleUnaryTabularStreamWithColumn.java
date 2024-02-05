package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleUnaryTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RowsIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoubleUnaryTabularStreamWithColumn extends DoubleUnaryTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(DoubleUnaryTabularStreamWithColumn.class);

	private final double[] column;

	public DoubleUnaryTabularStreamWithColumn(final double[] column) {
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
	public DoubleTabularStreamIterator iterator() {
		return new RowsIterator(new double[][] {column}, column.length);
	}
}
