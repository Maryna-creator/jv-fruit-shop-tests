package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void beforeEach() {
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperation());
        handlers.put(Operation.SUPPLY, new SupplyOperation());
        handlers.put(Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_nullOperation_NotOk() {
        OperationHandler result = operationStrategy.get(null);
        assertNull(result);
    }

    @Test
    void get_unknownOperation_NotOk() {
        OperationHandler result = operationStrategy.get(Operation.PURCHASE);
        assertNull(result);
    }

    @Test
    void get_validOperation_Ok() {
        OperationHandler purchaseHandler = operationStrategy.get(Operation.BALANCE);
        OperationHandler result = operationStrategy.get(Operation.BALANCE);
        assertEquals(purchaseHandler, result);
    }
}
