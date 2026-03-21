package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverterImpl converter;

    @BeforeAll
    static void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convertValidLinesShouldReturnTransactions() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,10",
                "p,banana,5",
                "r,apple,2"
        );

        List<FruitTransaction> transactions = converter.convertToTransaction(lines);
        assertEquals(4, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
    }

    @Test
    void convertInvalidColumnsShouldThrow() {
        List<String> lines = Arrays.asList("b,banana");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines)
        );
        assertTrue(exception.getMessage().toLowerCase().contains("expect 3"));
    }

    @Test
    void convertNegativeQuantityShouldThrow() {
        List<String> lines = Arrays.asList("p,banana,-5");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines)
        );
        assertTrue(exception.getMessage().toLowerCase().contains("non-negative"));
    }

    @Test
    void convertUnknownOperationShouldThrow() {
        List<String> lines = Arrays.asList("x,banana,5");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines)
        );
        assertTrue(exception.getMessage().toLowerCase().contains("unknown operation"));
    }
}
