package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/closeClass") // form tag의 action부분에서 호출 가능한 형태의 별명을 붙여준다
public class CloseClass extends HttpServlet{

    public CloseClass() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String[] class_id_arr = request.getParameterValues("class_id");
        boolean result = new UserDAO(request.getSession()).closeClass(class_id_arr);
        if(result)
            UserDAO.alertAndGo(response,"삭제 성공","openCloseClass.jsp");
    }

}
