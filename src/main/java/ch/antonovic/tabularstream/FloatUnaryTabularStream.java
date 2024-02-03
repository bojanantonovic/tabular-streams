package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.internal.tabular.floattabular.stream.FloatTabularStreamWithConcatenation;
import ch.antonovic.tabularstream.internal.tabular.floattabular.stream.FloatUnaryTabularStreamWithColumn;

public abstract class FloatUnaryTabularStream extends FloatTabularStream {
	protected FloatUnaryTabularStream() {
		super(1);
	}

	public static FloatUnaryTabularStream of(final float[] a) {
		return new FloatUnaryTabularStreamWithColumn(a);
	}

	public static FloatTabularStream concat(final FloatUnaryTabularStream a, final FloatUnaryTabularStream b) {
		return new FloatTabularStreamWithConcatenation(1, a, b);
	}
}
