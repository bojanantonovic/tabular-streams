package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.DoubleUnaryTabularStream;

public abstract class DoubleTabularStreamWithSingleRowReducing extends DoubleUnaryTabularStream {

	protected final DoubleTabularStream sourceStream;

	protected DoubleTabularStreamWithSingleRowReducing(final DoubleTabularStream sourceStream) {
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
