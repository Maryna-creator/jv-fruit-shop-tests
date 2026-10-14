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
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void get_nullOperation_NotOk() {
        OperationStrategy operationStrategy = getOperationStrategy();
        OperationHandler result = operationStrategy.get(null);
        assertNull(result);
    }

    @Test
    void get_unknownOperation_NotOk() {
        OperationStrategy operationStrategy = getOperationStrategy();
        OperationHandler result = operationStrategy.get(Operation.PURCHASE);
        assertNull(result);
    }

    @Test
    void get_validOperation_Ok() {
        OperationStrategy operationStrategy = getOperationStrategy();
        OperationHandler purchaseHandler = operationStrategy.get(Operation.BALANCE);
        OperationHandler result = operationStrategy.get(Operation.BALANCE);
        assertEquals(purchaseHandler, result);
    }

    private OperationStrategy getOperationStrategy() {
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperation());
        handlers.put(Operation.SUPPLY, new SupplyOperation());
        handlers.put(Operation.RETURN, new ReturnOperation());

        return new OperationStrategyImpl(handlers);
    }
}
