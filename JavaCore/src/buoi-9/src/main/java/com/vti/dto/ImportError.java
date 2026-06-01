package com.vti.dto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ImportError<T> {
    private T data;
    private String errorMessage;

    public ImportError(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static <T> void writeErrorsToCSV(String pathName, String headerLine, List<ImportError<T>> importErrors, int expectedCols) {
        File inputFile = new File(pathName);
        String name = inputFile.getName();
        String parent = inputFile.getParent();
        
        String outputPath;
        if (name.toLowerCase().startsWith("errs_")) {
            outputPath = pathName;
        } else {
            outputPath = (parent != null) ? parent + File.separator + "errs_" + name : "errs_" + name;
        }

        // Tạo dòng Header sạch cho file báo cáo lỗi (chỉ giữ lại expectedCols cột dữ liệu đầu và thêm cột error_message ở cuối)
        String cleanHeader = headerLine;
        if (headerLine != null && !headerLine.trim().isEmpty()) {
            String[] headerCols = headerLine.split(",", -1);
            if (headerCols.length >= expectedCols) {
                cleanHeader = String.join(",", Arrays.copyOfRange(headerCols, 0, expectedCols));
            } else {
                cleanHeader = headerLine.trim();
            }
        }
        String errorHeader = cleanHeader + ",error_message";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(errorHeader);
            bw.newLine();
            importErrors.forEach(err -> {
                try {
                    T rowData = err.getData();
                    String escapedMsg = err.getErrorMessage();
                    if (escapedMsg != null) {
                        escapedMsg = escapedMsg.replace("\"", "\"\"");
                    } else {
                        escapedMsg = "";
                    }
                    
                    StringBuilder sb = new StringBuilder();
                    if (rowData != null) {
                        Field[] fields = rowData.getClass().getDeclaredFields();
                        for (int i = 0; i < fields.length; i++) {
                            fields[i].setAccessible(true);
                            Object value = fields[i].get(rowData);
                            String valStr = (value != null) ? value.toString() : "";
                            if (valStr.contains(",")) {
                                valStr = "\"" + valStr.replace("\"", "\"\"") + "\"";
                            }
                            sb.append(valStr);
                            if (i < fields.length - 1) {
                                sb.append(",");
                            }
                        }
                    }
                    sb.append(",\"").append(escapedMsg).append("\"");
                    bw.write(sb.toString());
                    bw.newLine();
                } catch (Exception e) {
                    // Nếu một dòng lỗi gây exception khi ghi, ghi lại error message để debug và tiếp tục với dòng tiếp theo
                    System.err.println("Lỗi khi xử lý import error: " + e.getMessage());
                }
            });
            System.out.println("Đã xuất lỗi ra file " + outputPath);
        } catch (Exception e) {
            System.err.println("Lỗi khi ghi file báo cáo lỗi CSV: " + e.getMessage());
        }
    }
}
