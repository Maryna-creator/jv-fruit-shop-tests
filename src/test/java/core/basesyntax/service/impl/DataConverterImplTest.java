package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;
    private Storage storage;
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
        storage = new Storage();
        fileReader = new FileReaderImpl();
    }

    @AfterEach
    public void afterEachTest() {
        storage.getAll().clear();
    }

    @Test
    void convert_null_NotOk() {
        assertThrows(IllegalArgumentException.class, 
                () -> dataConverter.convert(null));
    }

    @Test
    void convert_emptyList_NotOk() {
        String filePath = "src/test/resourсes/empty_test.csv";
        List<String> lines = fileReader.read(filePath);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convert(lines));
    }

    @Test
    void convert_validData_Ok() {
        String filePath = "src/test/resourсes/testInput.csv";
        List<String> lines = fileReader.read(filePath);
        List<FruitTransaction> result = dataConverter.convert(lines);
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
        assertEquals(Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(100, result.get(1).getQuantity());
        assertEquals(Operation.BALANCE, result.get(1).getOperation());
    }

    @Test
    void convert_invalidOperation_NotOk() {
        String filePath = "src/test/resourсes/testInvalid_Operation.csv";
        List<String> lines = fileReader.read(filePath);
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(lines));
    }

    @Test
    void convert_invalidQuantity_NotOk() {
        String filePath = "src/test/resourсes/testInvalid_Quantity.csv";
        List<String> lines = fileReader.read(filePath);
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(lines));
    }
}
