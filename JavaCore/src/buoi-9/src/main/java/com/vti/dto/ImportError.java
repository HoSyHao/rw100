package com.vti.dto;

import javax.sound.midi.Soundbank;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ImportError {
    private List<String> rawFields;
    private String errorMessage;

    public ImportError(List<String> rawFields, String errorMessage) {
        this.rawFields = rawFields;
        this.errorMessage = errorMessage;
    }

    public List<String> getRawFields() {
        return rawFields;
    }

    public void setRawFields(List<String> rawFields) {
        this.rawFields = rawFields;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static void writeErrorsToCSV(String pathName, String header, List<ImportError> importErrors) {
        File inputFile = new File(pathName);
        String name = inputFile.getName();
        String parent = inputFile.getParent();
        String outputPath = (parent != null) ? parent + File.separator + "errs_" + name : "errs_" + name;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(header);
            bw.newLine();
            for (ImportError err : importErrors) {
                List<String> rawFields = err.getRawFields();
                String escapedMsg = err.getErrorMessage();
                if (escapedMsg != null) {
                    escapedMsg = escapedMsg.replace("\"", "\"\"");
                } else {
                    escapedMsg = "";
                }
                
                StringBuilder sb = new StringBuilder();
                if (rawFields != null) {
                    for (int i = 0; i < rawFields.size(); i++) {
                        String field = rawFields.get(i);
                        sb.append(field);
                        if (i < rawFields.size() - 1) {
                            sb.append(",");
                        }
                    }
                }
                sb.append(",\"").append(escapedMsg).append("\"");
                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Đã xuất lỗi ra file " + outputPath);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file báo cáo lỗi CSV: " + e.getMessage());
        }
    }
}
