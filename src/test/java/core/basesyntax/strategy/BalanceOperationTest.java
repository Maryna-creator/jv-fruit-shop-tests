package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransaction fruitTransaction;
    private BalanceOperation balanceOperation;
    private Storage storage;

    @BeforeEach
    void beforeEach() {
        fruitTransaction = new FruitTransaction();
        balanceOperation = new BalanceOperation();
        storage = new Storage();
    }

    @AfterEach
    void afterEach() {
        storage.getAll().clear();
    }

    @Test
    void handle_balanceNewFruit_Ok() {
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        balanceOperation.handle(fruitTransaction, storage);
        assertEquals(100, storage.getOrDefault("banana", 0));
    }

    @Test
    void handle_updatingExistingFruit_Ok() {
        storage.put("apple", 50);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(30);
        balanceOperation.handle(fruitTransaction, storage);
        assertEquals(30, storage.getOrDefault("apple", 0));
    }

    @Test
    void handle_multiplyFruits_Ok() {
        FruitTransaction banana = new FruitTransaction();
        banana.setFruit("banana");
        banana.setQuantity(100);
        FruitTransaction apple = new FruitTransaction();
        apple.setFruit("apple");
        apple.setQuantity(50);
        balanceOperation.handle(banana, storage);
        balanceOperation.handle(apple, storage);
        assertEquals(100, storage.getAll().get("banana"));
        assertEquals(50, storage.getAll().get("apple"));
    }
}
