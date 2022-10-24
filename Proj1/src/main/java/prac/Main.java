package prac;

import user.User;

public class Main {
    public static void main(String[] args) {
        String SQL = "insert into enroll values (\'"+User.getS_user_id()+"\',\'"+"hwang"+"\')";
        System.out.println(SQL);
    }
}
