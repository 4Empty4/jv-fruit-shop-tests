package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static BalanceOperation balanceOperation;
    private Map<String, Integer> storage;

    @BeforeAll
    static void initOperation() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    void setUpStorage() {
        storage = new HashMap<>();
    }

    @AfterEach
    void clearStorage() {
        if (storage != null) {
            storage.clear();
        }
    }

    @Test
    void balanceSetsQuantity() {
        storage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(transaction, storage);
        assertEquals(100, storage.get("apple"));
    }

    @Test
    void handleWithNullTransactionShouldThrow() {
        assertThrows(NullPointerException.class, () -> balanceOperation.handle(null, storage));
    }

    @Test
    void handleWithNullStorageShouldThrow() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        assertThrows(NullPointerException.class, () -> balanceOperation.handle(transaction, null));
    }
}
