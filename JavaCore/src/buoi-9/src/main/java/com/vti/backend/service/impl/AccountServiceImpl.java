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
import java.io.File;
import java.io.FileReader;
import java.util.*;
import com.vti.dto.ImportError;

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
        // Kiểm tra file có tồn tại
        File file = new File(pathName);
        if (!file.exists()) {
            return "File không tồn tại.";
        }

        // Kiểm tra phần mở rộng định dạng file có phải là .csv
        if (pathName == null || !pathName.endsWith(".csv")) {
            return "Lỗi: Định dạng file không đúng!";
        }

        List<Account> allRowsInFile = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        List<ImportError> importErrors = new ArrayList<>();
        Map<Account, List<String>> accountRawFieldsMap = new IdentityHashMap<>();
        
        // Map để đếm tần suất xuất hiện của email và username trong file CSV
        Map<String, Integer> emailFrequencyMap = new HashMap<>();
        Map<String, Integer> usernameFrequencyMap = new HashMap<>();

        String headerLine = "";

        // Các biến đếm số lượng để hiển thị báo cáo tổng kết
        int totalInputLines = 0;
        int parseErrorCount = 0;   // Lỗi định dạng dòng / validation
        int internalDupCount = 0; // Lỗi trùng lặp trong file CSV
        int dbErrorCount = 0;       // Lỗi do trùng lặp Database hoặc khóa ngoại không tồn tại

        // PHASE 1.1: Đọc file CSV, validate format từng dòng & đếm tần suất xuất hiện
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            headerLine = br.readLine(); // Đọc dòng Header
            String line;
            int lineNum = 1;

            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.trim().isEmpty()) continue; // Bỏ qua dòng trống
                totalInputLines++;
                String[] fields = line.split(",", -1);

                // Thực hiện validate định dạng dòng
                Account acc = this.validateAccountRow(fields, lineNum, errorMessages, importErrors);
                if (acc == null) {
                    parseErrorCount++;
                    continue; // Dòng bị lỗi định dạng, bỏ qua và chuyển dòng tiếp theo
                }

                allRowsInFile.add(acc);
                // Map Account và CSV
                accountRawFieldsMap.put(acc, Arrays.asList(fields));

                // Đếm tần suất xuất hiện để check trùng lặp
                emailFrequencyMap.put(acc.getEmail(), emailFrequencyMap.getOrDefault(acc.getEmail(), 0) + 1);
                usernameFrequencyMap.put(acc.getUsername(), usernameFrequencyMap.getOrDefault(acc.getUsername(), 0) + 1);
            }
        } catch (Exception e) {
            return "Lỗi đọc file: " + e.getMessage();
        }

        // Tạo dòng Header cho file báo cáo lỗi thêm cột error_message ở cuối
        String errorHeader = (headerLine != null ? headerLine.trim() : "email,full_name,username,department_id,position_id") + ",error_message";

        // Nếu file không chứa dòng nào hợp lệ, ghi file lỗi và trả về thông báo
        if (allRowsInFile.isEmpty()) {
            ImportError.writeErrorsToCSV(pathName, errorHeader, importErrors);
            int successCount = 0;
            int skippedCount = parseErrorCount;
            int failedCount = 0;
            return "Kết quả Import Account: Tổng=" + totalInputLines + ", Thành công=" + successCount + ", Bỏ (validate)=" + skippedCount + ", Lỗi DB=" + failedCount + ". Chi tiết lỗi đã ghi vào file.";
        }

        // PHASE 1.2: Lọc trùng lặp trong file CSV
        List<Account> filteredAccounts = this.filterInternalDuplicates(
                allRowsInFile, emailFrequencyMap, usernameFrequencyMap, errorMessages, importErrors, accountRawFieldsMap
        );
        internalDupCount = allRowsInFile.size() - filteredAccounts.size();

        // Nếu tất cả record đều bị trùng lặp, ghi file báo cáo lỗi và kết thúc
        if (filteredAccounts.isEmpty()) {
            ImportError.writeErrorsToCSV(pathName, errorHeader, importErrors);
            int successCount = 0;
            int skippedCount = parseErrorCount + internalDupCount;
            int failedCount = 0;
            return "Kết quả Import Account: Tổng=" + totalInputLines + ", Thành công=" + successCount + ", Bỏ (validate)=" + skippedCount + ", Lỗi DB=" + failedCount + ". Chi tiết lỗi đã ghi vào file.";
        }

        // Gom danh sách các ID phòng ban và chức vụ cần kiểm tra tồn tại dưới Database
        Set<Integer> depIdsToCheck = new HashSet<>();
        Set<Integer> posIdsToCheck = new HashSet<>();
        for (Account acc : filteredAccounts) {
            if (acc.getDepartment() != null) depIdsToCheck.add(acc.getDepartment().getDepartmentID());
            if (acc.getPosition() != null) posIdsToCheck.add(acc.getPosition().getPositionID());
        }

        // PHASE 2: Truy vấn một lần từ Database và kiểm tra trùng lặp DB / Khóa ngoại không tồn tại
        IDepartmentRepository depRepo = new DepartmentRepositoryImpl();
        IPositionRepository posRepo = new PositionRepositoryImpl();

        List<String> existingEmails = accountRepository.findExistingEmails(new ArrayList<>(emailFrequencyMap.keySet()));
        List<String> existingUsernames = accountRepository.findExistingUsernames(new ArrayList<>(usernameFrequencyMap.keySet()));
        List<Integer> existingDepIds = depRepo.findExistingIds(new ArrayList<>(depIdsToCheck));
        List<Integer> existingPosIds = posRepo.findExistingIds(new ArrayList<>(posIdsToCheck));

        // Kiểm tra bản ghi hợp lệ với DB
        List<Account> finalAccountsToInsert = this.validateAgainstDatabase(
                filteredAccounts, existingEmails, existingUsernames, existingDepIds, existingPosIds,
                errorMessages, importErrors, accountRawFieldsMap
        );
        dbErrorCount = filteredAccounts.size() - finalAccountsToInsert.size();

        // PHASE 3: Thực hiện Batch Insert lưu các bản ghi hoàn toàn hợp lệ xuống DB
        if (!finalAccountsToInsert.isEmpty()) {
            accountRepository.createAccountsBatch(finalAccountsToInsert);
        }

        // Ghi báo cáo danh sách dòng lỗi ra file CSV
        ImportError.writeErrorsToCSV(pathName, errorHeader, importErrors);

        int successCount = finalAccountsToInsert.size();
        int skippedCount = parseErrorCount + internalDupCount;
        int failedCount = dbErrorCount;

        return "Kết quả Import Account: Tổng=" + totalInputLines + ", Thành công=" + successCount + ", Bỏ (validate)=" + skippedCount + ", Lỗi DB=" + failedCount + ". Chi tiết lỗi đã ghi vào file.";
    }

    /**
     * Phương thức validate từng dòng trong file CSV.
     * Trả về đối tượng Account nếu dòng dữ liệu hợp lệ về định dạng, ngược lại trả về null.
     */
    private Account validateAccountRow(String[] fields, int lineNum, List<String> errorMessages, List<ImportError> importErrors) {
        // Kiểm tra dòng có đủ các cột dữ liệu cơ bản
        if (fields.length < 3) {
            String msg = "Thiếu cột dữ liệu cơ bản.";
            errorMessages.add("Dòng " + lineNum + ": " + msg);
            importErrors.add(new ImportError(Arrays.asList(fields), msg));
            return null;
        }

        String email = fields[0].trim();
        String fullname = fields[1].trim();
        String username = fields[2].trim();
        String department = fields[3].trim();
        String position = fields[4].trim();

        // Kiểm tra các trường dữ liệu không được để trống (Not Null)
        List<String> rowFormatErrors = new ArrayList<>();
        if (email.isEmpty()) rowFormatErrors.add("Thiếu Email");
        if (fullname.isEmpty()) rowFormatErrors.add("Thiếu Fullname");
        if (username.isEmpty()) rowFormatErrors.add("Thiếu Username");

        // Kiểm tra giới hạn độ dài ký tự tối đa là 100 (varchar 100)
        if (email.length() > 100) rowFormatErrors.add("Độ dài Email không được vượt quá 100 ký tự");
        if (fullname.length() > 100) rowFormatErrors.add("Độ dài Fullname không được vượt quá 100 ký tự");
        if (username.length() > 100) rowFormatErrors.add("Độ dài Username không được vượt quá 100 ký tự");

        // Nếu phát hiện lỗi định dạng cơ bản, ghi nhận lỗi và trả về null
        if (!rowFormatErrors.isEmpty()) {
            String msg = String.join(", ", rowFormatErrors) + ".";
            errorMessages.add("Dòng " + lineNum + ": " + msg);
            importErrors.add(new ImportError(Arrays.asList(fields), msg));
            return null;
        }

        // Kiểm tra định dạng Email có hợp lệ hay không bằng biểu thức chính quy
        if (!isValidEmail(email)) {
            String msg = "Định dạng Email '" + email + "' không hợp lệ.";
            errorMessages.add("Dòng " + lineNum + ": " + msg);
            importErrors.add(new ImportError(Arrays.asList(fields), msg));
            return null;
        }

        // Phân tích và ép kiểu cho DepartmentID và PositionID (nếu có) sang số nguyên
        Department dep = null;
        Position pos = null;
        try {
            if (fields.length >= 4 && !department.isEmpty()) {
                int depId = Integer.parseInt(department);
                dep = new Department();
                dep.setDepartmentID(depId);
            }
            if (fields.length >= 5 && !position.isEmpty()) {
                int posId = Integer.parseInt(position);
                pos = new Position();
                pos.setPositionID(posId);
            }
        } catch (NumberFormatException e) {
            String msg = "ID phòng ban hoặc chức vụ phải là số nguyên.";
            errorMessages.add("Dòng " + lineNum + ": " + msg);
            importErrors.add(new ImportError(Arrays.asList(fields), msg));
            return null;
        }

        return new Account(0, email, username, fullname, dep, pos, null);
    }

    /**
     * Phương thức lọc các tài khoản bị trùng lặp dữ liệu (Email hoặc Username) ngay trong file CSV.
     */
    private List<Account> filterInternalDuplicates(
            List<Account> allRows,
            Map<String, Integer> emailFreq,
            Map<String, Integer> usernameFreq,
            List<String> errorMessages,
            List<ImportError> importErrors,
            Map<Account, List<String>> rawFieldsMap
    ) {
        List<Account> filtered = new ArrayList<>();
        for (Account acc : allRows) {
            List<String> internalDupErrors = new ArrayList<>();
            // Kiểm tra trùng email
            if (emailFreq.get(acc.getEmail()) > 1) {
                internalDupErrors.add("Email lặp lại trong file");
            }
            // Kiểm tra trùng username
            if (usernameFreq.get(acc.getUsername()) > 1) {
                internalDupErrors.add("Username lặp lại trong file");
            }

            // Nếu không trùng lặp thì giữ lại, ngược lại ghi nhận báo cáo lỗi
            if (internalDupErrors.isEmpty()) {
                filtered.add(acc);
            } else {
                String msg = String.join(", ", internalDupErrors) + ".";
                errorMessages.add("Tài khoản '" + acc.getEmail() + "': " + msg);
                importErrors.add(new ImportError(rawFieldsMap.get(acc), msg));
            }
        }
        return filtered;
    }

    /**
     * Phương thức đối chiếu dữ liệu với Database để kiểm tra trùng lặp Email/Username
     * và tồn tại khóa ngoại (DepartmentID, PositionID).
     */
    private List<Account> validateAgainstDatabase(
            List<Account> accounts,
            List<String> existingEmails,
            List<String> existingUsernames,
            List<Integer> existingDepIds,
            List<Integer> existingPosIds,
            List<String> errorMessages,
            List<ImportError> importErrors,
            Map<Account, List<String>> rawFieldsMap
    ) {
        List<Account> validToInsert = new ArrayList<>();
        for (Account acc : accounts) {
            List<String> dbErrors = new ArrayList<>();
            
            // Check trùng email dưới DB
            if (existingEmails.contains(acc.getEmail())) dbErrors.add("Email đã tồn tại");
            // Check trùng username dưới DB
            if (existingUsernames.contains(acc.getUsername())) dbErrors.add("Username đã tồn tại");
            
            // Check tồn tại DepartmentID
            if (acc.getDepartment() != null && !existingDepIds.contains(acc.getDepartment().getDepartmentID())) {
                dbErrors.add("DepartmentID " + acc.getDepartment().getDepartmentID() + " không tồn tại");
            }
            // Check tồn tại PositionID
            if (acc.getPosition() != null && !existingPosIds.contains(acc.getPosition().getPositionID())) {
                dbErrors.add("PositionID " + acc.getPosition().getPositionID() + " không tồn tại");
            }

            // Nếu hoàn toàn hợp lệ với DB thì đưa vào hàng đợi insert, ngược lại lưu lỗi
            if (dbErrors.isEmpty()) {
                validToInsert.add(acc);
            } else {
                String msg = String.join(", ", dbErrors) + ".";
                errorMessages.add("Tài khoản '" + acc.getEmail() + "': " + msg);
                importErrors.add(new ImportError(rawFieldsMap.get(acc), msg));
            }
        }
        return validToInsert;
    }

}
