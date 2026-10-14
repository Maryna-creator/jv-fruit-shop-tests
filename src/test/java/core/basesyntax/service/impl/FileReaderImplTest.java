package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void reader_fileName_null_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(null));
    }

    @Test
    void reader_fileName_isEmpty_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(""));
    }

    @Test
    void reader_file_notExist_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("nonexistent.txt"));
    }

    @Test
    void reader_validFile_Ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, List.of("line1", "line2"));

        List<String> result = fileReader.read(tempFile.toString());

        assertEquals(2, result.size());
        assertEquals("line1", result.get(0));

        Files.delete(tempFile);
    }
}
