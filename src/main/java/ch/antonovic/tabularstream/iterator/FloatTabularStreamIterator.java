package ch.antonovic.tabularstream.iterator;

public interface FloatTabularStreamIterator extends TabularStreamIterator<float[]> {
	float valueFromColumn(int index);
}
