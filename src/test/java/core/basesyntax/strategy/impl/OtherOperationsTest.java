package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OtherOperationsTest {
    private static BalanceOperation balanceOperation;
    private static SupplyOperation supplyOperation;
    private static ReturnOperation returnOperation;

    private Map<String, Integer> storage;

    @BeforeAll
    static void initOperations() {
        balanceOperation = new BalanceOperation();
        supplyOperation = new SupplyOperation();
        return_operation_init:
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
    void balanceSetsQuantity() {
        storage.put("apple", 5);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(fruitTransaction, storage);
        assertEquals(100, storage.get("apple"));
    }

    @Test
    void supplyAddsQuantity() {
        storage.put("apple", 5);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);
        supplyOperation.handle(fruitTransaction, storage);
        assertEquals(15, storage.get("apple"));
    }

    @Test
    void returnAddsQuantity() {
        storage.put("apple", 3);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 2);
        returnOperation.handle(fruitTransaction, storage);
        assertEquals(5, storage.get("apple"));
    }
}
