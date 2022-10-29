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


    //수강 할 수 있는 강의 조회
    public ArrayList<ClassLookUp> showClass(int class_id, String course_id, String class_name)
    {
        String SQL = "SELECT * FROM class_look_up_extended WHERE";
        ArrayList<ClassLookUp> classesList = new ArrayList<ClassLookUp>();
        try
        {
            if(class_id!=0){
                SQL+=(" class_id="+class_id+" and");
            }else{
                SQL+=" True and";
            }
            if(course_id!=null){
                SQL+=(" course_id=\'"+course_id+"\'"); // 뒤에 and
            }else{
                SQL+=" True"; // 뒤에 and
            }

//            if(class_name!=null){
//                SQL+=(" class_name=\'"+class_name+"\'");
//            }else{
//                SQL+=" True";
//            }
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while(rs.next())
            {
                if(class_name == null) {

                } else if (rs.getString(3).contains(class_name)) {

                }else{
                    continue;
                }
                ClassLookUp class_lookUp = new ClassLookUp();
                class_lookUp.setClass_id(rs.getInt(1));
                class_lookUp.setCourse_id(rs.getString(2));
                class_lookUp.setClass_name(rs.getString(3));
                class_lookUp.setLecturer_name(rs.getString(4));
                class_lookUp.setPerson_max(rs.getInt(5));
//                if(rs.getInt(6)==0){
//                    class_lookUp.setUser_count(0);
//                }else{
                class_lookUp.setUser_count(rs.getInt(6));
//                }
                class_lookUp.setCredit(rs.getInt(7));
                class_lookUp.setRoom_id(rs.getInt(8));
                class_lookUp.setBuilding_name(rs.getString(9));
                class_lookUp.setOpened(rs.getInt(10));
                class_lookUp.setBegin(rs.getString(11));
                class_lookUp.setEnd(rs.getString(12));
                class_lookUp.setDay(rs.getString(13));
                classesList.add(class_lookUp);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return classesList;
    }

    //희망 강의 조회
    public ArrayList<ClassLookUp> showWishClass(){
        String SQL = "select * from user_class_look_up_extended where userID= ?";
        ArrayList<ClassLookUp> classesList = new ArrayList<ClassLookUp>();
//#################################################### 여기 수정 ############################
        return classesList;
    }


    //수강한 강의조회
    public ArrayList<ClassLookUp> showMyClass(){
        String SQL = "select * from user_class_look_up_extended where userID= ?";
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
                class_lookUp.setUser_count(rs.getInt(7));
                class_lookUp.setCredit(rs.getInt(8));
                class_lookUp.setRoom_id(rs.getInt(9));
                class_lookUp.setBuilding_name(rs.getString(10));
                class_lookUp.setOpened(rs.getInt(11));
                class_lookUp.setBegin(rs.getString(12));
                class_lookUp.setEnd(rs.getString(13));
                class_lookUp.setDay(rs.getString(14));

                classesList.add(class_lookUp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classesList;
    }

    //수강 신청
    public int enrollClass(String class_id) { //status 1:등록 성공 0:등록 실패 -1:학점 초과 -2:정원 초과 -3:재수강 실패(성적) -4:재수강 성공
        int status=1; //성공으로 출발
        try {
            conn.setAutoCommit(false);
            String SQL = "insert into enroll values (? ,?)";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,User.getS_user_id());
            pstmt.setString(2,class_id);
            status = pstmt.executeUpdate();

            int reEnrollStatus = checkReEnroll(class_id);

            if(!checkCreditToEnroll()) {
                status= -1;
                throw new SQLException(); // rollback하는 예외처리로 넘아간다.
            } else if (!checkMaxPersonToEnroll(class_id)) {
                status = -2;
                throw new SQLException();

            } else if (reEnrollStatus ==-1) { // 재수강 실패
                status = -3;
                throw new SQLException();
            } else {
                if (reEnrollStatus == 1)// 재수강 성공
                    status = -4;
                conn.commit();
                pstmt.close();
            }
        } catch (SQLIntegrityConstraintViolationException e1) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            status=0;
            return status; //등록 실패시 0가능
        }catch (SQLException e2){
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return status; //세부 조건 실패시  -1,-2,-3가능
        }

        return status; // 정상시      1,-4가능
    }
    //재수강 조건
    public int checkReEnroll(String class_id){
        int status = 0; // status 0 == 재수강 아님 / status 1 == 재수강 / status -1 == 재수강 실패
        double grade=-1;
        String course_id = classIDToCourseID(class_id);  // class_id에 해당하는 course_id를 구하고
        String SQL = "select grade from user_grade where course_id=? and userID=?" ;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,course_id);
            pstmt.setString(2,User.getS_user_id());
            rs = pstmt.executeQuery();
            if(rs.next()){
                grade = gradeStringToDouble(rs.getString(1)); //grade를 숫자로 변환
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(grade == -1){
            status=0;
        }else if (grade<3.0){ //B0 미만(C+ 이하)인 경우 == 재수강 신청 가능
            status=1;
        }else{
            status=-1;
        }
        return status;
    }

    //성적을 문자에서 숫자로 변환
    public double gradeStringToDouble(String grade){
        switch (grade){
            case "A+":
                return 4.5;
            case "A0":
                return 4.0;
            case "B+":
                return 3.5;
            case "B0":
                return 3.0;
            case "C+":
                return 2.5;
            case "C0":
                return 2.0;
            case "F":
                return 0;
            default:
                return -1;
        }
    }

    // class_id에 해당하는 course_id
    public String classIDToCourseID(String class_id){
        String course_id;
        String SQL = "select course_id from class_look_up where class_id=?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,class_id);
            rs = pstmt.executeQuery();
            rs.next();
            course_id=rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course_id;
    }

    //수강 신청 정원 조건
    public boolean checkMaxPersonToEnroll(String class_id){
        int person_max=0,user_count=0;
        String SQL = "select  * from class_person_max_and_user_count where class_id=?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,class_id);
            rs = pstmt.executeQuery();
            rs.next();
            person_max=rs.getInt(2);
            user_count=rs.getInt(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(person_max>= user_count){
            return true;
        }else{
            return false;
        }
    }

    //수강 신청 시간 조건
    public boolean checkTimeToEnroll(String class_id){
        // 만들기
        return true;
    }

    //수강 신청 학점조건
    public boolean checkCreditToEnroll(){
        int total_credit=totalCredit();
        if(total_credit<=18){
//            System.out.println("등록 성공");
            return true;
        }else{
//            System.out.println("등록 실패");
            return false;
        }
    }
    //학점 합계 죄회
    public int totalCredit(){
        String SQL = "select sum(credit) from user_class_look_up where userID= ?";
        int total_credit=0;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,User.getS_user_id());
            rs = pstmt.executeQuery();
            rs.next();
            total_credit=rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return total_credit;
    }

    // 수강내역 삭제
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

    //유저 정보 변경
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
