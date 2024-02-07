package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.HtmlExport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

class HtmlExportTest {

	@Test
	void toHtmlFile_FloatTabularStream() throws IOException {
		// arrange
		final var stream = FloatTabularStream.of(FloatTabularStreamTest.a, FloatTabularStreamTest.b);
		// act
		HtmlExport.toHtmlFile(Path.of("target", "toHtmlFile_FloatTabularStream.html"), stream);
	}
}
