package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private FruitTransaction fruitTransaction;
    private Storage storage;

    @BeforeEach
    void beforeEach() {
        purchaseOperation = new PurchaseOperation();
        fruitTransaction = new FruitTransaction();
        storage = new Storage();
    }

    @Test
    void handle_purchaseValidAmount_Ok() {
        storage.put("banana", 100);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        purchaseOperation.handle(fruitTransaction, storage);
        assertEquals(70, storage.getOrDefault("banana", 0));
    }

    @Test
    void handle_purchaseExactAmount_Ok() {
        storage.put("apple", 50);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(50);
        purchaseOperation.handle(fruitTransaction, storage);
        assertEquals(0, storage.getOrDefault("apple", 0));
    }

    @Test
    void handle_purchaseNotEnoughFruit_NotOk() {
        storage.put("banana", 10);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(50);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle(fruitTransaction, storage));
    }

    @Test
    void handle_purchaseFruitNotInStorage_NotOk() {
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle(fruitTransaction, storage));
    }
}
