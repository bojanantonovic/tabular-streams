package ch.antonovic.tabularstream.iterator;

public interface DoubleTabularStreamIterator extends TabularStreamIterator<double[]> {

	double valueFromColumn(int index);
}
