package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectUnaryTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.RowsIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;

public class ObjectUnaryTabularStreamWithColumn<T> extends ObjectUnaryTabularStream<T> {

	private static final Logger LOGGER = LogManager.getLogger(ObjectUnaryTabularStreamWithColumn.class);

	private final T[] column;
	private final T[][] table;

	public ObjectUnaryTabularStreamWithColumn(final Class<T> type, final T[] column) {
		super(type);
		this.column = column;
		this.table = (T[][]) Array.newInstance(type.arrayType(), 1);
		table[0] = column;
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
	public ObjectTabularStreamIterator<T> iterator() {
		return new RowsIterator<>(table, type, column.length);
	}
}
