package user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ClassAddNum")
public class ClassAddNum extends HttpServlet {
    public ClassAddNum() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String class_id = request.getParameter("class_id");
        int add_num = Integer.parseInt(request.getParameter("add_num"));

        new UserDAO(request.getSession()).classAddNum(class_id,add_num);

        UserDAO.alertAndGo(response,"증원 완료", null);
    }
}