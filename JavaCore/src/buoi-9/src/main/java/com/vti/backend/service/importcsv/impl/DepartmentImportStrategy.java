package com.vti.backend.service.importcsv.impl;

import com.vti.backend.repository.IDepartmentRepository;
import com.vti.backend.service.importcsv.IImportCSVStrategy;
import com.vti.dto.ImportError;
import com.vti.dto.csv.DepartmentCSV;
import com.vti.dto.context.DepartmentImportContext;
import com.vti.entity.Department;

import java.util.*;

public class DepartmentImportStrategy implements IImportCSVStrategy<DepartmentImportContext, Department, DepartmentCSV> {

    private final IDepartmentRepository departmentRepository;

    public DepartmentImportStrategy(IDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public int getExpectedColumns() {
        return 1;
    }

    @Override
    public DepartmentCSV parseAndValidateRow(String[] fields, List<ImportError<DepartmentCSV>> importErrors) {
        // Kiểm tra xem dòng có chứa dữ liệu tên phòng ban hay không
        if (fields.length == 0 || fields[0].trim().isEmpty()) {
            String msg = "Thiếu tên phòng ban.";
            DepartmentCSV badCsv = new DepartmentCSV("");
            importErrors.add(new ImportError<>(badCsv, msg));
            return null;
        }

        String name = fields[0].trim();
        DepartmentCSV csvData = new DepartmentCSV(name);

        // Kiểm tra giới hạn độ dài ký tự tối đa là 100 (varchar 100)
        if (name.length() > 100) {
            String msg = "Độ dài Tên phòng ban không được vượt quá 100 ký tự.";
            importErrors.add(new ImportError<>(csvData, msg));
            return null;
        }

        return csvData;
    }

    @Override
    public List<DepartmentCSV> filterInternalDuplicates(List<DepartmentCSV> rawList, List<ImportError<DepartmentCSV>> importErrors) {
        // Tính toán tần suất xuất hiện của tên phòng ban trong file CSV
        Map<String, Integer> frequencyMap = new HashMap<>();
        rawList.forEach(csv -> frequencyMap.put(csv.getDepartmentName(), frequencyMap.getOrDefault(csv.getDepartmentName(), 0) + 1));

        List<DepartmentCSV> filtered = new ArrayList<>();
        rawList.forEach(csv -> {
            String name = csv.getDepartmentName();
            // Kiểm tra xem tên phòng ban có bị trùng lặp ngay trong file CSV hay không
            if (frequencyMap.get(name) > 1) {
                String msg = "Tên phòng ban lặp lại trong file.";
                importErrors.add(new ImportError<>(csv, msg));
            } else {
                filtered.add(csv);
            }
        });
        return filtered;
    }

    @Override
    public Department mapToEntity(DepartmentCSV dto) {
        return new Department(dto.getDepartmentName());
    }

    @Override
    public DepartmentImportContext buildValidationContext(List<Department> entities) {
        List<String> names = new ArrayList<>();
        entities.forEach(dep -> names.add(dep.getDepartmentName()));
        // Truy vấn DB lấy các tên phòng ban đã tồn tại
        List<String> existingNames = departmentRepository.findExistingNames(names);
        return new DepartmentImportContext(new HashSet<>(existingNames));
    }

    @Override
    public List<Department> validateAgainstDatabase(
        List<Department> entities,
        DepartmentImportContext context,
        List<ImportError<DepartmentCSV>> importErrors,
        Map<Department, DepartmentCSV> entityToDtoMap
    ) {
        List<Department> validToInsert = new ArrayList<>();
        entities.forEach(dep -> {
            String name = dep.getDepartmentName();
            // Nếu tên phòng ban đã tồn tại trong DB, báo lỗi và bỏ qua
            if (context.getExistingNames().contains(name)) {
                String msg = "Phòng ban đã tồn tại.";
                importErrors.add(new ImportError<>(entityToDtoMap.get(dep), msg));
            } else {
                validToInsert.add(dep);
            }
        });
        return validToInsert;
    }

    @Override
    public void saveBatch(List<Department> entities) {
        boolean isSuccess = departmentRepository.createDepartments(entities);
        if (!isSuccess) {
            throw new RuntimeException("Không thể lưu dữ liệu phòng ban vào hệ thống.");
        }
    }

    @Override
    public String buildSummaryMessage(int total, int success, int skipped, int failed) {
        return "Kết quả Import: Tổng=" + total + ", Thành công=" + success + ", Bỏ (validate)=" + skipped + ", Lỗi DB=" + failed + ". Chi tiết lỗi đã ghi vào file.";
    }
}
