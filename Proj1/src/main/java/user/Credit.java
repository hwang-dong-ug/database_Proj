package user;

public class Credit {
    String student_id;
    String userID;
    String userName;
    String course_id;
    String year;
    String grade;

    public Credit(String student_id, String userID, String userName, String course_id, String year, String grade) {
        this.student_id = student_id;
        this.userID = userID;
        this.userName = userName;
        this.course_id = course_id;
        this.year = year;
        this.grade = grade;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
