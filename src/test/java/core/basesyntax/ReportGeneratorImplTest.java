package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    void getReportProducesSortedCsv() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);
        ShopServiceImpl shopService = new ShopServiceImpl(strategy);

        shopService.process(Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 2)
        ));

        ReportGeneratorImpl generator = new ReportGeneratorImpl(shopService);
        String report = generator.getReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,2" + System.lineSeparator()
                + "banana,5" + System.lineSeparator();
        assertEquals(expected, report);
    }
}
