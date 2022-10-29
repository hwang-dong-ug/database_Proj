package user;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/enrollClass")
public class EnrollClass extends HttpServlet{

    public EnrollClass() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String[] class_id_arr = request.getParameterValues("class_id");

        if(class_id_arr.length>1){ // problem solving
            UserDAO.alertAndGo(response,"1개 이상 수강 신청 불가","enrollClass.jsp"); // 1개 이상의 수강 신청을 하면 등록 불가!!
            return;
        }

        String class_id = class_id_arr[0];
        int result =new UserDAO().enrollClass(class_id);

        if(result==1){
            UserDAO.alertAndGo(response,"강의 신청 성공","enrollClass.jsp");
        }else if(result == 0){
            UserDAO.alertAndGo(response,"중복된 수강 신청","enrollClass.jsp");
        }else if(result == -1){
            UserDAO.alertAndGo(response,"최대 학점 초과!","enrollClass.jsp");
        }else if(result == -2){
            UserDAO.alertAndGo(response,"최대 정원 초과!","enrollClass.jsp");
        } else if (result == -3) {
            UserDAO.alertAndGo(response,"지난 성적 B0이상으로 재수강 불가","enrollClass.jsp");
        } else if (result == -4){
            UserDAO.alertAndGo(response,"재수강 입니다","enrollClass.jsp");
            return;
        }
    }

}
