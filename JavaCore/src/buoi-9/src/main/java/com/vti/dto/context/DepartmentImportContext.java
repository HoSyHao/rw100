package com.vti.dto.context;

import java.util.Set;

/**
 * Lớp ngữ cảnh chứa các thông tin tra cứu từ Database phục vụ cho việc đối chiếu
 * validate dữ liệu nhập của danh sách phòng ban (Department) trong lần import CSV.
 */
public class DepartmentImportContext {
    private final Set<String> existingNames;

    public DepartmentImportContext(Set<String> existingNames) {
        this.existingNames = existingNames;
    }

    public Set<String> getExistingNames() {
        return existingNames;
    }
}
