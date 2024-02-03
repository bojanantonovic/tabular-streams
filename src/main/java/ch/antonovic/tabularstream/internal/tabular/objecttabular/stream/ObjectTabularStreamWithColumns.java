package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.RowsIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class ObjectTabularStreamWithColumns<T> extends ObjectTabularStream<T> {

	private static final Logger LOGGER = LogManager.getLogger(ObjectTabularStreamWithColumns.class);

	private final T[][] table;
	private final int numberOfRows;

	@SafeVarargs
	public ObjectTabularStreamWithColumns(final Class<T> type, final T[]... table) {
		super(table.length, type/* (Class<T>) table.getClass().componentType().componentType()*/);
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
	public ObjectTabularStreamIterator<T> iterator() {
		return new RowsIterator<>(table, type, numberOfColumns, numberOfRows);
	}
}
