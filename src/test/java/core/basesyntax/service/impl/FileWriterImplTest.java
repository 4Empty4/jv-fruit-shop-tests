package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriterImpl writer;

    @BeforeAll
    static void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void writeCreatesFileWithGivenContent() throws Exception {
        Path out = Files.createTempFile("fruitshop-writer-", ".csv");
        out.toFile().deleteOnExit();
        String content = "fruit,quantity\nbanana,10\n";

        writer.write(content, out.toString());

        String readOut = new String(Files.readAllBytes(out), StandardCharsets.UTF_8);
        assertEquals(content, readOut);
    }

    @Test
    void writeNullContentShouldThrow() throws Exception {
        Path out = Files.createTempFile("fruitshop-writer-", ".csv");
        out.toFile().deleteOnExit();
        assertThrows(NullPointerException.class, () -> writer.write(null, out.toString()));
    }

    @Test
    void writeNullFileNameShouldThrow() {
        String content = "fruit,quantity\nbanana,10\n";
        assertThrows(NullPointerException.class, () -> writer.write(content, null));
    }
}
