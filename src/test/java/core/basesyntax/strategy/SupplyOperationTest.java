package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private FruitTransaction fruitTransaction;
    private Storage storage;

    @BeforeEach
    void beforeEach() {
        supplyOperation = new SupplyOperation();
        fruitTransaction = new FruitTransaction();
        storage = new Storage();
    }

    @Test
    void handle_supplyToExistingFruit_Ok() {
        storage.put("banana", 50);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        supplyOperation.handle(fruitTransaction, storage);
        assertEquals(80, storage.getOrDefault("banana", 0));
    }

    @Test
    void handle_supplyToNewFruit_Ok() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        supplyOperation.handle(fruitTransaction, storage);
        assertEquals(100, storage.getOrDefault("apple", 0));
    }

    @Test
    void handle_supplyZeroQuantity_Ok() {
        storage.put("banana", 50);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(0);
        supplyOperation.handle(fruitTransaction, storage);
        assertEquals(50, storage.getOrDefault("banana", 0));
    }
}
