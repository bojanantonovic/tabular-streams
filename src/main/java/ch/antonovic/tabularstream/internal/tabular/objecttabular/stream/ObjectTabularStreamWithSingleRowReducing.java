package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.ObjectUnaryTabularStream;

public abstract class ObjectTabularStreamWithSingleRowReducing<T> extends ObjectUnaryTabularStream<T> {

	protected final ObjectTabularStream<T> sourceStream;

	protected ObjectTabularStreamWithSingleRowReducing(final ObjectTabularStream<T> sourceStream) {
		super(sourceStream.getType());
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
