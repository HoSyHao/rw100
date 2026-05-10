package btvn_buoi5.src.entity;

public class Department {
    private int departmentID;
    private String departmentName;
    private static int AUTO_ID = 1;

    public Department() {}
    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.departmentID = AUTO_ID++;
    }


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentID=" + departmentID +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
