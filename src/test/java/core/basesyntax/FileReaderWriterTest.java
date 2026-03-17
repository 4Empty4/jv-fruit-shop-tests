package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderWriterTest {
    @Test
    void fileReaderReadsAndFileWriterWrites() throws Exception {
        Path temp = Files.createTempFile("fruitshop-test-", ".csv");
        temp.toFile().deleteOnExit();
        List<String> lines = Arrays.asList("type,fruit,quantity", "b,banana,10");
        Files.write(temp, lines, StandardCharsets.UTF_8);

        FileReaderImpl reader = new FileReaderImpl();
        List<String> read = reader.read(temp.toString());
        assertEquals(lines.size(), read.size());
        assertEquals("b,banana,10", read.get(1));

        Path out = Files.createTempFile("fruitshop-out-", ".csv");
        out.toFile().deleteOnExit();
        FileWriterImpl writer = new FileWriterImpl();
        String content = "fruit,quantity\nbanana,10\n";
        writer.write(content, out.toString());
        String readOut = new String(Files.readAllBytes(out), StandardCharsets.UTF_8);
        assertEquals(content, readOut);
    }
}
