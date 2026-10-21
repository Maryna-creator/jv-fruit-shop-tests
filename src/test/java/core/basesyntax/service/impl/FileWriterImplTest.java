package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter fileWriter;
    private FileReader fileReader;

    @BeforeEach
    void beforeEach() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    void write_report_null_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, "src/test/resources/testInput"));
    }

    @Test
    void write_filePath_null_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("testInput.csv",null));
    }

    @Test
    void write_filePath_isEmpty_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("testInput.csv", ""));
    }

    @Test
    void write_directory_notExist_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("testInput.csv","nonexistent/path.csv"));
    }

    @Test
    void write_validData_Ok() throws IOException {
        String resourcePath = "src/test/resourсes/testInput.csv";
        List<String> lines = fileReader.read(resourcePath);
        String content = String.join("\n", lines);
        Path tempFile = Files.createTempFile("output", ".csv");
        fileWriter.write(content, tempFile.toString());
        assertTrue(Files.exists(tempFile));
        assertEquals(content, Files.readString(tempFile));
        Files.delete(tempFile);
    }
}
