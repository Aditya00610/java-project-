package com.example.expensewatch.service;

import com.example.expensewatch.dto.ExpenseDTO;
import com.example.expensewatch.dto.ReportDTO;
import com.example.expensewatch.entity.Expense;
import com.example.expensewatch.entity.User;
import com.example.expensewatch.exception.NotFoundException;
import com.example.expensewatch.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository repo;

    public ExpenseDTO createExpense(ExpenseDTO dto, User user) {
        Expense e = Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .expenseDate(dto.getExpenseDate())
                .note(dto.getNote())
                .user(user)
                .build();
        Expense saved = repo.save(e);
        dto.setId(saved.getId());
        return dto;
    }

    public ExpenseDTO updateExpense(Long id, ExpenseDTO dto, Long userId) {
        Expense e = repo.findById(id).orElseThrow(() -> new NotFoundException("Expense not found"));
        if (e.getUser() == null || !e.getUser().getId().equals(userId)) throw new NotFoundException("Expense not found for user");
        e.setTitle(dto.getTitle());
        e.setAmount(dto.getAmount());
        e.setCategory(dto.getCategory());
        e.setExpenseDate(dto.getExpenseDate());
        e.setNote(dto.getNote());
        repo.save(e);
        dto.setId(e.getId());
        return dto;
    }

    public void deleteExpense(Long id, Long userId) {
        Expense e = repo.findById(id).orElseThrow(() -> new NotFoundException("Expense not found"));
        if (e.getUser() == null || !e.getUser().getId().equals(userId)) throw new NotFoundException("Expense not found for user");
        repo.deleteById(id);
    }

    public ExpenseDTO getExpense(Long id, Long userId) {
        Expense e = repo.findById(id).orElseThrow(() -> new NotFoundException("Expense not found"));
        if (e.getUser() == null || !e.getUser().getId().equals(userId)) throw new NotFoundException("Expense not found for user");
        return mapToDto(e);
    }

    public List<ExpenseDTO> listExpenses(LocalDate start, LocalDate end, Long userId) {
        if (start == null) start = LocalDate.now().withDayOfMonth(1);
        if (end == null) end = LocalDate.now();
        return repo.findByExpenseDateBetweenAndUserId(start, end, userId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<ReportDTO> reportByCategory(LocalDate start, LocalDate end, Long userId) {
        List<Object[]> rows = repo.totalByCategoryBetween(start, end, userId);
        List<ReportDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            out.add(new ReportDTO((String) r[0], (BigDecimal) r[1]));
        }
        return out;
    }

    public List<ReportDTO> reportByMonth(LocalDate start, LocalDate end, Long userId) {
        List<Object[]> rows = repo.totalByMonthBetween(start, end, userId);
        List<ReportDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            out.add(new ReportDTO((String) r[0], (BigDecimal) r[1]));
        }
        return out;
    }

    public ByteArrayInputStream exportCsv(LocalDate start, LocalDate end, Long userId) {
        List<ExpenseDTO> items = listExpenses(start, end, userId);
        return com.example.expensewatch.util.CsvExporter.expensesToCSV(items);
    }

    private ExpenseDTO mapToDto(Expense e) {
        return ExpenseDTO.builder()
                .id(e.getId())
                .title(e.getTitle())
                .amount(e.getAmount())
                .category(e.getCategory())
                .expenseDate(e.getExpenseDate())
                .note(e.getNote())
                .build();
    }
}
