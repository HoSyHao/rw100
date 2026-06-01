package com.vti.backend.service.importcsv.impl;

import com.vti.backend.repository.IAccountRepository;
import com.vti.backend.repository.IDepartmentRepository;
import com.vti.backend.repository.IPositionRepository;
import com.vti.backend.repository.impl.DepartmentRepositoryImpl;
import com.vti.backend.repository.impl.PositionRepositoryImpl;
import com.vti.backend.service.importcsv.IImportCSVStrategy;
import com.vti.dto.ImportError;
import com.vti.dto.csv.AccountCSV;
import com.vti.dto.context.AccountImportContext;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;

import java.util.*;

import static com.vti.utils.InputUtils.isValidEmail;

public class AccountImportStrategy implements IImportCSVStrategy<AccountImportContext, Account, AccountCSV> {

    private final IAccountRepository accountRepository;
    private final IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    private final IPositionRepository positionRepository = new PositionRepositoryImpl();

    public AccountImportStrategy(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public int getExpectedColumns() {
        return 5;
    }

    @Override
    public AccountCSV parseAndValidateRow(String[] fields, List<ImportError<AccountCSV>> importErrors) {
        // Kiểm tra dòng có đủ các cột dữ liệu cơ bản
        if (fields.length < 3) {
            String msg = "Thiếu cột dữ liệu cơ bản.";
            String email = fields.length >= 1 ? fields[0].trim() : "";
            String fullname = fields.length >= 2 ? fields[1].trim() : "";
            AccountCSV badCsv = new AccountCSV(email, fullname, "", "", "");
            importErrors.add(new ImportError<>(badCsv, msg));
            return null;
        }

        String email = fields[0].trim();
        String fullname = fields[1].trim();
        String username = fields[2].trim();
        String department = (fields.length >= 4) ? fields[3].trim() : "";
        String position = (fields.length >= 5) ? fields[4].trim() : "";

        AccountCSV csvData = new AccountCSV(email, fullname, username, department, position);

        // Kiểm tra các trường dữ liệu không được để trống (Not Null)
        List<String> rowFormatErrors = new ArrayList<>();
        if (email.isEmpty()) rowFormatErrors.add("Thiếu Email");
        if (fullname.isEmpty()) rowFormatErrors.add("Thiếu Fullname");
        if (username.isEmpty()) rowFormatErrors.add("Thiếu Username");

        // Kiểm tra giới hạn độ dài ký tự tối đa là 100 (varchar 100)
        if (email.length() > 100) rowFormatErrors.add("Độ dài Email không được vượt quá 100 ký tự");
        if (fullname.length() > 100) rowFormatErrors.add("Độ dài Fullname không được vượt quá 100 ký tự");
        if (username.length() > 100) rowFormatErrors.add("Độ dài Username không được vượt quá 100 ký tự");

        // Kiểm tra DepartmentID và PositionID (nếu có) phải là số nguyên
        if (!department.isEmpty()) {
            try {
                Integer.parseInt(department);
            } catch (NumberFormatException e) {
                rowFormatErrors.add("ID phòng ban phải là số nguyên");
            }
        }
        if (!position.isEmpty()) {
            try {
                Integer.parseInt(position);
            } catch (NumberFormatException e) {
                rowFormatErrors.add("ID chức vụ phải là số nguyên");
            }
        }

        // Nếu phát hiện lỗi định dạng cơ bản, ghi nhận lỗi và trả về null
        if (!rowFormatErrors.isEmpty()) {
            String msg = String.join(", ", rowFormatErrors) + ".";
            importErrors.add(new ImportError<>(csvData, msg));
            return null;
        }

        // Kiểm tra định dạng Email có hợp lệ hay không bằng biểu thức chính quy
        if (!isValidEmail(email)) {
            String msg = "Định dạng Email '" + email + "' không hợp lệ.";
            importErrors.add(new ImportError<>(csvData, msg));
            return null;
        }

        return csvData;
    }

    @Override
    public List<AccountCSV> filterInternalDuplicates(List<AccountCSV> rawList, List<ImportError<AccountCSV>> importErrors) {
        // Tính toán tần suất xuất hiện để check trùng lặp nội bộ trong file CSV
        Map<String, Integer> emailFrequencyMap = new HashMap<>();
        Map<String, Integer> usernameFrequencyMap = new HashMap<>();
        rawList.forEach(csv -> {
            emailFrequencyMap.put(csv.getEmail(), emailFrequencyMap.getOrDefault(csv.getEmail(), 0) + 1);
            usernameFrequencyMap.put(csv.getUsername(), usernameFrequencyMap.getOrDefault(csv.getUsername(), 0) + 1);
        });

        List<AccountCSV> filtered = new ArrayList<>();
        rawList.forEach(csv -> {
            List<String> internalDupErrors = new ArrayList<>();
            // Kiểm tra trùng email
            if (emailFrequencyMap.get(csv.getEmail()) > 1) {
                internalDupErrors.add("Email lặp lại trong file");
            }
            // Kiểm tra trùng username
            if (usernameFrequencyMap.get(csv.getUsername()) > 1) {
                internalDupErrors.add("Username lặp lại trong file");
            }

            // Nếu không trùng lặp thì giữ lại, ngược lại ghi nhận báo cáo lỗi
            if (internalDupErrors.isEmpty()) {
                filtered.add(csv);
            } else {
                String msg = String.join(", ", internalDupErrors) + ".";
                importErrors.add(new ImportError<>(csv, msg));
            }
        });
        return filtered;
    }

    @Override
    public Account mapToEntity(AccountCSV dto) {
        // Ép kiểu cho DepartmentID và PositionID (nếu có) sang số nguyên
        // (Đã được kiểm tra tính hợp lệ số nguyên ở bước parseAndValidateRow)
        Department dep = null;
        Position pos = null;
        if (dto.getDepartment() != null && !dto.getDepartment().isEmpty()) {
            int depId = Integer.parseInt(dto.getDepartment());
            dep = new Department(); dep.setDepartmentID(depId);
        }
        if (dto.getPosition() != null && !dto.getPosition().isEmpty()) {
            int posId = Integer.parseInt(dto.getPosition());
            pos = new Position(); pos.setPositionID(posId);
        }
        return new Account(0, dto.getEmail(), dto.getUsername(), dto.getFullname(), dep, pos, null);
    }

    @Override
    public AccountImportContext buildValidationContext(List<Account> entities) {
        Set<String> emails = new HashSet<>();
        Set<String> usernames = new HashSet<>();
        Set<Integer> depIds = new HashSet<>();
        Set<Integer> posIds = new HashSet<>();

        entities.forEach(acc -> {
            emails.add(acc.getEmail());
            usernames.add(acc.getUsername());
            if (acc.getDepartment() != null) {
                depIds.add(acc.getDepartment().getDepartmentID());
            }
            if (acc.getPosition() != null) {
                posIds.add(acc.getPosition().getPositionID());
            }
        });

        // Truy vấn DB để lấy danh sách đã tồn tại
        List<String> existingEmails = accountRepository.findExistingEmails(new ArrayList<>(emails));
        List<String> existingUsernames = accountRepository.findExistingUsernames(new ArrayList<>(usernames));
        List<Integer> existingDepIds = departmentRepository.findExistingIds(new ArrayList<>(depIds));
        List<Integer> existingPosIds = positionRepository.findExistingIds(new ArrayList<>(posIds));

        return new AccountImportContext(
            new HashSet<>(existingEmails),
            new HashSet<>(existingUsernames),
            new HashSet<>(existingDepIds),
            new HashSet<>(existingPosIds)
        );
    }

    @Override
    public List<Account> validateAgainstDatabase(
        List<Account> entities,
        AccountImportContext context,
        List<ImportError<AccountCSV>> importErrors,
        Map<Account, AccountCSV> entityToDtoMap
    ) {
        List<Account> validToInsert = new ArrayList<>();
        entities.forEach(acc -> {
            List<String> dbErrors = new ArrayList<>();
            
            // Check trùng email dưới DB
            if (context.getExistingEmails().contains(acc.getEmail())) dbErrors.add("Email đã tồn tại");
            // Check trùng username dưới DB
            if (context.getExistingUsernames().contains(acc.getUsername())) dbErrors.add("Username đã tồn tại");
            
            // Check tồn tại DepartmentID
            if (acc.getDepartment() != null && !context.getExistingDepIds().contains(acc.getDepartment().getDepartmentID())) {
                dbErrors.add("DepartmentID " + acc.getDepartment().getDepartmentID() + " không tồn tại");
            }
            // Check tồn tại PositionID
            if (acc.getPosition() != null && !context.getExistingPosIds().contains(acc.getPosition().getPositionID())) {
                dbErrors.add("PositionID " + acc.getPosition().getPositionID() + " không tồn tại");
            }

            // Nếu hoàn toàn hợp lệ với DB thì đưa vào hàng đợi insert, ngược lại lưu lỗi
            if (dbErrors.isEmpty()) {
                validToInsert.add(acc);
            } else {
                String msg = String.join(", ", dbErrors) + ".";
                importErrors.add(new ImportError<>(entityToDtoMap.get(acc), msg));
            }
        });
        return validToInsert;
    }

    @Override
    public void saveBatch(List<Account> entities) {
        accountRepository.createAccountsBatch(entities);
    }

    @Override
    public String buildSummaryMessage(int total, int success, int skipped, int failed) {
        return "Kết quả Import Account: Tổng=" + total + ", Thành công=" + success + ", Bỏ (validate)=" + skipped + ", Lỗi DB=" + failed + ". Chi tiết lỗi đã ghi vào file.";
    }
}
