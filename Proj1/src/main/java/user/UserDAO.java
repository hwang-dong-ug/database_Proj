package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;


public class UserDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    HttpSession session;

    public UserDAO(HttpSession session)
    {
        try{
            this.session= session;
            String dbURL = "jdbc:mysql://localhost:3306/bbs?serverTimezone=Asia/Seoul";
            String dbID = "root";
            String dbPassword = "@zz060331";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//#########################공통
    //수강 할 수 있는 강의 조회
    public ArrayList<ClassLookUp> showClass(int class_id, String course_id, String class_name) {
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
                class_lookUp.setUser_count(rs.getInt(6));
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

    // student_id 를 입력하면 userID를 반환
    public String student_idToUserID(String student_id){
        String SQL = "select userID from user where student_id =? ";
        String userID =null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, student_id);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                userID=rs.getString(1);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return userID;
    }

    // userID 를 입력하면 student_id로 반환
    public String userIDToStudent_id(String userID){
        String SQL = "select student_id from user where userID =? ";
        String student_id =null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                student_id=rs.getString(1);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return student_id;
    }

//#########################USER
    //수강한 강의조회 //희망 강의 조회
    public ArrayList<ClassLookUp> showMyClass(String table){

        String SQL;
        if(table.equals("enroll")){
            SQL= "select * from user_class_look_up_extended where userID= ?";
        }else{
            SQL= "select * from user_wish_class_look_up_extended where userID= ?"; //wish_enroll으로 table이 오면
        }
        ArrayList<ClassLookUp> classesList = new ArrayList<ClassLookUp>();

        try{
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,(String) session.getAttribute("userID"));

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

    //수강 신청  //희망 바구니 담기
    public int enrollClass(String class_id,String table) { //status 1:등록 성공 0:등록 실패 -1:학점 초과 -2:정원 초과 -3:재수강 실패(성적) -4:재수강 성공
        int status=1; //성공으로 출발
        try {
            conn.setAutoCommit(false);              //transaction으로 처리 하기 위해서 auto commit을 꺼준다
            String SQL = "insert into "+ table +" values (? ,?)";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,(String) session.getAttribute("userID"));
            pstmt.setString(2,class_id);
            status = pstmt.executeUpdate();

            if(table.equals("enroll")){
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
                }
            }
            conn.commit();
            pstmt.close();

        } catch (SQLIntegrityConstraintViolationException e1) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            status=0;
            return status; //등록 실패시(동일한 정보) 0 가능
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
        String SQL = "select grade from user_credit where course_id=? and userID=?" ;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,course_id);
            pstmt.setString(2,(String) session.getAttribute("userID"));
            rs = pstmt.executeQuery();
            if(rs.next()){
                grade = gradeStringToDouble(rs.getString(1)); //grade를 숫자로 변환
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (grade<3.0){ //B0 미만(C+ 이하)인 경우 == 재수강 신청 가능
            status=1;
        }else{
            status=-1;
        }

        if(grade == -1){
            status=0;
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
            pstmt.setString(1,(String) session.getAttribute("userID"));
            rs = pstmt.executeQuery();
            rs.next();
            total_credit=rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return total_credit;
    }

    // 수강내역 삭제  //희망 내역 삭제
    public boolean change_enroll(String[] change_class_id_arr, String table) {
        int r=0;
        for (int i = 0; i < change_class_id_arr.length; i++) {
            String SQL;
            if(table.equals("enroll")){
                SQL= "delete from enroll where userID=? and class_id=?";
            }else{
                SQL= "delete from wish_enroll where userID=? and class_id=?";
            }
            try {
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1,(String) session.getAttribute("userID"));
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
            if(url == null){
                w.write("<script>alert('"+msg+"'); history.back();</script>");
            }else{
                w.write("<script>alert('"+msg+"');location.href='"+url+"';</script>");
            }

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
        if(userID.equals(User.getS_admin_id()) && userPassword.equals(User.getS_admin_pwd())){
            return 1;
        }
        String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; // password column
        try{

            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                if(rs.getString(1).equals(userPassword))
                {
                    return 1; //유저 로그인 성공
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
    	String SQL = "INSERT INTO USER(userID,userPassword,userName,userGender,userEmail,student_id) VALUES (?, ?, ?, ?, ?,?)";
    	try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user.getUserID()); //첫번째 물음표에 user.getUserID()
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserGender());
            pstmt.setString(5, user.getUserEmail());
            pstmt.setString(6, user.getStudent_id());
            return pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();// student table에 존재하지 않는 student_id 를 입력학거나 이미 존자해는 유저 아이디를 통해 가입할 경우 error 발생
            return -1; // 데이터베이스 오류
        }
    }

    //##################################관리자

    //olap
    public ArrayList<OLAP> showOLAP(){
        ArrayList<OLAP> olaps =new ArrayList<>();
        try {
            String SQL ="select * from olap";
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                OLAP olap = new OLAP(
                        rs.getString(1),
                        rs.getString(2)
                        );
                olaps.add(olap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return olaps;
    }

    //수강 허용
    public void allowClass(String class_id, String student_id){
        String userID = student_idToUserID(student_id);
        try {
            String SQL ="insert into enroll values(?,?)";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);
            pstmt.setString(2, class_id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //강의 증원
    public void classAddNum(String class_id,int add_num) {
        try {
            String SQL = "update class" +
                    " set person_max = person_max+ ?" +
                    " where class_id = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, add_num);
            pstmt.setString(2, class_id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //재학/휴학/자퇴 등 상태 변경
    public void changeState(String student_id, String state){
        try {
            String SQL = "update student" +
                    " set state = ?" +
                    " where student_id = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, state);
            pstmt.setString(2, student_id);
            pstmt.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //재학/휴학/자퇴 등 상태 조회 및 지도 교수 조회
    public ArrayList<String> stateAndLecturer(String student_id) {
        ArrayList<String> info= new ArrayList<>();
        try {
            String SQL = "select * from student_id_state_lecturer where student_id = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, student_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                info.add(rs.getString(2)); //userID
                info.add(rs.getString(3)); //userName
                info.add(rs.getString(4)); //lecturer_name
                info.add(rs.getString(5)); //state
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return info;
    }

    //시간표 생성
    public ArrayList<TimeTable> createTimeTable(String student_id){
        ArrayList<TimeTable> timeTables =new ArrayList<>();
        try {
            String SQL = "select * from student_id_time where student_id = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,student_id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                TimeTable timeTable = new TimeTable(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
                timeTables.add(timeTable);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return timeTables;
    }

    //성적 조회
    public ArrayList<Credit> lookUpCredit(String student_id){
        ArrayList<Credit> credits =new ArrayList<>();

        try {
            String SQL = "select * from student_id_credit where student_id = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,student_id); // course_id를 통해 검색
            rs = pstmt.executeQuery();
            while(rs.next()){
                Credit credit = new Credit(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
                credits.add(credit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return credits;
    }

    //새로운 강의 개설  //여기서부터!!!!!!!!!!!!!
    public int openClass(OpenClassContainer openClassContainer){ // status == -1 이면 room occupancy 보다 강좌 max_person이 큰 경우
        String class_id = openClassContainer.getClass_id();
        String course_id = openClassContainer.getCourse_id();
        String lecturer_name= openClassContainer.getLecturer_name();
        int person_max = Integer.parseInt(openClassContainer.getPerson_max());
        int room_id = Integer.parseInt(openClassContainer.getRoom_id());
        String begin = openClassContainer.getBegin();
        String end = openClassContainer.getEnd();
        String day = openClassContainer.getDay();

        int status=0;

        // #########part 1-3 필용한 정보 추출

        // part 1.
        String class_no= null;
        String name =null;
        int major_id = -1 ;
        int year= -1;
        int credit= -1;
        try {
            String SQL = "SELECT class_no," +
                    "name," +
                    "major_id," +
                    "year," +
                    "credit" +
                    " FROM class " +
                    "where course_id = ? limit 1";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,course_id); // course_id를 통해 검색
            rs = pstmt.executeQuery();
            while (rs.next()){ // rs가 null 일 때를 방지
                class_no = rs.getString(1);
                name = rs.getString(2);
                major_id = rs.getInt(3);
                year =rs.getInt(4);
                credit = rs.getInt(5);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // part 2.
        String lecturer_id =null;
        try {
            String SQL = "SELECT lecturer_id" +
                    " FROM lecturer " +
                    "where name = ? limit 1";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,lecturer_name); // lecturer_name를 통해 검색
            rs = pstmt.executeQuery();
            while (rs.next()){ // rs가 null 일 때를 방지
                lecturer_id = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //part 3.
        int occupancy =-1;
        try {
            String SQL = "SELECT occupancy" +
                    " FROM room " +
                    "where room_id = ? limit 1";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1,room_id); // room_id를 통해 검색
            rs = pstmt.executeQuery();
            while (rs.next()){ // rs가 null 일 때를 방지
                occupancy =rs.getInt(1);
                if(person_max>occupancy){
                    status=-1;                                              //  -1 == 수강정원이 방 수용 가능한 인원보다 큰 경우
                    return status;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // part 4.
        int begin_id = -1;
        int end_id = -1;

        try {
            String SQL = "SELECT time_at_id" +
                    " FROM time_at " +
                    "where time in (?,?) order by time_at_id asc";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,begin);
            pstmt.setString(2,end);
            rs = pstmt.executeQuery();

            rs.next();
            begin_id =Integer.parseInt(rs.getString(1));
            rs.next();
            end_id =Integer.parseInt(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //#######part 5-6 table에 정보를 insert

        //part 5. insert into class
        try {
            conn.setAutoCommit(false); //transaction을 사용해서 part5가 실패하면 part4도 취소
            String SQL = "insert into class values (?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,class_id);
            pstmt.setString(2,class_no);
            pstmt.setString(3,course_id);
            pstmt.setString(4,name);
            pstmt.setInt(5,major_id);
            pstmt.setInt(6,year);
            pstmt.setInt(7,credit);
            pstmt.setString(8,lecturer_id);
            pstmt.setInt(9,person_max);
            pstmt.setInt(10,2022);  // default 2022
            pstmt.setInt(11,room_id);
            pstmt.execute();

        } catch (SQLException e) {
            status= -2; // class table insert 실패
            return status;
        }

        // part 6.
        try {
            String SQL = "insert into time(class_id,begin_id,end_id,day) values (?,?,?,?)";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,class_id);
            pstmt.setInt(2,begin_id);
            pstmt.setInt(3,end_id);
            pstmt.setString(4,day);
            pstmt.execute();

            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            status = -3; //time table insert 실패
            return status;
        }

        return status;
    }

    //강의 삭제
    public boolean closeClass(String[] class_id_arr){
        for (int i = 0; i < class_id_arr.length; i++) {
            try {
                conn.setAutoCommit(true);

                String SQL= "delete from class where class_id=?"; // class를 삭제 하면 cascade 되어 있기 때문에 time 또한 같이 삭제
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1,class_id_arr[i]);
                pstmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

}
