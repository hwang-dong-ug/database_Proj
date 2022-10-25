package user;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public UserDAO()
    {
        try{
            String dbURL = "jdbc:mysql://localhost:3306/bbs?serverTimezone=Asia/Seoul";
            String dbID = "root";
            String dbPassword = "@zz060331";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    //show class (내가 만든)
    public ArrayList<ClassLookUp> showClass(int class_id, String course_id, String class_name)
    {
        String SQL = "SELECT * FROM class_look_up WHERE";
        ArrayList<ClassLookUp> classesList = new ArrayList<ClassLookUp>();
        try
        {
            if(class_id!=0){
                SQL+=(" class_id="+class_id+" and");
            }else{
                SQL+=" True and";
            }
            if(course_id!=null){
                SQL+=(" course_id=\'"+course_id+"\' and");
            }else{
                SQL+=" True and";
            }

            if(class_name!=null){
                SQL+=(" class_name=\'"+class_name+"\'");
            }else{
                SQL+=" True";
            }
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while(rs.next())
            {
                ClassLookUp class_lookUp = new ClassLookUp();
                class_lookUp.setClass_id(rs.getInt(1));
                class_lookUp.setCourse_id(rs.getString(2));
                class_lookUp.setClass_name(rs.getString(3));
                class_lookUp.setLecturer_name(rs.getString(4));
                class_lookUp.setPerson_max(rs.getInt(5));
                class_lookUp.setRoom_id(rs.getInt(6));
                class_lookUp.setBuilding_name(rs.getString(7));
                class_lookUp.setOpened(rs.getInt(8));
                class_lookUp.setBegin(rs.getString(9));
                class_lookUp.setEnd(rs.getString(10));
                class_lookUp.setDay(rs.getString(11));


                classesList.add(class_lookUp);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return classesList;
    }

    // show my class (내가 만든)
    public ArrayList<ClassLookUp> showMyClass(){
        String SQL = "select * from user_class_look_up where userID= ?";
        ArrayList<ClassLookUp> classesList = new ArrayList<ClassLookUp>();
        try{
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,User.getS_user_id());
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                ClassLookUp class_lookUp = new ClassLookUp();// 2부터 해야 userID는 제거된 화면을 볼 수 있다.
                class_lookUp.setClass_id(rs.getInt(2));
                class_lookUp.setCourse_id(rs.getString(3));
                class_lookUp.setClass_name(rs.getString(4));
                class_lookUp.setLecturer_name(rs.getString(5));
                class_lookUp.setPerson_max(rs.getInt(6));
                class_lookUp.setRoom_id(rs.getInt(7));
                class_lookUp.setBuilding_name(rs.getString(8));
                class_lookUp.setOpened(rs.getInt(9));
                class_lookUp.setBegin(rs.getString(10));
                class_lookUp.setEnd(rs.getString(11));
                class_lookUp.setDay(rs.getString(12));


                classesList.add(class_lookUp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classesList;
    }

    //enroll (내가 만든)
    public boolean enrollClass(String[] class_id_arr) {
        int r=0;
        for (int i = 0; i < class_id_arr.length; i++) {
            String SQL = "insert into enroll values (? ,?)";
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1,User.getS_user_id());
                pstmt.setString(2,class_id_arr[i]);
                r+=pstmt.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        }
        if(r>=1){
            return true;
        }else return false;
    }
    public boolean change_enroll(String[] change_class_id_arr) {
        int r=0;
        for (int i = 0; i < change_class_id_arr.length; i++) {
            String SQL = "delete from enroll where userID=? and class_id=?";
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1,User.getS_user_id());
                pstmt.setString(2,change_class_id_arr[i]);
                r+=pstmt.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        }
        if(r>=1){
            return true;
        }else return false;
    }

    //change (내가 만든)
    public boolean user_change(User old_user, User new_user){
        String SQL = "update user set userID= ?, userPassword= ?, userName= ?, userGender=?, userEmail=?" +
                "where userID = ? and userPassword = ? ;";


        try {
//            System.out.println(new_userId+" "+new_userPassword);
//            System.out.println(old_userId+" "+old_userPassword);

            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,new_user.getUserID());
            pstmt.setString(2,new_user.getUserPassword());
            pstmt.setString(3,new_user.getUserName());
            pstmt.setString(4,new_user.getUserGender());
            pstmt.setString(5,new_user.getUserEmail());

            pstmt.setString(6,old_user.getUserID());
            pstmt.setString(7,old_user.getUserPassword());
            int result = pstmt.executeUpdate();
            if(result>=1){
                return true;
            }else return false;
//            System.out.println(r);
        } catch (SQLException e) {
            return false;
        }
    }


    // 알림창
    public static void alertAndGo(HttpServletResponse response, String msg, String url) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('"+msg+"');location.href='"+url+"';</script>");
            w.flush();
            w.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public String test(String userID, String userPassword)
    {
        String test = userID + " " + userPassword;

        return test;
    }


    public int login(String userID, String userPassword)
    {
        String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; // password column
        try{

            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID); // ?에 input으로 들어온 userID
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                if(rs.getString(1).equals(userPassword)) //
                {
                    User.setS_user_id(userID);  //관리자도 여기서 userID
                    User.setS_user_pwd(userPassword); // userPasswor에 해당
                    return 1; //로그인 성공
                }
                else
                {
                    return 0; //비밀번호 불일치
                }
            }
            return -1; // 아이디가 없음
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2; // 데이터베이스 오류
    }
    
    public int join(User user)
    {
    	String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
    	try
    	{
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1, user.getUserID()); //첫번째 물음표에 user.getUserID()
    		pstmt.setString(2, user.getUserPassword());
    		pstmt.setString(3, user.getUserName());
    		pstmt.setString(4, user.getUserGender());
    		pstmt.setString(5, user.getUserEmail());
    		return pstmt.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return -1; // 데이터베이스 오류
    }


    public ArrayList<User> showAll()
    {
        String SQL = "SELECT * FROM USER";
        ArrayList<User> studentList = new ArrayList<User>();
        try
        {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while(rs.next())
            {
                User user = new User();
                user.setUserID(rs.getString(1));
                user.setUserPassword(rs.getString(2));
                user.setUserName(rs.getString(3));
                user.setUserGender(rs.getString(4));
                user.setUserEmail(rs.getString(5));
                studentList.add(user);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return studentList;
    }

}
