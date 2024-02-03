package ch.antonovic.tabularstream.function;

// TODO find other package
@FunctionalInterface
public interface LoaderWithOffset<S, T> {
	T load(S source, int offset);
}
