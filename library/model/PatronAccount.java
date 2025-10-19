package library.model;

import library.model.enums.AccountStatus;
import library.model.enums.MembershipType;
import java.time.LocalDate;

public class PatronAccount {
    private String id;
    private String patronId;
    private AccountStatus status;
    private MembershipType membershipType;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;

    public PatronAccount() {
        this.status = AccountStatus.ACTIVE;
        this.membershipType = MembershipType.BASIC;
        this.membershipStartDate = LocalDate.now();
        this.membershipEndDate = membershipStartDate.plusYears(1);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPatronId() { return patronId; }
    public void setPatronId(String patronId) { this.patronId = patronId; }
    
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    
    public MembershipType getMembershipType() { return membershipType; }
    public void setMembershipType(MembershipType membershipType) { this.membershipType = membershipType; }
    
    public LocalDate getMembershipStartDate() { return membershipStartDate; }
    public void setMembershipStartDate(LocalDate startDate) { this.membershipStartDate = startDate; }
    
    public LocalDate getMembershipEndDate() { return membershipEndDate; }
    public void setMembershipEndDate(LocalDate endDate) { this.membershipEndDate = endDate; }

    public boolean isActive() {
        return status == AccountStatus.ACTIVE && 
               LocalDate.now().isBefore(membershipEndDate);
    }
}