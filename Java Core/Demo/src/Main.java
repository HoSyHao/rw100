public class Main {
    public static void main(String[] args) {
        Account account1 = new Account(
                1,
                "さようなら",
                "Hồ Sỹ Hào",
                Account.Gender.MALE,
                22,
                1.70f,
                72
        );

        System.out.println("=== THÔNG TIN LÚC VỪA TẠO ===");
        account1.printProfile();

        System.out.println("\n...Có người lạ đang cố tình hack tuổi thành -50...");
        account1.setAge(-50);

        System.out.println("\n...Giảng viên đang nhập điểm thi...");
        account1.updatePoints(7.5f, 8.0f);

        System.out.println("\n=== THÔNG TIN SAU KHI CÓ ĐIỂM ===");
        account1.printProfile();
    }
}
