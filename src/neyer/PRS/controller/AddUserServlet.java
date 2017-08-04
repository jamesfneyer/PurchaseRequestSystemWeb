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
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	static UsersDAO usersDB = DAOFactory.getUsersDAO();
	private static final long serialVersionUID = 1L;
	static String url = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		url = "/addUser.jsp";
		String action = request.getParameter("action");
		if (action == null) {
			action = "addUser"; // default action
		}
		if (action.equals("addUser")) {
			url = "/addUser.jsp"; // the "login" page
		} else if (action.equals("addedUser")) {
			String username = request.getParameter("username");
			try {
				Users user = usersDB.getUser(username);
				if(user == null){
					String password = request.getParameter("password");
					String firstName = request.getParameter("firstName");
					String lastName = request.getParameter("lastName");
					String phone = request.getParameter("phone");
					String email = request.getParameter("email");
					String role = request.getParameter("role");
					int roleID = 0;
					if(role.equals("employee"))
						roleID = 1;
					else if(role.equals("manager"))
						roleID = 2;
					else if(role.equals("administrator"))
						roleID = 3;
					user = new Users();
					user.setUsername(username);
					user.setPassword(password);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setPhone(phone);
					user.setEmail(email);
					user.setRoleID(roleID);
					usersDB.addUser(user);
					url = "/UsersServlet";
				}
				else{
					url = "/addUser.jsp";
				}					
			} catch (DBException e) {
				e.printStackTrace();
			}
			
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
		
	}

}
