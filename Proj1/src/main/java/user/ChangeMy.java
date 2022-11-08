package user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ChangeMy")
public class ChangeMy extends HttpServlet {
    public ChangeMy() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        User old_user=new User();
        User new_user=new User();

        String old_userID =request.getParameter("old_userID");
        String old_userPassword= request.getParameter("old_userPassword");
        String userID = (String) session.getAttribute("userID");
        String userPassword = (String) session.getAttribute("userPassword");
        if(old_userID.equals(userID) && old_userPassword.equals(userPassword)){
            old_user.setUserID(old_userID);
            old_user.setUserPassword(old_userPassword);


            new_user.setUserID(request.getParameter("new_userID"));
            new_user.setUserPassword(request.getParameter("new_userPassword"));
            new_user.setUserName(request.getParameter("new_userName"));
            new_user.setUserGender(request.getParameter("new_userGender"));
            new_user.setUserEmail(request.getParameter("new_userEmail"));

            boolean result =new UserDAO(session).user_change(old_user,new_user);
            if(result){
                session.setAttribute("userID",new_user.getUserID());
                session.setAttribute("userPassword",new_user.getUserPassword());
                UserDAO.alertAndGo(response,"변경성공",null);
            }else{
                UserDAO.alertAndGo(response,"변경실패",null);
            }
        }else{
            UserDAO.alertAndGo(response,"필수정보 오류",null);
        }



    }
}
