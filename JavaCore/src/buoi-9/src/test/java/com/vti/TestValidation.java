package com.vti;

import junit.framework.TestCase;
import com.vti.backend.service.impl.AccountServiceImpl;
import com.vti.backend.service.impl.DepartmentServiceImpl;
import java.io.*;

public class TestValidation extends TestCase {

    public void testAccountImportValidation() throws Exception {
        // Create a temporary accounts CSV with format errors, length errors, duplicates
        File tempFile = new File("test_accounts.csv");
        try (PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
            pw.println("email,full_name,username,department_id,position_id");
            // Valid row
            pw.println("test1@gmail.com,Test One,testone,1,1");
            // Missing email
            pw.println(",Test Two,testtwo,1,1");
            // Invalid email format
            pw.println("invalid-email,Test Three,testthree,1,1");
            // Length limits exceeded (>100 characters)
            String longString = new String(new char[101]).replace('\0', 'a');
            pw.println("email_too_long_" + longString + "@gmail.com,Test Four,testfour,1,1");
            pw.println("testfive@gmail.com,Fullname_too_long_" + longString + ",testfive,1,1");
            pw.println("testsix@gmail.com,Test Six,username_too_long_" + longString + ",1,1");
            // Duplicate rows in file
            pw.println("dup@gmail.com,Dup One,dupone,1,1");
            pw.println("dup@gmail.com,Dup Two,dupone,1,1");
        }

        AccountServiceImpl service = new AccountServiceImpl();
        String result = service.importAccountCSV(tempFile.getAbsolutePath());
        System.out.println("--- Account Import Console Result ---");
        System.out.println(result);
        System.out.println("-------------------------------------");

        // Verify that errs_test_accounts.csv is created in the same folder
        File errFile = new File("errs_test_accounts.csv");
        assertTrue(errFile.exists());

        // Print content of error file
        System.out.println("--- Account Error CSV Content ---");
        try (BufferedReader br = new BufferedReader(new FileReader(errFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("-------------------------------------");

        // Clean up
        tempFile.delete();
        errFile.delete();
    }

    public void testDepartmentImportValidation() throws Exception {
        // Create a temporary departments CSV
        File tempFile = new File("test_departments.csv");
        try (PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
            pw.println("department_name");
            // Valid row
            pw.println("Phong Kinh Doanh");
            // Empty row
            pw.println("");
            // Length limit exceeded (>100 characters)
            String longString = new String(new char[101]).replace('\0', 'a');
            pw.println("Dep_too_long_" + longString);
            // Duplicate rows in file
            pw.println("Phong Nhan Su");
            pw.println("Phong Nhan Su");
        }

        DepartmentServiceImpl service = new DepartmentServiceImpl();
        String result = service.importDepartmentCSV(tempFile.getAbsolutePath());
        System.out.println("--- Department Import Console Result ---");
        System.out.println(result);
        System.out.println("-------------------------------------");

        // Verify that errs_test_departments.csv is created
        File errFile = new File("errs_test_departments.csv");
        assertTrue(errFile.exists());

        // Print content of error file
        System.out.println("--- Department Error CSV Content ---");
        try (BufferedReader br = new BufferedReader(new FileReader(errFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("-------------------------------------");

        // Clean up
        tempFile.delete();
        errFile.delete();
    }
}
