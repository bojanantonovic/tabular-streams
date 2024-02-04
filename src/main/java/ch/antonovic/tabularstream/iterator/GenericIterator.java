package ch.antonovic.tabularstream.iterator;

public interface GenericIterator extends Resetable {

	void moveCursorToNextPosition();

	long numberOfDeliveredElements();
	boolean hasNext();
}
