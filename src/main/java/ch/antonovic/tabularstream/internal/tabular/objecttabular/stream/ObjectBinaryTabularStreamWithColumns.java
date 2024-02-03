package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

public class ObjectBinaryTabularStreamWithColumns<T> extends ObjectTabularStreamWithColumns<T> {
	public ObjectBinaryTabularStreamWithColumns(final Class<T> type, final T[] a, final T[] b) {
		super(type, a, b);
	}
}
