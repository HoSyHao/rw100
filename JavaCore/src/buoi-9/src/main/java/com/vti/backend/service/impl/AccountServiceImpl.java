package com.vti.backend.service.impl;

import com.vti.backend.repository.IAccountRepository;
import com.vti.backend.repository.IDepartmentRepository;
import com.vti.backend.repository.IPositionRepository;
import com.vti.backend.repository.impl.AccountRepositoryImpl;
import com.vti.backend.repository.impl.DepartmentRepositoryImpl;
import com.vti.backend.repository.impl.PositionRepositoryImpl;
import com.vti.backend.service.IAccountService;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static com.vti.utils.InputUtils.isValidEmail;

public class AccountServiceImpl implements IAccountService {
    private final IAccountRepository accountRepository = new AccountRepositoryImpl();

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @Override
    public List<Account> findByFullname(String fullname) {
        return accountRepository.findByFullname(fullname);
    }

    @Override
    public List<Account> findByFullnameAndUsername(String val_ful, String val_user) {
        return accountRepository.findByFullnameAndUsername(val_ful, val_user);
    }

    @Override
    public boolean createAccount(String email, String username, String fullname, Integer departmentId, Integer positionId) {
        return accountRepository.createAccount(email, username, fullname, departmentId, positionId);
    }

    @Override
    public boolean updateAccount(String email, String username, String fullname, Integer departmentId, Integer positionId, int accountId) {
        return accountRepository.updateAccount(email, username, fullname, departmentId, positionId, accountId);
    }

    @Override
    public boolean deleteAccount(int accountId) {
        return accountRepository.deleteAccount(accountId);
    }

    @Override
    public boolean checkAccountExists(String email, String username, Integer accountId) {
        return accountRepository.checkAccountExists(email, username, accountId);
    }

    @Override
    public com.vti.entity.Account getAccountById(int accountId) {
        return accountRepository.getAccountById(accountId);
    }

