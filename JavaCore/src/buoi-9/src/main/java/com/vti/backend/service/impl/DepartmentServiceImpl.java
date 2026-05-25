package com.vti.backend.service.impl;

import com.vti.backend.repository.IDepartmentRepository;
import com.vti.backend.repository.impl.DepartmentRepositoryImpl;
import com.vti.backend.service.IDepartmentService;
import com.vti.entity.Department;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        if (pathName == null || !pathName.endsWith(".csv")) {
            return "Lỗi: Định dạng file không đúng!";
        }

        List<String> namesInFile = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line = br.readLine(); // Bỏ qua Header
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(",");
                if (fields.length > 0) {
                    namesInFile.add(fields[0].trim());
                }
            }
        } catch (Exception e) {
            return "Lỗi khi đọc file: " + e.getMessage();
        }

        if (namesInFile.isEmpty()) {
            return "Cảnh báo: File không có dữ liệu.";
        }

        // 1. Lọc trùng ngay trong file (nếu file có 2 dòng giống hệt nhau)
        Set<String> uniqueNamesInFile = new LinkedHashSet<>(namesInFile);

        // 2. Kiểm tra những tên nào đã tồn tại trong DB (Chỉ gọi DB 1 lần)
        List<String> existingNamesInDB = departmentRepository.findExistingNames(new ArrayList<>(uniqueNamesInFile));

        // 3. Phân loại: Cái nào mới thì cho vào list để Insert, cái nào trùng thì báo cáo
        List<Department> newDepartments = new ArrayList<>();
        List<String> skippedNames = new ArrayList<>();

        for (String name : uniqueNamesInFile) {
            if (existingNamesInDB.contains(name)) {
                skippedNames.add(name);
            } else {
                newDepartments.add(new Department(name));
            }
        }

        // 4. Lưu vào DB bằng Batch (Chỉ gọi DB thêm 1 lần nữa)
        boolean isSuccess = true;
        if (!newDepartments.isEmpty()) {
            isSuccess = departmentRepository.createDepartments(newDepartments);
        }

        // 5. Trả về thông báo
        if (!isSuccess) return "Lỗi: Không thể lưu dữ liệu vào hệ thống.";

        StringBuilder result = new StringBuilder();
        result.append("Kết quả Import:\n");
        result.append("- Thành công: ").append(newDepartments.size()).append(" phòng ban.\n");
        if (!skippedNames.isEmpty()) {
            result.append("- Bỏ qua (đã tồn tại): ").append(skippedNames.size());
            if (skippedNames.size() <= 5) {
                result.append(" (").append(String.join(", ", skippedNames)).append(")");
            }
        }
        return result.toString();
    }

}
