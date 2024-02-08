package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.FromObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.Function;

public class ObjectTabularStreamWithFunctionMapping<U, T> extends ObjectTabularStream<T> {

	protected final ObjectTabularStream<U> sourceStream;

	private final Function<U, T> function;

	public ObjectTabularStreamWithFunctionMapping(final ObjectTabularStream<U> sourceStream, final Function<U, T> function, final Class<T> type) {
		super(sourceStream.getNumberOfColumns(), type);
		this.sourceStream = sourceStream;
		this.function = function;
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

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new FromObjectTabularStreamIterator<>(sourceStream.iterator(), function, type);
	}
}
