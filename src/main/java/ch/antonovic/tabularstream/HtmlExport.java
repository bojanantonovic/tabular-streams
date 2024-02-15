package ch.antonovic.tabularstream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HtmlExport {

	private static final String HEADER = """
			<!DOCTYPE html>
			<html lang="en">
			<head>
			<meta charset="UTF-8">
			<title>TabularStream as HTML</title>
			</head>
			<body>""";
	private static final String FOOTER = """
			</body>
			</html>""";

	private HtmlExport() {

	}

	public static String toHtml(final FloatTabularStream stream) {
		final var countedLength = stream.count();
		if (countedLength.isEmpty() || countedLength.getAsLong() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		return "<table>" + tableHeader(stream) + tableBody(stream) + "</table>";
	}

	public static String toHtml(final DoubleTabularStream stream) {
		final var countedLength = stream.count();
		if (countedLength.isEmpty() || countedLength.getAsLong() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		return "<table>" + tableHeader(stream) + tableBody(stream) + "</table>";
	}

	public static String toHtml(final ObjectTabularStream<?> stream) {
		final var countedLength = stream.count();
		if (countedLength.isEmpty() || countedLength.getAsLong() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countedLength exceeds array limit in Java!");
		}
		return "<table>" + tableHeader(stream) + tableBody(stream) + "</table>";
	}

	private static String tableHeader(final TabularStream<?, ?> stream) {
		final var stringBuilder = new StringBuilder();
		stringBuilder.append("<tr>");
		for (var columnIndex = 0; columnIndex < stream.numberOfColumns; columnIndex++) {
			stringBuilder.append("<th>") //
					.append("Column ") //
					.append(columnIndex) //
					.append("</th>");
		}
		stringBuilder.append("</tr>");
		return stringBuilder.toString();
	}

	private static String tableBody(final FloatTabularStream stream) {
		final var stringBuilder = new StringBuilder();
		for (final var iterator = stream.iterator(); iterator.hasNext(); ) {
			final var value = iterator.next();
			final var valueAsRow = tableRow(value);
			stringBuilder.append(valueAsRow);
		}
		return stringBuilder.toString();
	}

	private static String tableBody(final DoubleTabularStream stream) {
		final var stringBuilder = new StringBuilder();
		for (final var iterator = stream.iterator(); iterator.hasNext(); ) {
			final var value = iterator.next();
			final var valueAsRow = tableRow(value);
			stringBuilder.append(valueAsRow);
		}
		return stringBuilder.toString();
	}

	private static String tableBody(final ObjectTabularStream<?> stream) {
		final var stringBuilder = new StringBuilder();
		for (final var iterator = stream.iterator(); iterator.hasNext(); ) {
			final var value = iterator.next();
			final var valueAsRow = tableRow(value);
			stringBuilder.append(valueAsRow);
		}
		return stringBuilder.toString();
	}

	private static String tableRow(final float[] value) {
		final var stringBuilder = new StringBuilder();
		stringBuilder.append("<tr>");
		for (final var v : value) {
			stringBuilder.append("<td>") //
					.append(v) //
					.append("</td>");
		}
		stringBuilder.append("</tr>");
		return stringBuilder.toString();
	}

	private static String tableRow(final double[] value) {
		final var stringBuilder = new StringBuilder();
		stringBuilder.append("<tr>");
		for (final var v : value) {
			stringBuilder.append("<td>") //
					.append(v) //
					.append("</td>");
		}
		stringBuilder.append("</tr>");
		return stringBuilder.toString();
	}

	private static String tableRow(final Object[] value) {
		final var stringBuilder = new StringBuilder();
		stringBuilder.append("<tr>");
		for (final var v : value) {
			stringBuilder.append("<td>") //
					.append(v) //
					.append("</td>");
		}
		stringBuilder.append("</tr>");
		return stringBuilder.toString();
	}

	public static void toHtmlFile(final Path path, final FloatTabularStream stream) throws IOException {
		final String string = HEADER + toHtml(stream) + FOOTER;
		Files.writeString(path, string);
	}

	public static void toHtmlFile(final Path path, final DoubleTabularStream stream) throws IOException {
		final String string = HEADER + toHtml(stream) + FOOTER;
		Files.writeString(path, string);
	}

	public static void toHtmlFile(final Path path, final ObjectTabularStream<?> stream) throws IOException {
		final String string = HEADER + toHtml(stream) + FOOTER;
		Files.writeString(path, string);
	}
}
