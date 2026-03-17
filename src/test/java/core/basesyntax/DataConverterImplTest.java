package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverterImpl converter = new DataConverterImpl();

    @Test
    void convertValidLinesShouldReturnTransactions() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,10",
                "p,banana,5",
                "r,apple,2"
        );
        List<FruitTransaction> tx = converter.convertToTransaction(lines);
        assertEquals(4, tx.size());
        assertEquals(FruitTransaction.Operation.BALANCE, tx.get(0).getOperation());
        assertEquals("banana", tx.get(0).getFruit());
        assertEquals(20, tx.get(0).getQuantity());
    }

    @Test
    void convertInvalidColumnsShouldThrow() {
        List<String> lines = Arrays.asList("b,banana");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(ex.getMessage().toLowerCase().contains("expect 3"));
    }

    @Test
    void convertNegativeQuantityShouldThrow() {
        List<String> lines = Arrays.asList("p,banana,-5");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(ex.getMessage().toLowerCase().contains("non-negative"));
    }

    @Test
    void convertUnknownOperationShouldThrow() {
        List<String> lines = Arrays.asList("x,banana,5");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines));
        assertTrue(ex.getMessage().toLowerCase().contains("unknown operation"));
    }
}
