package neyer.PRS.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neyer.PRS.business.Requests;
import neyer.PRS.business.Users;
import neyer.PRS.dbutils.DBException;
import neyer.PRS.javautil.DAOFactory;
import neyer.PRS.requests.RequestsDAO;
import neyer.PRS.users.UsersDAO;

/**
 * Servlet implementation class UpdateProductsServlett
 */
@WebServlet("/UpdateRequestsServlet")
public class UpdateRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String url = "";
	private static RequestsDAO requestsDB = DAOFactory.getRequestsDAO();
	private static UsersDAO usersDB = DAOFactory.getUsersDAO();       
    /**
     * @see HttpServlet#HttpServlet()
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		url = "/managerMenu.jsp";
		String action = request.getParameter("action");
		if (action == null) {
			action = "updateRequest"; // default action
		}
		if (action.equals("updateRequest")) {
			url = "/managerMenu.jsp";
		}else if (action.equals("updatedRequest")) {
			try {
				String username = request.getParameter("user");
				Users user = usersDB.getUser(username);
				request.setAttribute("user", user);
				String status = "";
				for (String id : request.getParameterValues("id")){
					int requestID = Integer.parseInt(id);
					status = request.getParameter("status_"+id);
					requestsDB.updateRequest(requestID, status);
				}
				ArrayList<Requests> rs = requestsDB.getSubmittedRequests();
				request.setAttribute("requests", rs);

			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
