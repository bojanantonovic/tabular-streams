module tabular.streams.test {
	requires jdk.incubator.vector;
	requires org.junit.jupiter.api;
	requires tabular.streams;
	requires spring.core;

	opens test.ch.antonovic.tabularstream to org.junit.platform.commons;
}
