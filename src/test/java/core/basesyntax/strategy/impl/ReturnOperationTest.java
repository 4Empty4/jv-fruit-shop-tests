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

class ReturnOperationTest {
    private static ReturnOperation returnOperation;
    private Map<String, Integer> storage;

    @BeforeAll
    static void initOperation() {
        returnOperation = new ReturnOperation();
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
    void returnAddsQuantity() {
        storage.put("apple", 3);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 2);
        returnOperation.handle(transaction, storage);
        assertEquals(5, storage.get("apple"));
    }

    @Test
    void handleWithNullTransactionShouldThrow() {
        assertThrows(NullPointerException.class, () -> returnOperation.handle(null, storage));
    }

    @Test
    void handleWithNullStorageShouldThrow() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 2);
        assertThrows(NullPointerException.class, () -> returnOperation.handle(transaction, null));
    }
}
