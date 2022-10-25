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
        if(new UserDAO().enrollClass(class_id_arr)){
            UserDAO.alertAndGo(response,"신청 성공","enrollClass.jsp");
        }else{
            UserDAO.alertAndGo(response,"신청 실패","enrollClass.jsp");
        }
    }

}
