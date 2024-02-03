package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

public class ObjectUnaryTabularStreamWithColumns<T> extends ObjectTabularStreamWithColumns<T> {
	public ObjectUnaryTabularStreamWithColumns(final T[] a) {
		super((Class<T>) a.getClass().componentType(), a);
	}
}
