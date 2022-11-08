package user;

public class OLAP {
    String course_id;
    String average;

    public OLAP(String course_id, String average) {
        this.course_id = course_id;
        this.average = average;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
