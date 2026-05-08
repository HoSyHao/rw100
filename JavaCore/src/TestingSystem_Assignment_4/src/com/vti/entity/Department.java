package TestingSystem_Assignment_4.src.com.vti.entity;

public class Department {
    private int departmentID;
    private String departmentName;

//    Question 1:
//    Tạo constructor cho department:
//    a)	không có parameters
    public Department() {}
    //    b)	Có 1 parameter là nameDepartment và default id của Department = 0
//    Khởi tạo 1 Object với mỗi constructor ở trên
    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.departmentID = 0;
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
