package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.SingleRowIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public class SingleRowStream<T> extends ObjectTabularStream<T> {
	private final T[] row;

	public SingleRowStream(final T[] row) {
		super(row.length, (Class<T>) row.getClass().componentType());
		this.row = row;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new SingleRowIterator<>(row);
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
		return 1;
	}

	@Override
	public long count() {
		return 1;
	}
}
