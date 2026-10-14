package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_report_null_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, "valid/path.txt"));
    }

    @Test
    void write_filePath_null_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("valid report",null));
    }

    @Test
    void write_filePath_isEmpty_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("valid report", ""));
    }

    @Test
    void write_directory_notExist_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("valid report","nonexistent/path.txt"));
    }

    @Test
    void write_validData_Ok() throws IOException {
        Path tempFile = Files.createTempFile("output", ".txt");
        fileWriter.write("test report", tempFile.toString());
        assertTrue(Files.exists(tempFile));
        assertEquals("test report", Files.readString(tempFile));

        Files.delete(tempFile);
    }
}
