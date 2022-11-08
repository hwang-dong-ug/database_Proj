package user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ChangeState")
public class ChangeState extends HttpServlet{
    public ChangeState() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String state = request.getParameter("state");
        String student_id =request.getParameter("student_id");
        new UserDAO(request.getSession()).changeState(student_id,state);

        UserDAO.alertAndGo(response,state+"로 변경 되었습니다. 이전 페이지로 이동 합니다!","changeAll.jsp");
    }
}
