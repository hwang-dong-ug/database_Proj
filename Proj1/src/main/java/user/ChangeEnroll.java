package user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ChangeEnroll")
public class ChangeEnroll extends HttpServlet {
    public ChangeEnroll() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String[] change_class_id_arr = request.getParameterValues("class_id");
        if(new UserDAO().change_enroll(change_class_id_arr,"enroll")){
            UserDAO.alertAndGo(response,"삭제 성공","changeEnroll.jsp");
        }else{
            UserDAO.alertAndGo(response,"삭제 실패","changeEnroll.jsp");
        }


    }
}
