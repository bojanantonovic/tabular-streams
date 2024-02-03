package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;

public class FloatArrayMapper {
	private FloatArrayMapper() {

	}

	public static float[] mapColumnsUnary(final float[] row, final FloatUnaryOperator operator) {
		final float[] result = new float[row.length];
		for (var i = 0; i < row.length; i++) {
			result[i] = operator.applyAsFloat(row[i]);
		}
		return result;
	}
}
