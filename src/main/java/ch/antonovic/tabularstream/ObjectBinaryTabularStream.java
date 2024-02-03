package ch.antonovic.tabularstream;

public abstract class ObjectBinaryTabularStream<T> extends ObjectTabularStream<T> {
	protected ObjectBinaryTabularStream(final Class<T> type) {
		super(2, type);
	}
}
