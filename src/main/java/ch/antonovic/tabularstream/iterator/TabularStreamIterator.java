package ch.antonovic.tabularstream.iterator;

import java.util.Iterator;

public interface TabularStreamIterator<R> extends Iterator<R>, GenericIterator {

	R current();
	//	<T> T mapRow(Function<R, T> rowMapper);
}
