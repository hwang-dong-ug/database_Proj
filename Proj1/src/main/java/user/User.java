package user;

public class User {
    private final static  String s_admin_id="admin";
    private final static  String s_admin_pwd="1234";

    public static String getS_admin_id() {
        return s_admin_id;
    }

    public static String getS_admin_pwd() {
        return s_admin_pwd;
    }


    private static String s_user_id;
    private static String s_user_pwd;
    private String UserID;
    private String UserPassword;
    private String UserName;
    private String UserGender;
    private String UserEmail;

    public static String getS_user_id() {
        return s_user_id;
    }

    public static void setS_user_id(String s_user_id) {
        User.s_user_id = s_user_id;
    }

    public static String getS_user_pwd() {
        return s_user_pwd;
    }

    public static void setS_user_pwd(String s_user_pwd) {
        User.s_user_pwd = s_user_pwd;
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
