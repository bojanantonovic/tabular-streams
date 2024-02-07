package ch.antonovic.tabularstream.iterator;

public interface GenericIterator extends Resetable {

	void moveCursorToNextPosition();
	void moveCursorToNextPosition(long stepWidth);

	long numberOfDeliveredElements();
	
	boolean hasNext();
	boolean hasNext(long stepWidth);
}
