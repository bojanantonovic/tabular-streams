package ch.antonovic.tabularstream.iterator;

public interface GenericIterator extends Resetable {

	void moveCursorToNextPosition();
	void moveCursorToNextPosition(long stepWidth);

	/**
	 * @param amount
	 * @return number of skipped elements. Can deviate of a stream is concatenated.
	 */
	int skip(int amount);

	long numberOfDeliveredElements();

	boolean hasNext();
	boolean hasNext(long stepWidth);
}
