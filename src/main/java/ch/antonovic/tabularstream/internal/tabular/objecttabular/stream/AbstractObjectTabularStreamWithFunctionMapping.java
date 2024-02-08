package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

public abstract class AbstractObjectTabularStreamWithFunctionMapping<U, T> extends ObjectTabularStream<T> {

	protected final TabularStream<U, ? extends TabularStreamIterator<U>> sourceStream;

	protected AbstractObjectTabularStreamWithFunctionMapping(final TabularStream<U, ? extends TabularStreamIterator<U>> sourceStream, final Class<T> type) {
		super(sourceStream.getNumberOfColumns(), type);
		this.sourceStream = sourceStream;
	}

	@Override
	public boolean isInfinite() {
		return sourceStream.isInfinite();
	}

	@Override
	public boolean isFiltered() {
		return sourceStream.isFiltered();
	}

	@Override
	public int numberOfLayers() {
		return 1 + sourceStream.numberOfLayers();
	}

	@Override
	public long estimatedGrossLength() {
		return sourceStream.estimatedGrossLength();
	}
}
