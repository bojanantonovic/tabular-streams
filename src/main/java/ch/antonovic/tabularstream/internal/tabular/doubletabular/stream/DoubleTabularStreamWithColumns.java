package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RowsIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class DoubleTabularStreamWithColumns extends DoubleTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(DoubleTabularStreamWithColumns.class);

	private final double[][] table;
	private final int numberOfRows;

	public DoubleTabularStreamWithColumns(final double[]... table) {
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
	public DoubleTabularStreamIterator iterator() {
		return new RowsIterator(table, numberOfColumns, numberOfRows);
	}
}
