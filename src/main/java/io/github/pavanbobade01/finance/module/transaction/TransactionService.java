package io.github.pavanbobade01.finance.module.transaction;

import io.github.pavanbobade01.finance.exception.ResourceNotFoundException;
import io.github.pavanbobade01.finance.module.transaction.dto.TransactionRequest;
import io.github.pavanbobade01.finance.module.transaction.dto.TransactionResponse;
import io.github.pavanbobade01.finance.module.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    // ─────────────────────────────────────────
    // CREATE TRANSACTION
    // ─────────────────────────────────────────

    @Transactional
    public TransactionResponse create(TransactionRequest req, User user) {

        Transaction transaction = Transaction.builder()
                .amount(req.getAmount())
                .type(req.getType())
                .category(req.getCategory())
                .date(req.getDate())
                .notes(req.getNotes())
                .user(user)   // ✅ FIXED HERE (was createdBy)
                .deleted(false)
                .build();

        Transaction saved = transactionRepository.save(transaction);

        return TransactionResponse.from(saved);
    }

    // ─────────────────────────────────────────
    // GET ALL
    // ─────────────────────────────────────────

    public Page<TransactionResponse> getAll(
            TransactionType type,
            String category,
            LocalDate from,
            LocalDate to,
            Pageable pageable) {

        Page<Transaction> page;

        if (type != null) {
            page = transactionRepository.findByDeletedFalseAndType(type, pageable);
        } else if (category != null) {
            page = transactionRepository.findByDeletedFalseAndCategory(category, pageable);
        } else if (from != null && to != null) {
            page = transactionRepository.findByDeletedFalseAndDateBetween(from, to, pageable);
        } else {
            page = transactionRepository.findByDeletedFalse(pageable);
        }

        return page.map(TransactionResponse::from);
    }

    // ─────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────

    public TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        return TransactionResponse.from(transaction);
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────

    @Transactional
    public TransactionResponse update(String id, TransactionRequest req) {

        Transaction transaction = transactionRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        transaction.setAmount(req.getAmount());
        transaction.setType(req.getType());
        transaction.setCategory(req.getCategory());
        transaction.setDate(req.getDate());
        transaction.setNotes(req.getNotes());

        return TransactionResponse.from(transactionRepository.save(transaction));
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────

    @Transactional
    public void softDelete(String id) {

        Transaction transaction = transactionRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        transaction.setDeleted(true);

        transactionRepository.save(transaction);
    }
}