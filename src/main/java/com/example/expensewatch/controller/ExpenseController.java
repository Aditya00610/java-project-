package com.example.expensewatch.controller;

import com.example.expensewatch.dto.ExpenseDTO;
import com.example.expensewatch.dto.ReportDTO;
import com.example.expensewatch.entity.User;
import com.example.expensewatch.service.ExpenseService;
import com.example.expensewatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService service;
    private final UserService userService;

    private User currentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(username).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> create(@Validated @RequestBody ExpenseDTO dto) {
        ExpenseDTO saved = service.createExpense(dto, currentUser());
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> update(@PathVariable Long id, @Validated @RequestBody ExpenseDTO dto) {
        ExpenseDTO updated = service.updateExpense(id, dto, currentUser().getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteExpense(id, currentUser().getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getExpense(id, currentUser().getId()));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok(service.listExpenses(start, end, currentUser().getId()));
    }

    @GetMapping("/report/category")
    public ResponseEntity<List<ReportDTO>> reportByCategory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok(service.reportByCategory(start, end, currentUser().getId()));
    }

    @GetMapping("/report/month")
    public ResponseEntity<List<ReportDTO>> reportByMonth(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok(service.reportByMonth(start, end, currentUser().getId()));
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportCsv(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) throws Exception {
        ByteArrayInputStream in = service.exportCsv(start, end, currentUser().getId());
        byte[] bytes = in.readAllBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }
}
