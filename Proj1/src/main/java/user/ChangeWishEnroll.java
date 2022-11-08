package user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ChangeWishEnroll")
public class ChangeWishEnroll extends HttpServlet {
    public ChangeWishEnroll() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String[] change_class_id_arr = request.getParameterValues("class_id");
        if(new UserDAO(request.getSession()).change_enroll(change_class_id_arr,"wish_enroll")){
            UserDAO.alertAndGo(response,"삭제 성공","wishClass.jsp"); // url 을 null로 하지 않은 이유 : 변경사항이 업로드 되게 하기 위해서
        }else{
            UserDAO.alertAndGo(response,"삭제 실패","wishClass.jsp");
        }
    }
}
