package com.vti.backend.service.impl;

import com.vti.backend.repository.IDepartmentRepository;
import com.vti.backend.repository.impl.DepartmentRepositoryImpl;
import com.vti.backend.service.IDepartmentService;
import com.vti.entity.Department;

import java.io.*;
import java.util.*;
import com.vti.dto.ImportError;

public class DepartmentServiceImpl implements IDepartmentService {
    private final IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public List<Department> findDepartmentByIdAndName(String searchName, int searchId) {
        return departmentRepository.findDepartmentByIdAndName(searchName, searchId);
    }

    @Override
    public List<Department> findeDepartmentHaveMoreThan2Acc() {
        return departmentRepository.findeDepartmentHaveMoreThan2Acc();
    }

    @Override
    public boolean createDepartment(String departmentName) {
        return departmentRepository.createDepartment(departmentName);
    }

    @Override
    public boolean deleteDepartment(int id) {
        return departmentRepository.deleteDepartment(id);
    }

    @Override
    public boolean updateDepartment(int id, String departmentName) {
        return departmentRepository.updateDepartment(id, departmentName);
    }

    @Override
    public boolean checkExistDepartment(String departmentName, Integer departmentId) {
        return departmentRepository.checkExistDepartment(departmentName, departmentId);
    }

    @Override
    public String importDepartmentCSV(String pathName) {
        // Kiểm tra file có tồn tại
        File file = new File(pathName);
        if (!file.exists()) {
            return "File không tồn tại.";
        }

        // Kiểm tra phần mở rộng định dạng file có phải là .csv
        if (!pathName.endsWith(".csv")) {
            return "Lỗi: Định dạng file không đúng!";
        }

        List<String[]> allRows = new ArrayList<>();
        List<ImportError> importErrors = new ArrayList<>();
        
        String headerLine = "";
        
        // Các biến đếm số lượng để hiển thị báo cáo tổng kết
        int totalInputLines = 0;
        int parseErrorCount = 0;   // Lỗi định dạng dòng / validation
        int internalDupCount = 0; // Lỗi trùng lặp nội bộ trong file CSV
        int dbErrorCount = 0;       // Lỗi do trùng lặp Database

        // BƯỚC 1: Đọc tất cả các dòng từ file CSV (bỏ qua dòng trống)
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            headerLine = br.readLine(); // Đọc dòng tiêu đề (Header)
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Bỏ qua dòng trống
                totalInputLines++;
                String[] fields = line.split(",", -1);
                allRows.add(fields);
            }
        } catch (Exception e) {
            return "Lỗi khi đọc file: " + e.getMessage();
        }

        // Tạo dòng Header cho file báo cáo lỗi bằng cách thêm cột error_message ở cuối
        String errorHeader = (headerLine != null ? headerLine.trim() : "department_name") + ",error_message";

        // Nếu file không chứa dòng nào hợp lệ về dữ liệu
        if (allRows.isEmpty()) {
            ImportError.writeErrorsToCSV(pathName, errorHeader, importErrors);
            int successCount = 0;
            int skippedCount = parseErrorCount;
            int failedCount = 0;
            return "Kết quả Import: Tổng=" + totalInputLines + ", Thành công=" + successCount + ", Bỏ (validate)=" + skippedCount + ", Lỗi DB=" + failedCount + ". Chi tiết lỗi đã ghi vào file.";
        }

        // BƯỚC 2: Đếm tần suất xuất hiện của tên phòng ban trong file CSV (phục vụ lọc trùng)
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String[] fields : allRows) {
            if (fields.length > 0) {
                String name = fields[0].trim();
                frequencyMap.put(name, frequencyMap.getOrDefault(name, 0) + 1);
            }
        }

        // BƯỚC 3: Lọc các dòng hợp lệ & thu thập các tên cần kiểm tra dưới Database
        List<String> namesToCheckDb = new ArrayList<>();
        List<String[]> validRows = new ArrayList<>();

        for (String[] fields : allRows) {
            String name = validateAndParseDepartmentRow(fields, importErrors);
            if (name == null) {
                parseErrorCount++;
                continue; // Lỗi định dạng dòng, bỏ qua và chuyển sang dòng tiếp theo
            }

            // Kiểm tra xem tên phòng ban có bị trùng lặp ngay trong file CSV hay không
            if (frequencyMap.get(name) > 1) {
                String msg = "Tên phòng ban lặp lại trong file.";
                importErrors.add(new ImportError(Arrays.asList(fields), msg));
                internalDupCount++;
                continue;
            }

            namesToCheckDb.add(name);
            validRows.add(fields);
        }

        // BƯỚC 4: Kiểm tra tồn tại trong Database
        List<String> existingNamesInDB = departmentRepository.findExistingNames(namesToCheckDb);
        List<Department> newDepartments = new ArrayList<>();
        List<String> skippedNames = new ArrayList<>();

        for (String[] fields : validRows) {
            String name = fields[0].trim();
            // Nếu tên phòng ban đã tồn tại trong DB, báo lỗi và bỏ qua
            if (existingNamesInDB.contains(name)) {
                String msg = "Phòng ban đã tồn tại.";
                importErrors.add(new ImportError(Arrays.asList(fields), msg));
                if (!skippedNames.contains(name)) {
                    skippedNames.add(name);
                }
                dbErrorCount++;
                continue;
            }

            newDepartments.add(new Department(name));
        }

        // BƯỚC 5: Thực hiện Batch Insert lưu các phòng ban mới vào DB
        boolean isSuccess = true;
        if (!newDepartments.isEmpty()) {
            isSuccess = departmentRepository.createDepartments(newDepartments);
        }

        // Ghi báo cáo danh sách dòng lỗi ra file CSV
        ImportError.writeErrorsToCSV(pathName, errorHeader, importErrors);

        if (!isSuccess) return "Lỗi: Không thể lưu dữ liệu vào hệ thống.";

        int successCount = newDepartments.size();
        int skippedCount = parseErrorCount + internalDupCount;
        int failedCount = dbErrorCount;

        return "Kết quả Import: Tổng=" + totalInputLines + ", Thành công=" + successCount + ", Bỏ (validate)=" + skippedCount + ", Lỗi DB=" + failedCount + ". Chi tiết lỗi đã ghi vào file.";
    }

    /**
     * Phương thức validate và phân tích (parse) từng dòng phòng ban trong file CSV.
     * Trả về tên phòng ban (String) nếu dòng dữ liệu hợp lệ về định dạng, ngược lại trả về null.
     */
    private String validateAndParseDepartmentRow(String[] fields, List<ImportError> importErrors) {
        // Kiểm tra xem dòng có chứa dữ liệu tên phòng ban hay không
        if (fields.length == 0 || fields[0].trim().isEmpty()) {
            String msg = "Thiếu tên phòng ban.";
            importErrors.add(new ImportError(Arrays.asList(fields), msg));
            return null;
        }

        String name = fields[0].trim();

        // Kiểm tra giới hạn độ dài ký tự tối đa là 100 (varchar 100)
        if (name.length() > 100) {
            String msg = "Độ dài Tên phòng ban không được vượt quá 100 ký tự.";
            importErrors.add(new ImportError(Arrays.asList(fields), msg));
            return null;
        }

        return name;
    }
    
}
