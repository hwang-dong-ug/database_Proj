package user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AllowClass")
public class AllowClass extends HttpServlet {
    public AllowClass() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String class_id = request.getParameter("class_id");
        String student_id = request.getParameter("student_id");
        int result = new UserDAO(request.getSession()).allowClass(class_id, student_id);

        if (result == 0) UserDAO.alertAndGo(response, "수강허용 완료", null);
        else if (result ==-1) {
            UserDAO.alertAndGo(response, "수강 허용 실패 잘못된 student_id ", null);
        }
    }

}