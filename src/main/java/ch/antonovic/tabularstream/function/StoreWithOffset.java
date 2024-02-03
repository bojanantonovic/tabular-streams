package ch.antonovic.tabularstream.function;

// TODO find other package
@FunctionalInterface
public interface StoreWithOffset<S, T> {
	void store(S source, T target, int sourceOffset);
}
