package ch.antonovic.tabularstream.iterator;

public interface GenericIterator extends Resetable {

	void moveCursorToNextPosition();

	@Deprecated
	void incrementPositionWithoutReading();

	long numberOfDeliveredElements();
	boolean hasNext();

	//void prepareNextValue();
}
