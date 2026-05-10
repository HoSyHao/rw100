package buoi7.src.entity;

public class Department {
    private int departmentID;
    private String departmentName;

    public Department() {}
    public Department(int departmentID, String departmentName) {
        this.departmentName = departmentName;
        this.departmentID = departmentID;
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
