package com.vti.backend.service.importcsv;

/**
 * Interface cho luồng xử lý import CSV chung sử dụng Generic và Strategy Pattern.
 * @param <T> Kiểu của đối tượng Context (chứa dữ liệu tra cứu dưới DB)
 * @param <K> Kiểu của Entity Database (Account, Department)
 * @param <E> Kiểu của đối tượng DTO map với dòng CSV (AccountCSV, DepartmentCSV)
 */
public interface IImportCSVService<T, K, E> {
    
    /**
     * Phương thức thực hiện import file CSV một cách tổng quát.
     * @param pathName Đường dẫn tới file CSV cần import
     * @param strategy Đối tượng nghiệp vụ cụ thể cho thực thể đang import
     * @return Chuỗi thông báo tổng kết kết quả import
     */
    String importCSV(String pathName, IImportCSVStrategy<T, K, E> strategy);
}
