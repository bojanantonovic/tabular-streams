package ch.antonovic.tabularstream.iterator;

public interface ObjectIterator<T> extends SingularStreamIterator {
	T next();
}
