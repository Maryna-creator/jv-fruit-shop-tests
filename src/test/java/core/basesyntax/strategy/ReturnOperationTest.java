package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitTransaction fruitTransaction;
    private Storage storage;
    private ReturnOperation returnOperation;

    @BeforeEach
    void beforeEach() {
        returnOperation = new ReturnOperation();
        fruitTransaction = new FruitTransaction();
        storage = new Storage();
    }

    @Test
    void handle_returnToExistingFruit_Ok() {
        storage.put("banana", 50);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        returnOperation.handle(fruitTransaction, storage);
        assertEquals(80, storage.getOrDefault("banana", 0));
    }

    @Test
    void handle_returnNewFruit_Ok() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        returnOperation.handle(fruitTransaction, storage);
        assertEquals(100, storage.getOrDefault("apple", 0));
    }

    @Test
    void handle_returnZeroQuantity_Ok() {
        storage.put("banana", 50);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(0);
        returnOperation.handle(fruitTransaction, storage);
        assertEquals(50, storage.getOrDefault("banana", 0));
    }
}
