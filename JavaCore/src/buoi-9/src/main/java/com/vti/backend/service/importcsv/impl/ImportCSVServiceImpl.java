package com.vti.backend.service.importcsv.impl;

import com.vti.backend.service.importcsv.IImportCSVService;
import com.vti.backend.service.importcsv.IImportCSVStrategy;
import com.vti.dto.ImportError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class ImportCSVServiceImpl<T, K, E> implements IImportCSVService<T, K, E> {

    @Override
    public String importCSV(String pathName, IImportCSVStrategy<T, K, E> strategy) {
        // 1. Kiểm tra sự tồn tại của file nguồn
        File file = new File(pathName);
        if (!file.exists()) {
            return "File không tồn tại.";
        }

        // 2. Kiểm tra phần mở rộng định dạng file phải là .csv
        if (pathName == null || !pathName.endsWith(".csv")) {
            return "Lỗi: Định dạng file không đúng!";
        }

        List<E> rawDTOList = new ArrayList<>();
        List<ImportError<E>> importErrors = new ArrayList<>();
        
        // Map dùng để ánh xạ giữa thực thể Entity (K) và DTO gốc (E) 
        // để khi đối chiếu DB phát hiện lỗi thì biết chính xác đối tượng DTO nào bị lỗi để ghi file lỗi.
        Map<K, E> entityToDtoMap = new IdentityHashMap<>();

        int totalInputLines = 0;
        int parseErrorCount = 0;  
        int internalDupCount = 0;
        int dbErrorCount = 0;

        String headerLine = "";

        // PHASE 1: Đọc file CSV và thực hiện validate định dạng thô từng dòng
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            headerLine = br.readLine(); // Đọc dòng Header
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Bỏ qua dòng trống
                totalInputLines++;
                String[] fields = line.split(",", -1);

                // Gọi chiến lược cụ thể để validate định dạng dòng
                E dto = strategy.parseAndValidateRow(fields, importErrors);
                if (dto == null) {
                    parseErrorCount++;
                    continue; // Dòng lỗi định dạng, bỏ qua và chuyển sang dòng tiếp theo
                }
                rawDTOList.add(dto);
            }
        } catch (Exception e) {
            return "Lỗi đọc file: " + e.getMessage();
        }

        // Nếu file không có dòng dữ liệu hợp lệ nào
        if (rawDTOList.isEmpty()) {
            ImportError.writeErrorsToCSV(pathName, headerLine, importErrors, strategy.getExpectedColumns());
            return strategy.buildSummaryMessage(totalInputLines, 0, parseErrorCount, 0);
        }

        // PHASE 2: Lọc trùng lặp dữ liệu nội bộ ngay trong file CSV
        List<E> filteredDTOList = strategy.filterInternalDuplicates(rawDTOList, importErrors);
        internalDupCount = rawDTOList.size() - filteredDTOList.size();

        // Nếu sau khi lọc trùng lặp file không còn bản ghi nào
        if (filteredDTOList.isEmpty()) {
            ImportError.writeErrorsToCSV(pathName, headerLine, importErrors, strategy.getExpectedColumns());
            return strategy.buildSummaryMessage(totalInputLines, 0, parseErrorCount + internalDupCount, 0);
        }

        // Ánh xạ từ DTO (E) sang Entity Database (K)
        List<K> entities = new ArrayList<>();
        filteredDTOList.forEach(dto -> {
            K entity = strategy.mapToEntity(dto);
            entities.add(entity);
            entityToDtoMap.put(entity, dto);
        });

        // PHASE 3: Truy vấn một lần từ DB dựng đối tượng Context (T) để validate đối chiếu
        T validationContext = strategy.buildValidationContext(entities);

        // PHASE 4: Validate đối chiếu với Database sử dụng Context dữ liệu (T)
        List<K> finalEntitiesToInsert = strategy.validateAgainstDatabase(
            entities, validationContext, importErrors, entityToDtoMap
        );
        dbErrorCount = entities.size() - finalEntitiesToInsert.size();

        // PHASE 5: Thực hiện batch insert lưu các bản ghi hoàn toàn hợp lệ xuống DB
        if (!finalEntitiesToInsert.isEmpty()) {
            strategy.saveBatch(finalEntitiesToInsert);
        }

        // Ghi báo cáo danh sách các bản ghi bị lỗi ra file CSV
        if (!importErrors.isEmpty()) {
            ImportError.writeErrorsToCSV(pathName, headerLine, importErrors, strategy.getExpectedColumns());
        }

        int successCount = finalEntitiesToInsert.size();
        int skippedCount = parseErrorCount + internalDupCount;
        int failedCount = dbErrorCount;

        return strategy.buildSummaryMessage(totalInputLines, successCount, skippedCount, failedCount);
    }
}
