package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balance;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        balance = new BalanceOperation();
        storage = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_whenBalanceExistingFruit_thenReplacesQuantity() {
        storage.put("apple", 5);
        FruitTransaction t = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);

        balance.handle(t, storage);

        assertEquals(100, storage.get("apple"));
    }

    @Test
    void handle_whenBalanceNewFruit_thenAddsEntry() {
        FruitTransaction t = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50);

        balance.handle(t, storage);

        assertEquals(50, storage.get("banana"));
    }

    @Test
    void handle_whenTransactionNull_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> balance.handle(null, storage));
    }

    @Test
    void handle_whenStorageNull_thenThrowsNullPointerException() {
        FruitTransaction t = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10);
        assertThrows(NullPointerException.class, () -> balance.handle(t, null));
    }
}
