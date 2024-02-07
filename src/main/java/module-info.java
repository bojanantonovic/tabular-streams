module tabular.streams {
	exports ch.antonovic.tabularstream;
	exports ch.antonovic.tabularstream.function;
	exports ch.antonovic.tabularstream.iterator;

	requires org.apache.logging.log4j;
	requires spring.core;
	requires jdk.incubator.vector;
}
