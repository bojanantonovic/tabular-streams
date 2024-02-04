package ch.antonovic.tabularstream.iterator;

public interface ObjectTabularStreamIterator<T> extends TabularStreamIterator<T[]> {

	T valueFromColumn(int index);

	void next(T[] target);
}
