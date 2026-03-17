package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OtherOperationsTest {
    private final BalanceOperation balance = new BalanceOperation();
    private final SupplyOperation supply = new SupplyOperation();
    private final ReturnOperation ret = new ReturnOperation();

    @Test
    void balanceSetsQuantity() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 5);
        FruitTransaction t = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        balance.handle(t, storage);
        assertEquals(100, storage.get("apple"));
    }

    @Test
    void supplyAddsQuantity() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 5);
        FruitTransaction t = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        supply.handle(t, storage);
        assertEquals(15, storage.get("apple"));
    }

    @Test
    void returnAddsQuantity() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 3);
        FruitTransaction t = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 2);
        ret.handle(t, storage);
        assertEquals(5, storage.get("apple"));
    }
}
