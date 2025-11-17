package library.service;

import library.model.*;
import library.model.enums.*;
import library.repository.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

public class PatronService {
    private final PatronRepository patronRepository;
    private final PatronAccountRepository accountRepository;
    private final BorrowingProfileRepository profileRepository;
    private final FineRecordRepository fineRepository;

    public PatronService(
            PatronRepository patronRepository,
            PatronAccountRepository accountRepository,
            BorrowingProfileRepository profileRepository,
            FineRecordRepository fineRepository) {
        this.patronRepository = patronRepository;
        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
        this.fineRepository = fineRepository;
    }

    // Create new patron with all associated entities
    public Patron createPatron(String name, String email, String phoneNumber) {
        String patronId = generatePatronId();
        
        // Create main patron
        Patron patron = new Patron(patronId, name, email, phoneNumber);
        
        // Create and link account
        PatronAccount account = new PatronAccount();
        account.setId(UUID.randomUUID().toString());
        account.setPatronId(patronId);
        account = accountRepository.save(account);
        
        // Create and link borrowing profile
        BorrowingProfile profile = new BorrowingProfile();
        profile.setId(UUID.randomUUID().toString());
        profile.setPatronId(patronId);
        profile = profileRepository.save(profile);
        
        // Link everything together
        patron.setAccount(account);
        patron.setBorrowingProfile(profile);
        
        return patronRepository.save(patron);
    }

    // Update patron information
    public void updatePatron(Patron patron) {
        patronRepository.update(patron);
    }

    // Manage patron account status
    public void suspendPatron(String patronId) {
        PatronAccount account = accountRepository.findByPatronId(patronId);
        if (account != null) {
            account.setStatus(AccountStatus.SUSPENDED);
            accountRepository.save(account);
        }
    }

    public void activatePatron(String patronId) {
        PatronAccount account = accountRepository.findByPatronId(patronId);
        if (account != null) {
            account.setStatus(AccountStatus.ACTIVE);
            accountRepository.save(account);
        }
    }

    // Membership management
    public void updateMembership(String patronId, MembershipType newType) {
        PatronAccount account = accountRepository.findByPatronId(patronId);
        if (account != null) {
            account.setMembershipType(newType);
            accountRepository.save(account);
        }
    }

    // Fine management
    public void recordFine(String patronId, String loanId, double amount, String reason) {
        FineRecord fine = new FineRecord();
        fine.setId(UUID.randomUUID().toString());
        fine.setPatronId(patronId);
        fine.setLoanId(loanId);
        fine.setAmount(BigDecimal.valueOf(amount));
        fine.setReason(reason);
        
        fineRepository.save(fine);
        
        // Update borrowing profile
        BorrowingProfile profile = profileRepository.findByPatronId(patronId);
        if (profile != null) {
            profile.setOutstandingFines(
                profile.getOutstandingFines().add(BigDecimal.valueOf(amount))
            );
            profileRepository.save(profile);
        }
    }

    public void payFine(String fineId) {
        FineRecord fine = fineRepository.findById(fineId);
        if (fine != null && fine.getStatus() == FineStatus.PENDING) {
            fine.markAsPaid();
            fineRepository.save(fine);
            
            // Update borrowing profile
            BorrowingProfile profile = profileRepository.findByPatronId(fine.getPatronId());
            if (profile != null) {
                profile.setOutstandingFines(
                    profile.getOutstandingFines().subtract(fine.getAmount())
                );
                profileRepository.save(profile);
            }
        }
    }

    // Queries and validations
    public Optional<Patron> getPatronById(String patronId) {
        return patronRepository.findById(patronId);
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public List<FineRecord> getPatronFines(String patronId) {
        return fineRepository.findByPatronId(patronId);
    }

    public boolean canPatronBorrow(String patronId) {
        Optional<Patron> patronOpt = patronRepository.findById(patronId);
        return patronOpt.map(Patron::canBorrow).orElse(false);
    }

    private String generatePatronId() {
        return "P" + UUID.randomUUID().toString().substring(0, 8);
    }
}
