package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convert_whenValidLines_thenReturnTransactions() {
        List<String> lines = Arrays.asList("supply,apple,10", "balance,banana,5");
        List<FruitTransaction> result = converter.convertToTransaction(lines);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
    }

    @Test
    void convert_whenInvalidLine_thenThrowsException() {
        List<String> lines = Arrays.asList("bad,line");
        assertThrows(RuntimeException.class, () -> converter.convertToTransaction(lines));
    }
}
