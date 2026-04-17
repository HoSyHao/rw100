import java.time.LocalDateTime;
import java.util.Arrays;

public class Account {
    // =========================================================
    // 1. THUỘC TÍNH (Luôn luôn private)
    // =========================================================
    private int id;
    private String userName;
    private String fullName;
    private Gender gender;
    private int age;
    private float height;
    private float weight;
    private float[] points;
    private boolean isPass;
    private LocalDateTime createDate;

    public enum Gender {
        MALE, FEMALE, OTHER;
    }

    // =========================================================
    // 2. CONSTRUCTOR (Hàm khởi tạo)
    // =========================================================
    public Account(int id, String userName, String fullName, Gender gender, int age, float height, float weight) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.points = new float[2]; // Mặc định tạo mảng 2 phần tử (Lý thuyết, Thực hành)
        this.isPass = false;
        this.createDate = LocalDateTime.now(); // Tự động lấy giờ hệ thống
    }

    // =========================================================
    // 3. GETTERS & SETTERS (Trạm kiểm soát an ninh)
    // =========================================================

    // ID và CreateDate CHỈ CÓ GETTER, KHÔNG CÓ SETTER
    public int getId() {
        return id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    // Các biến khác có cả Getter và Setter (kèm kiểm tra logic)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (userName != null && !userName.trim().isEmpty()) {
            this.userName = userName;
        } else {
            System.out.println("Lỗi: Username không được để trống!");
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName != null && !fullName.trim().isEmpty()) {
            this.fullName = fullName;
        } else {
            System.out.println("Lỗi: Họ tên không được để trống!");
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("Lỗi: Tuổi phải từ 1 đến 150!");
        }
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        if (height > 0) {
            this.height = height;
        } else {
            System.out.println("Lỗi: Chiều cao phải lớn hơn 0!");
        }
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            System.out.println("Lỗi: Cân nặng phải lớn hơn 0!");
        }
    }

    public float[] getPoints() {
        return points;
    }

    public boolean isPass() { // Với biến boolean, Getter thường được đặt tên là is... thay vì get...
        return isPass;
    }

    // =========================================================
    // 4. METHODS
    // =========================================================

    // Cập nhật điểm và tự động xét qua môn
    public void updatePoints(float diemLyThuyet, float diemThucHanh) {
        this.points[0] = diemLyThuyet;
        this.points[1] = diemThucHanh;

        // Tính trung bình
        float trungBinh = (diemLyThuyet + diemThucHanh) / 2;
        this.isPass = trungBinh >= 5.0f; // Viết tắt của lệnh if-else
    }

    // Tính chỉ số BMI
    public float calculateBMI() {
        if (height <= 0) return 0;
        return weight / (height * height);
    }

    // In toàn bộ hồ sơ
    public void printProfile() {
        System.out.println("\n--- HỒ SƠ TÀI KHOẢN ---");
        System.out.println("ID: " + this.id);
        System.out.println("Username: " + this.userName);
        System.out.println("Họ tên: " + this.fullName);
        System.out.println("Giới tính: " + this.gender);
        System.out.println("Tuổi: " + this.age);
        System.out.println("Chiều cao: " + this.height + "m");
        System.out.println("Cân nặng: " + this.weight + "kg");
        System.out.printf("Chỉ số BMI: %.2f\n", this.calculateBMI());
        System.out.println("Bảng điểm: " + Arrays.toString(this.points));
        System.out.println("Trạng thái: " + (this.isPass ? "Đã qua môn" : "Chưa qua môn"));
        System.out.println("Ngày tạo: " + this.createDate);
        System.out.println("-----------------------");
    }
}