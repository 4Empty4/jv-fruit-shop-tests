package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void purchaseReducesStorage() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        purchaseOperation.handle(transaction, storage);

        assertEquals(5, storage.get("banana"));
    }

    @Test
    void purchaseThatMakesZeroBalanceShouldPass() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        purchaseOperation.handle(transaction, storage);

        assertEquals(0, storage.get("banana"));
    }

    @Test
    void purchaseThatMakesNegativeShouldThrow() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 2);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> purchaseOperation.handle(transaction, storage)
        );

        assertTrue(exception.getMessage().toLowerCase().contains("negative"));
    }

    @Test
    void handleNullTransactionShouldThrow() {
        Map<String, Integer> storage = new HashMap<>();

        assertThrows(NullPointerException.class,
                () -> purchaseOperation.handle(null, storage));
    }

    @Test
    void handleNullStorageShouldThrow() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        assertThrows(NullPointerException.class,
                () -> purchaseOperation.handle(transaction, null));
    }
}
