package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private OperationStrategy buildStrategy() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        return new OperationStrategyImpl(handlers);
    }

    @Test
    void processTransactionsUpdatesStorageCorrectly() {
        OperationStrategy strategy = buildStrategy();
        ShopServiceImpl service = new ShopServiceImpl(strategy);

        List<FruitTransaction> tx = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 3),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1)
        );

        service.process(tx);
        Map<String, Integer> storage = service.getStorage();
        assertEquals(13, storage.get("banana"));
    }

    @Test
    void processThrowsWhenPurchaseLeadsNegative() {
        OperationStrategy strategy = buildStrategy();
        ShopServiceImpl service = new ShopServiceImpl(strategy);

        List<FruitTransaction> tx = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 2),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5)
        );

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.process(tx));
        assertTrue(ex.getMessage().toLowerCase().contains("negative"));
    }
}
