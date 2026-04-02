package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileReaderImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderTest {
    @TempDir
    private Path tempDir;

    @Test
    void read_whenFileExists_thenReturnLines() throws Exception {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "supply,apple,10\nbalance,banana,5");
        FileReaderImpl reader = new FileReaderImpl();

        List<String> lines = reader.read(file.toString());

        assertEquals(2, lines.size());
        assertTrue(lines.get(0).startsWith("supply"));
    }

    @Test
    void read_whenFileNotFound_thenThrows() {
        FileReaderImpl reader = new FileReaderImpl();
        assertThrows(RuntimeException.class, () -> reader.read("nonexistent-file.txt"));
    }
}
