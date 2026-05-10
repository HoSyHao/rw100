package buoi7.src.entity;

import java.time.LocalDateTime;

public class Exam {
    private int examID;
    private String code;
    private String title;
    private CategoryQuestion category;
    private int duration;
    private Account creator;
    private LocalDateTime createDate;

    public Exam(int examID, String code, String title, CategoryQuestion category, int duration, Account creator, LocalDateTime createDate) {
        this.examID = examID;
        this.code = code;
        this.title = title;
        this.category = category;
        this.duration = duration;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryQuestion getCategory() {
        return category;
    }

    public void setCategory(CategoryQuestion category) {
        this.category = category;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
