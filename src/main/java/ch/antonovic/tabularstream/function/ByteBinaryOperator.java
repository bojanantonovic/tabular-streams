package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface ByteBinaryOperator {
	byte applyAsByte(byte a, byte b);
}
