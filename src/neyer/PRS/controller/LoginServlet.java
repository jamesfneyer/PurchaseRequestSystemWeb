package neyer.PRS.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neyer.PRS.business.Products;
import neyer.PRS.business.Requests;
import neyer.PRS.business.Users;
import neyer.PRS.dbutils.DBException;
import neyer.PRS.javautil.DAOFactory;
import neyer.PRS.products.ProductsDAO;
import neyer.PRS.requests.RequestsDAO;
import neyer.PRS.users.UsersDAO;

/**
 * Servlet implementation class PRSPresentationServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	static UsersDAO usersDB = DAOFactory.getUsersDAO();
	private static final long serialVersionUID = 1L;
	static String url = "";


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		url = "/index.html";
		Users user = null;
		RequestsDAO requestsDB = DAOFactory.getRequestsDAO();
		ProductsDAO productsDB = DAOFactory.getProductsDAO();

		String action = request.getParameter("action");
		if (action == null) {
			action = "login"; // default action
		}
		if (action.equals("login")) {
			url = "/index.jsp"; // the "login" page
		} else if (action.equals("loggedin")) {
			user = isValidUsername(request);
			if(user != null){
				request.setAttribute("user",user);
				if(user.getRole().equalsIgnoreCase("Administrator"))
					url = "/administratorMenu.jsp";
				else if(user.getRole().equalsIgnoreCase("Manager")){
					try {
						ArrayList<Requests> requests = requestsDB.getSubmittedRequests();
						request.setAttribute("requests", requests);
					} catch (DBException e) {
						e.printStackTrace();
					}
					url = "/managerMenu.jsp";
				}
				else if(user.getRole().equalsIgnoreCase("Employee")){
					try {
						ArrayList<Requests> requests = requestsDB.getUserRequests(user.getID());
						ArrayList<Products> products = productsDB.getAllProducts();
						request.setAttribute("requests", requests);
						request.setAttribute("user", user);
						request.setAttribute("products", products);
					} catch (DBException e) {
						e.printStackTrace();
					}
					url = "/employeeMenu.jsp";
				}
			}
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);

	}

	private static Users isValidUsername(HttpServletRequest request) {
		Users user = null;
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			user = usersDB.getUser(username);
			if(user !=null){
				if (!user.getPassword().equals(password)){
					url  = "/index.jsp";
					user = null;
				}
			}
			else{
				url = "/index.jsp";
			}
		} catch (DBException e) {
			System.out.println(e + "\n");
		}
		return user;
	}
}   
