package library.service;

import library.model.Loan;
import library.model.FineRecord;
import library.model.enums.FineStatus;
import library.repository.FineRecordRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.util.UUID;

public class FineService {
    private static final BigDecimal FINE_PER_DAY = BigDecimal.valueOf(1.0); // $1 per day
    private final FineRecordRepository fineRepository;

    public FineService(FineRecordRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public FineRecord calculateAndCreateFine(Loan loan) {
        if (loan.getReturnDate() == null || !isOverdue(loan)) {
            return null;
        }

        long daysOverdue = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        BigDecimal fineAmount = FINE_PER_DAY.multiply(BigDecimal.valueOf(daysOverdue));

        FineRecord fine = new FineRecord();
        fine.setId(UUID.randomUUID().toString());
        fine.setPatronId(loan.getPatron().getPatronId());
        fine.setLoanId(loan.getId());
        fine.setAmount(fineAmount);
        fine.setReason("Overdue book return: " + daysOverdue + " days late");

        return fineRepository.save(fine);
    }

    public BigDecimal calculateFine(Loan loan) {
        if (loan.getReturnDate() == null || !isOverdue(loan)) {
            return BigDecimal.ZERO;
        }

        long daysOverdue = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        return FINE_PER_DAY.multiply(BigDecimal.valueOf(daysOverdue));
    }

    public void markFineAsPaid(String fineId) {
        FineRecord fine = fineRepository.findById(fineId);
        if (fine != null) {
            fine.setStatus(FineStatus.PAID);
            fine.setPaidDate(LocalDate.now());
            fineRepository.save(fine);
        }
    }

    private boolean isOverdue(Loan loan) {
        LocalDate returnDate = loan.getReturnDate() != null ? loan.getReturnDate() : LocalDate.now();
        return returnDate.isAfter(loan.getDueDate());
    }
}