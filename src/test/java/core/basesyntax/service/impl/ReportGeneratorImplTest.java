package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private Storage storage;

    @BeforeEach
    void beforeEach() {
        reportGenerator = new ReportGeneratorImpl();
        storage = new Storage();
    }

    @Test
    void getReport_storage_IsEmpty_Ok() {
        assertTrue(storage.getAll().isEmpty());
        String report = reportGenerator.getReport(storage);
        assertEquals("fruit,quantity\n", report);
    }

    @Test
    void getReport_withData_Ok() {
        storage.put("banana", 100);
        storage.put("apple", 50);
        String report = reportGenerator.getReport(storage);
        assertTrue(report.contains("banana,100"));
        assertTrue(report.contains("apple,50"));
        assertTrue(report.startsWith("fruit,quantity\n"));
    }

    @Test
    void getReport_null_throwsException() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.getReport(null));
    }
}
