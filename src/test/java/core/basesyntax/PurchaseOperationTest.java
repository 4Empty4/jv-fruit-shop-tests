package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.PurchaseOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation operation = new PurchaseOperation();

    @Test
    void purchaseReducesStorage() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 10);
        FruitTransaction t =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
        operation.handle(t, storage);
        assertEquals(5, storage.get("banana"));
    }

    @Test
    void purchaseThatMakesNegativeShouldThrow() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 2);
        FruitTransaction t =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> operation.handle(t, storage)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("negative"));
    }
}
