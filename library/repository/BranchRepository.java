package library.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import library.model.Branch;

public class BranchRepository {
    private Map<String, Branch> branches;

    public BranchRepository() {
        this.branches = new HashMap<>();
    }

    public void addBranch(Branch branch) {
        branches.put(branch.getBranchId(), branch);
    }

    public Optional<Branch> getBranchById(String branchId) {
        return Optional.ofNullable(branches.get(branchId));
    }

    public List<Branch> getAllBranches() {
        return new ArrayList<>(branches.values());
    }

    public void updateBranch(Branch branch) {
        if (branches.containsKey(branch.getBranchId())) {
            branches.put(branch.getBranchId(), branch);
        }
    }

    public void removeBranch(String branchId) {
        branches.remove(branchId);
    }

    public boolean exists(String branchId) {
        return branches.containsKey(branchId);
    }
}