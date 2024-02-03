package ch.antonovic.tabularstream;

public abstract class ObjectUnaryTabularStream<T> extends ObjectTabularStream<T> {
	protected ObjectUnaryTabularStream(final Class<T> type) {
		super(1, type);
	}
}