    @Override
    public String importAccountCSV(String pathName) {
        if (pathName == null || !pathName.endsWith(".csv")) {
            return "Lỗi: Định dạng file không đúng!";
        }

        List<Account> allRowsInFile = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        
        // Map để đếm số lần xuất hiện trong file
        Map<String, Integer> emailFrequencyMap = new HashMap<>();
        Map<String, Integer> usernameFrequencyMap = new HashMap<>();

        // PHASE 1.1: Đọc file, validate format và đếm tần suất
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line = br.readLine(); // Header
            int lineNum = 1;

            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(",", -1);

                if (fields.length < 3) {
                    errorMessages.add("Dòng " + lineNum + ": Thiếu cột dữ liệu cơ bản.");
                    continue;
                }

                String email = fields[0].trim();
                String fullname = fields[1].trim();
                String username = fields[2].trim();

                // Check Not Null & Format
                List<String> rowFormatErrors = new ArrayList<>();
                if (email.isEmpty()) rowFormatErrors.add("Thiếu Email");
                if (fullname.isEmpty()) rowFormatErrors.add("Thiếu Fullname");
                if (username.isEmpty()) rowFormatErrors.add("Thiếu Username");

                if (!rowFormatErrors.isEmpty()) {
                    errorMessages.add("Dòng " + lineNum + ": " + String.join(", ", rowFormatErrors) + ".");
                    continue;
                }

                if (!isValidEmail(email)) {
                    errorMessages.add("Dòng " + lineNum + ": Định dạng Email '" + email + "' không hợp lệ.");
                    continue;
                }

                // Parse Foreign Keys
                Department dep = null;
                Position pos = null;
                try {
                    if (fields.length >= 4 && !fields[3].trim().isEmpty()) {
                        int depId = Integer.parseInt(fields[3].trim());
                        dep = new Department(); dep.setDepartmentID(depId);
                    }
                    if (fields.length >= 5 && !fields[4].trim().isEmpty()) {
                        int posId = Integer.parseInt(fields[4].trim());
                        pos = new Position(); pos.setPositionID(posId);
                    }
                } catch (NumberFormatException e) {
                    errorMessages.add("Dòng " + lineNum + ": ID phòng ban hoặc chức vụ phải là số nguyên.");
                    continue;
                }

                Account acc = new Account(0, email, username, fullname, dep, pos, null);
                allRowsInFile.add(acc);

                // Đếm tần suất
                emailFrequencyMap.put(email, emailFrequencyMap.getOrDefault(email, 0) + 1);
                usernameFrequencyMap.put(username, usernameFrequencyMap.getOrDefault(username, 0) + 1);
            }
        } catch (Exception e) {
            return "Lỗi đọc file: " + e.getMessage();
        }

        if (allRowsInFile.isEmpty()) {
            return reportErrors("Cảnh báo: Không có dữ liệu hợp lệ để xử lý.", errorMessages);
        }

        // PHASE 1.2: Lọc trùng nội bộ
        List<Account> filteredAccounts = new ArrayList<>();
        Set<Integer> depIdsToCheck = new HashSet<>();
        Set<Integer> posIdsToCheck = new HashSet<>();

        for (Account acc : allRowsInFile) {
            List<String> internalDupErrors = new ArrayList<>();
            if (emailFrequencyMap.get(acc.getEmail()) > 1) {
                internalDupErrors.add("Email lặp lại trong file");
            }
            if (usernameFrequencyMap.get(acc.getUsername()) > 1) {
                internalDupErrors.add("Username lặp lại trong file");
            }

            if (internalDupErrors.isEmpty()) {
                filteredAccounts.add(acc);
                if (acc.getDepartment() != null) depIdsToCheck.add(acc.getDepartment().getDepartmentID());
                if (acc.getPosition() != null) posIdsToCheck.add(acc.getPosition().getPositionID());
            } else {
                errorMessages.add("Tài khoản '" + acc.getEmail() + "': " + String.join(", ", internalDupErrors) + ".");
            }
        }

        if (filteredAccounts.isEmpty()) {
            return reportErrors("Cảnh báo: Tất cả dữ liệu đều bị trùng lặp nội bộ.", errorMessages);
        }

        // PHASE 2: Check Database
        IDepartmentRepository depRepo = new DepartmentRepositoryImpl();
        IPositionRepository posRepo = new PositionRepositoryImpl();

        List<String> existingEmails = accountRepository.findExistingEmails(new ArrayList<>(emailFrequencyMap.keySet()));
        List<String> existingUsernames = accountRepository.findExistingUsernames(new ArrayList<>(usernameFrequencyMap.keySet()));
        List<Integer> existingDepIds = depRepo.findExistingIds(new ArrayList<>(depIdsToCheck));
        List<Integer> existingPosIds = posRepo.findExistingIds(new ArrayList<>(posIdsToCheck));

        List<Account> finalAccountsToInsert = new ArrayList<>();
        for (Account acc : filteredAccounts) {
            List<String> dbErrors = new ArrayList<>();
            
            if (existingEmails.contains(acc.getEmail())) dbErrors.add("Email đã tồn tại");
            if (existingUsernames.contains(acc.getUsername())) dbErrors.add("Username đã tồn tại");
            
            if (acc.getDepartment() != null && !existingDepIds.contains(acc.getDepartment().getDepartmentID())) {
                dbErrors.add("DepartmentID " + acc.getDepartment().getDepartmentID() + " không tồn tại");
            }
            if (acc.getPosition() != null && !existingPosIds.contains(acc.getPosition().getPositionID())) {
                dbErrors.add("PositionID " + acc.getPosition().getPositionID() + " không tồn tại");
            }

            if (dbErrors.isEmpty()) {
                finalAccountsToInsert.add(acc);
            } else {
                errorMessages.add("Tài khoản '" + acc.getEmail() + "': " + String.join(", ", dbErrors) + ".");
            }
        }

        // PHASE 3: Insert
        if (!finalAccountsToInsert.isEmpty()) {
            accountRepository.createAccountsBatch(finalAccountsToInsert);
        }

        return reportFinalResult(finalAccountsToInsert.size(), errorMessages);
    }

    private String reportErrors(String title, List<String> errors) {
        StringBuilder sb = new StringBuilder(title + "\n");
        sb.append("Chi tiết lỗi:\n");
        for (int i = 0; i < Math.min(10, errors.size()); i++) {
            sb.append("  + ").append(errors.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String reportFinalResult(int successCount, List<String> errors) {
        StringBuilder sb = new StringBuilder("Kết quả Import Account:\n");
        sb.append("- Thành công: ").append(successCount).append(" tài khoản\n");
        sb.append("- Bỏ qua/Lỗi: ").append(errors.size()).append(" tài khoản\n");
        if (!errors.isEmpty()) {
            sb.append("--- Chi tiết lỗi (Hiển thị tối đa 10 dòng) ---\n");
            for (int i = 0; i < Math.min(10, errors.size()); i++) {
                sb.append("  + ").append(errors.get(i)).append("\n");
            }
        }
        return sb.toString();
    }
}
