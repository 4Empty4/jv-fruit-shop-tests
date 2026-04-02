package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileWriterImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterTest {
    @TempDir
    private Path tempDir;

    @Test
    void write_whenValid_thenFileContainsContent() throws Exception {
        Path out = tempDir.resolve("out.txt");
        FileWriterImpl writer = new FileWriterImpl();

        writer.write("apple,10", out.toString());

        String content = Files.readString(out);
        assertEquals("apple,10", content);
    }
}
