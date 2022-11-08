package user;
public class TimeTable {
    String student_id;
    String userID;
    String userName;
    String class_name;
    String begin;
    String end;
    String day;

    public TimeTable(String student_id, String userID, String userName, String class_name, String begin, String end, String day) {
        this.student_id = student_id;
        this.userID = userID;
        this.userName = userName;
        this.class_name = class_name;
        this.begin = begin;
        this.end = end;
        this.day = day;
    }

//    public String[]

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

    public String getClass_name() {
        return class_name;
    }

    public void setClass_id(String class_name) {
        this.class_name = class_name;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }



}

