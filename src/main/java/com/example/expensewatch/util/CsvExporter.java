package com.example.expensewatch.util;

import com.example.expensewatch.dto.ExpenseDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvExporter {
    public static ByteArrayInputStream expensesToCSV(List<ExpenseDTO> expenses) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id","title","amount","category","expenseDate","note"))) {

            for (ExpenseDTO e : expenses) {
                csvPrinter.printRecord(
                        e.getId(),
                        e.getTitle(),
                        e.getAmount(),
                        e.getCategory(),
                        e.getExpenseDate(),
                        e.getNote()
                );
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to export CSV", ex);
        }
    }
}
