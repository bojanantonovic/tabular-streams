package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;

public abstract class FloatTabularStreamWrapper extends FloatTabularStream {
	protected final FloatTabularStream parent;

	protected FloatTabularStreamWrapper(final FloatTabularStream parent) {
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
