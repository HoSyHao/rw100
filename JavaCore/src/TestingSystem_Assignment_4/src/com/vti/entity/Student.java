package TestingSystem_Assignment_4.src.com.vti.entity;

public class Student {
    private int id;
    private String name;
    private String hometown;
    private float score;

    private static int counter = 1;

    public Student(String name, String hometown) {
        this.id = counter++;
        this.name = name;
        this.hometown = hometown;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        if (score < 0 || score > 10) {
            System.out.println("Điểm không được < 0 và > 10");
            return;
        }

        this.score = score;
    }

    public void plusScore(float score) {
        float newScore = this.score + score;
        if (newScore < 0 || newScore > 10){
            System.out.println("Điểm không được < 0 và > 10");
            return;
        }
        this.score = newScore;
    }


    @Override
    public String toString() {
        String rank;
        if(score < 4.0){
            rank = "Yếu";
        } else if(score < 6.0){
            rank = "Trung Bình";
        } else if(score < 8.0){
            rank = "Khá";
        } else {
            rank = "Giỏi";
        }

        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hometown='" + hometown + '\'' +
                ", rank=" + rank + '\'' +
                ", score=" + score +
                '}';
    }
}
