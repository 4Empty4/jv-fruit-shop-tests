package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReaderImpl reader;

    @BeforeAll
    static void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readExistingFileReturnsAllLines() throws Exception {
        Path temp = Files.createTempFile("fruitshop-reader-", ".csv");
        temp.toFile().deleteOnExit();
        List<String> lines = Arrays.asList("type,fruit,quantity", "b,banana,10");
        Files.write(temp, lines, StandardCharsets.UTF_8);

        List<String> read = reader.read(temp.toString());

        assertEquals(lines.size(), read.size());
        assertEquals("b,banana,10", read.get(1));
    }

    @Test
    void readWithNullOrEmptyFileNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> reader.read(null));
        assertThrows(IllegalArgumentException.class, () -> reader.read("   "));
    }

    @Test
    void readNonexistentFileShouldThrowRuntimeException() {
        String nonExistent = "this-file-should-not-exist-" + System.nanoTime() + ".csv";
        assertThrows(RuntimeException.class, () -> reader.read(nonExistent));
    }
}
