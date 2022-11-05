package user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/wishClass") // form tag의 action부분에서 호출 가능한 형태의 별명을 붙여준다
public class WishClass extends HttpServlet{
    public WishClass() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String[] class_id_arr = request.getParameterValues("class_id");

        if (class_id_arr.length > 1) { // problem solving
            UserDAO.alertAndGo(response, "1개 이상 담기 불가", null);
            return;
        }

        String class_id = class_id_arr[0];
        int result = new UserDAO().enrollClass(class_id,"wish_enroll");  // 희망수업의 경우 result는 0, 1만 가능

        // 결과에 따른 경고 창 생성 후  "enrollClass.jsp" 로 이동
        if (result == 1) {
            UserDAO.alertAndGo(response, "희망 수업에 담겼습니다", null);
        } else if (result == 0) {
            UserDAO.alertAndGo(response, "이미 담은 수업", null);
        }
    }
}
