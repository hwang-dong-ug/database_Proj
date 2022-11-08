package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/openClass") // form tag의 action부분에서 호출 가능한 형태의 별명을 붙여준다
public class OpenClass extends HttpServlet{

    public OpenClass() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String class_id = request.getParameter("class_id");
        String course_id = request.getParameter("course_id");
        String lecturer_name = request.getParameter("lecturer_name");
        String person_max = request.getParameter("person_max");
        String room_id = request.getParameter("room_id");
        String begin = request.getParameter("begin");
        String end = request.getParameter("end");
        String day = request.getParameter("day");

        if(class_id.equals("") ||
                course_id.equals("")||
        lecturer_name.equals("")||
        person_max.equals("")||
        room_id.equals("")||
        day.equals("none")){
            UserDAO.alertAndGo(response,"모든칸을 채워주세요","openCloseClass.jsp");
            return;
        }



        OpenClassContainer openClassContainer =new OpenClassContainer(
                class_id,
                course_id,
                lecturer_name,
                person_max,
                room_id,
                begin,
                end,
                day
        );

        int result = new UserDAO(request.getSession()).openClass(openClassContainer);
        if(result == -1){
            UserDAO.alertAndGo(response,"개설 실패 (occupancy 촤과한 max_person)","openCloseClass.jsp");

        } else if (result == -2) {
            UserDAO.alertAndGo(response,"개설 실패 (class table 오류)","openCloseClass.jsp");

        } else if (result== -3) {
            UserDAO.alertAndGo(response,"개설 실패 (time table 오류)","openCloseClass.jsp");

        } else if (result== -4) {
            UserDAO.alertAndGo(response,"개설 실패 (course_id 오류!!! 새로운 class_id는 가능하나 course는 기존에 있는 course_id를 입력하십시오.)","openCloseClass.jsp");
        } else if (result== -5) {
            UserDAO.alertAndGo(response,"개설 실패 (존재하지 않는 교수 이름)","openCloseClass.jsp");
        } else if (result== -6) {
            UserDAO.alertAndGo(response,"개설 실패 (존재하지 않는 room_id)","openCloseClass.jsp");
        }
        else {
            UserDAO.alertAndGo(response,"개설 성공","openCloseClass.jsp");
        }
    }

}
