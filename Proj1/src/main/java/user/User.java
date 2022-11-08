package user;

public class User {
    private final static  String s_admin_id="admin"; //관리자는 아이디 따라로 constant 하게 지정
    private final static  String s_admin_pwd="1234"; // password도 따로 지정

    public static String getS_admin_id() {
        return s_admin_id;
    }

    public static String getS_admin_pwd() {
        return s_admin_pwd;
    }

    private String UserID;
    private String UserPassword;
    private String UserName;

    private String student_id;
    private String UserGender;
    private String UserEmail;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserGender() {
        return UserGender;
    }

    public void setUserGender(String userGender) {
        UserGender = userGender;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}
