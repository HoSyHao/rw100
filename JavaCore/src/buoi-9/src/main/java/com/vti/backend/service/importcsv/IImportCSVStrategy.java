package com.vti.backend.service.importcsv;

import com.vti.dto.ImportError;
import java.util.List;
import java.util.Map;

/**
 * Interface cho các nghiệp vụ cụ thể của từng loại import CSV.
 * @param <T> Kiểu của đối tượng Context (chứa dữ liệu tra cứu dưới DB)
 * @param <K> Kiểu của Entity Database (Account, Department)
 * @param <E> Kiểu của đối tượng DTO map với dòng CSV (AccountCSV, DepartmentCSV)
 */
public interface IImportCSVStrategy<T, K, E> {
    
    // Lấy số lượng cột dữ liệu mong muốn (để ghi đè/làm sạch header file lỗi)
    int getExpectedColumns();
    
    // Phân tích và validate dữ liệu thô từ CSV thành DTO
    E parseAndValidateRow(String[] fields, List<ImportError<E>> importErrors);
    
    // Lọc các dòng bị trùng lặp nội bộ ngay trong file CSV
    List<E> filterInternalDuplicates(List<E> rawList, List<ImportError<E>> importErrors);
    
    // Ánh xạ đối tượng DTO (E) sang thực thể Entity (K)
    K mapToEntity(E dto);
    
    // Khởi tạo đối tượng Context (T) bằng việc truy vấn dữ liệu cần thiết từ DB
    T buildValidationContext(List<K> entities);
    
    // Đối chiếu danh sách thực thể (K) với DB thông qua dữ liệu tra cứu trong Context (T)
    List<K> validateAgainstDatabase(
        List<K> entities,
        T context,
        List<ImportError<E>> importErrors,
        Map<K, E> entityToDtoMap
    );
    
    // Thực hiện lưu hàng loạt (Batch Insert) các thực thể hợp lệ vào DB
    void saveBatch(List<K> entities);
    
    // Tạo câu thông báo tóm tắt kết quả import
    String buildSummaryMessage(int total, int success, int skipped, int failed);
}
