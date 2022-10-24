package user;
import java.io.IOException;
import java.io.PrintWriter;

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
        if(new UserDAO().enroll(class_id_arr)){
            UserDAO.alertAndGo(response,"등록 성공","showClass.jsp");
        }else{
            UserDAO.alertAndGo(response,"등록 실패","showClass.jsp");
        }
    }

}
