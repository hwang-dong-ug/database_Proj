package user;

public class OpenClassContainer {
    private String class_id;
    private String course_id;
    private String lecturer_name;
    private String person_max;
    private String room_id;
    private String begin;
    private String end;

    private String day;

    public OpenClassContainer(String class_id, String course_id, String lecturer_name, String person_max, String room_id, String begin, String end, String day) {
        this.class_id = class_id;
        this.course_id = course_id;
        this.lecturer_name = lecturer_name;
        this.person_max = person_max;
        this.room_id = room_id;
        this.begin = begin;
        this.end = end;
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getLecturer_name() {
        return lecturer_name;
    }

    public void setLecturer_name(String lecturer_name) {
        this.lecturer_name = lecturer_name;
    }

    public String getPerson_max() {
        return person_max;
    }

    public void setPerson_max(String person_max) {
        this.person_max = person_max;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
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
}
