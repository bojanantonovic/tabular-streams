package ch.antonovic.tabularstream.iterator;

import ch.antonovic.tabularstream.Resetable;

public interface GenericIterator extends Resetable {
	void incrementPositionWithoutReading();

	long numberOfDeliveredElements();
	boolean hasNext();

	//void prepareNextValue();
}
