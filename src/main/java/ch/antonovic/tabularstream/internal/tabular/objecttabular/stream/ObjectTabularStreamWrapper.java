package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;

public abstract class ObjectTabularStreamWrapper<T> extends ObjectTabularStream<T> {
	protected final ObjectTabularStream<T> parent;

	protected ObjectTabularStreamWrapper(final ObjectTabularStream<T> parent) {
		super(parent.getNumberOfColumns(), parent.getType());
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
