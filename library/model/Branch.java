package library.model;

import library.model.enums.BranchStatus;
import java.util.List;
import java.util.ArrayList;

public class Branch {
    private String branchId;
    private String name;
    private String address;
    private String contactInfo;
    private List<BookCopy> inventory;
    private BranchStatus status;

    public Branch(String branchId, String name, String address) {
        this.branchId = branchId;
        this.name = name;
        this.address = address;
        this.inventory = new ArrayList<>();
        this.status = BranchStatus.ACTIVE;
    }

    // Getters and setters
    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public BranchStatus getStatus() { return status; }
    public void setStatus(BranchStatus status) { this.status = status; }

    public List<BookCopy> getInventory() { return new ArrayList<>(inventory); }

    public void addToInventory(BookCopy bookCopy) {
        if (bookCopy != null && !inventory.contains(bookCopy)) {
            inventory.add(bookCopy);
            bookCopy.setBranch(this);
        }
    }

    public void removeFromInventory(BookCopy bookCopy) {
        if (bookCopy != null && inventory.contains(bookCopy)) {
            inventory.remove(bookCopy);
            bookCopy.setBranch(null);
        }
    }
}