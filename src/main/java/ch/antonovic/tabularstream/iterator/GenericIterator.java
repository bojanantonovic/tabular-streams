package ch.antonovic.tabularstream.iterator;

public interface GenericIterator extends Resetable {
	void incrementPositionWithoutReading();

	long numberOfDeliveredElements();
	boolean hasNext();

	//void prepareNextValue();
}
