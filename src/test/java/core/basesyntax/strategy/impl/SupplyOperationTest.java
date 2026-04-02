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

class SupplyOperationTest {
    private static SupplyOperation supplyOperation;
    private Map<String, Integer> storage;

    @BeforeAll
    static void initOperation() {
        supplyOperation = new SupplyOperation();
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
    void supplyAddsQuantity() {
        storage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);
        supplyOperation.handle(transaction, storage);
        assertEquals(15, storage.get("apple"));
    }

    @Test
    void handleWithNullTransactionShouldThrow() {
        assertThrows(NullPointerException.class, () -> supplyOperation.handle(null, storage));
    }

    @Test
    void handleWithNullStorageShouldThrow() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 5);
        assertThrows(NullPointerException.class, () -> supplyOperation.handle(transaction, null));
    }
}
