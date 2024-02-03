package ch.antonovic.tabularstream.function;

@FunctionalInterface
public interface IntToByteFunction {

	byte applyAsByte(int value);
}
