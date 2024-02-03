package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;

public abstract class DoubleTabularStreamWrapper extends DoubleTabularStream {
	protected final DoubleTabularStream parent;

	protected DoubleTabularStreamWrapper(final DoubleTabularStream parent) {
		super(parent.getNumberOfColumns());
		this.parent = parent;
	}

	@Override
	public boolean isInfinite() {
		return parent.isInfinite();
	}

	@Override
	public boolean isFiltered() {
		return parent.isFiltered();
	}

	@Override
	public int numberOfLayers() {
		return 1 + parent.numberOfLayers();
	}

	@Override
	public long estimatedGrossLength() {
		return parent.estimatedGrossLength();
	}
}
