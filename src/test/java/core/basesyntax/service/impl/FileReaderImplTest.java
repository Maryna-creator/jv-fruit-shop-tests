package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void beforeEach() {
        fileReader = new FileReaderImpl();
    }

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
                () -> fileReader.read("nonexistent.csv"));
    }

    @Test
    void reader_onlyHeader_Ok() {
        String filePath = "src/test/resourсes/testTitle.csv";
        List<String> lines = fileReader.read(filePath);
        assertEquals(1, lines.size());
    }

    @Test
    void reader_emptyFile_Ok() {
        String filePath = "src/test/resourсes/empty_test.csv";
        List<String> lines = fileReader.read(filePath);
        assertTrue(lines.isEmpty());
    }

    @Test
    void reader_validFile_Ok() {
        String filePath = "src/test/resourсes/testInput.csv";
        List<String> lines = fileReader.read(filePath);
        assertEquals(9, lines.size());
        assertEquals("type,fruit,quantity", lines.get(0));
    }
}
