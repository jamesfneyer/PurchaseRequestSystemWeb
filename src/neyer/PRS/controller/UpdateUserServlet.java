package neyer.PRS.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neyer.PRS.business.Users;
import neyer.PRS.dbutils.DBException;
import neyer.PRS.javautil.DAOFactory;
import neyer.PRS.users.UsersDAO;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	static UsersDAO usersDB = DAOFactory.getUsersDAO();
	private static final long serialVersionUID = 1L;
	static String url = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		url = "/updateUser.jsp";
		String action = request.getParameter("action");
		if (action == null) {
			action = "updateUser"; // default action
		}
		if (action.equals("updateUser")) {
			url = "/updateUser.jsp"; // the "login" page
		} else if (action.equals("updatedUser")) {
			String username = request.getParameter("username");
			try {
				Users user = usersDB.getUser(username);
				if(user != null){
					String password = request.getParameter("password");
					String firstName = request.getParameter("firstName");
					String lastName = request.getParameter("lastName");
					String phone = request.getParameter("phone");
					String email = request.getParameter("email");
					String role = request.getParameter("role");
					if(!role.equals("")){
					int roleID = 0;
					if(role.equals("employee"))
						roleID = 1;
					else if(role.equals("manager"))
						roleID = 2;
					else if(role.equals("administrator"))
						roleID = 3;
					user.setRoleID(roleID);
					}
					if(!password.equals(""))
						user.setPassword(password);
					if(!firstName.equals(""))
						user.setFirstName(firstName);
					if(!lastName.equals(""))
						user.setLastName(lastName);
					if(!phone.equals(""))
						user.setPhone(phone);
					if(!email.equals(""))
						user.setEmail(email);
					
					usersDB.updateUser(user);
					url = "/UsersServlet";
				}
				else{
					url = "/updateUser.jsp";
				}					
			} catch (DBException e) {
				e.printStackTrace();
			}
			
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
		
	}

}
