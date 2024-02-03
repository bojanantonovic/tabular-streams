package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.FloatUnaryTabularStream;

public abstract class FloatTabularStreamWithSingleRowReducing extends FloatUnaryTabularStream {

	protected final FloatTabularStream sourceStream;

	protected FloatTabularStreamWithSingleRowReducing(final FloatTabularStream sourceStream) {
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
