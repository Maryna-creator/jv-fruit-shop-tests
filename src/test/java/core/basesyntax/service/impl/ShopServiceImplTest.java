package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private final Storage storage = new Storage();

    @Test
    void process_emptyList_Ok() {
        ShopService shopService = createShopService();
        shopService.process(List.of());
        assertTrue(storage.getAll().isEmpty());

    }

    @Test
    void process_multipleTransactions_Ok() {
        ShopService shopService = createShopService();
        List<FruitTransaction> transactions = List.of(
                createTransaction(Operation.BALANCE, "banana", 100),
                createTransaction(Operation.PURCHASE, "banana", 30),
                createTransaction(Operation.SUPPLY, "apple", 50)
        );
        shopService.process(transactions);
        assertEquals(70, storage.getAll().get("banana"));
        assertEquals(50, storage.getAll().get("apple"));

    }

    @Test
    void process_nullList_throwsNpe() {
        ShopService shopService = createShopService();
        assertThrows(NullPointerException.class, () -> shopService.process(null));
    }

    private ShopService createShopService() {
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperation());
        handlers.put(Operation.PURCHASE, new PurchaseOperation());
        handlers.put(Operation.SUPPLY, new SupplyOperation());
        handlers.put(Operation.RETURN, new ReturnOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        return new ShopServiceImpl(operationStrategy, storage);
    }

    private FruitTransaction createTransaction(Operation operation, String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
