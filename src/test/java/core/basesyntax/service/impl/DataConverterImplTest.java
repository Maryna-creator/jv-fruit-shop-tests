package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();
    private final Storage storage = new Storage();

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
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convert(null));
    }

    @Test
    void convert_validData_Ok() {
        List<String> validInput = List.of(
                "type,fruit,quantity",
                "p,banana,100",
                "s,apple,50"
        );
        List<FruitTransaction> result = dataConverter.convert(validInput);
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
        assertEquals(Operation.PURCHASE, result.get(0).getOperation());
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(50, result.get(1).getQuantity());
        assertEquals(Operation.SUPPLY, result.get(1).getOperation());
    }

    @Test
    void convert_invalidOperation_NotOk() {
        List<String> invalidInput = List.of(
                "type,fruit,quantity",
                "x,banana,100"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(invalidInput));
    }

    @Test
    void convert_invalidQuantity_NotOk() {
        List<String> invalidInput = List.of(
                "type,fruit,quantity",
                "b,banana,one"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(invalidInput));
    }
}
