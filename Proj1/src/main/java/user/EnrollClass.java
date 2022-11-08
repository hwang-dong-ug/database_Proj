package user;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/enrollClass") // form tag의 action부분에서 호출 가능한 형태의 별명을 붙여준다
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
            UserDAO.alertAndGo(response,"1개 이상 수강 신청 불가",null); // 1개 이상의 수강 신청을 하면 등록 불가!!
            return;
        }

        String class_id = class_id_arr[0];
        int result =new UserDAO(request.getSession()).enrollClass(class_id,"enroll");  // 여기서 나온 결과에 따라 조건을 처리한다


        // 결과에 따른 경고 창 생성 후  "enrollClass.jsp" 로 이동
        if(result==1){
            UserDAO.alertAndGo(response,"강의 신청 성공",null);
        }else if(result == 0){
            UserDAO.alertAndGo(response,"중복된 수강 신청",null);
        }else if(result == -1){
            UserDAO.alertAndGo(response,"최대 학점 초과!",null);
        }else if(result == -2){
            UserDAO.alertAndGo(response,"최대 정원 초과!",null);
        } else if (result == -3) {
            UserDAO.alertAndGo(response,"지난 성적 B0이상으로 재수강 불가",null);
        } else if (result == -4){
            UserDAO.alertAndGo(response,"재수강 입니다",null);
            return;
        }
    }

}
