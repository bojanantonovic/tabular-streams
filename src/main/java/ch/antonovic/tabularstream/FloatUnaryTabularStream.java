package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.internal.tabular.floattabular.stream.FloatUnaryTabularStreamWithColumn;

public abstract class FloatUnaryTabularStream extends FloatTabularStream {
	protected FloatUnaryTabularStream() {
		super(1);
	}

	public static FloatUnaryTabularStream of(final float[] a) {
		return new FloatUnaryTabularStreamWithColumn(a);
	}
}
