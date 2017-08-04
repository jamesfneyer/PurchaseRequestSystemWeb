package neyer.PRS.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

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
 * Servlet implementation class PRSPresentationServlet
 */
@WebServlet("/UsersServlet")
public class UsersServlet extends HttpServlet {
	static UsersDAO usersDB = DAOFactory.getUsersDAO();
	static NumberFormat currency = NumberFormat.getCurrencyInstance();
	private static final long serialVersionUID = 1L;
	static String url = "";


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			url = "/usersMenu.jsp";
			ArrayList<Users> users = usersDB.getAllUsers();
			request.setAttribute("users", users);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);

	}


}   
