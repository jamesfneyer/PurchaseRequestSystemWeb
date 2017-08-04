package neyer.PRS.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neyer.PRS.Vendors.VendorsDAO;
import neyer.PRS.business.LineItems;
import neyer.PRS.business.Products;
import neyer.PRS.business.Requests;
import neyer.PRS.business.Users;
import neyer.PRS.business.Vendors;
import neyer.PRS.dbutils.DBException;
import neyer.PRS.javautil.DAOFactory;
import neyer.PRS.javautil.Validator;
import neyer.PRS.lineItems.LineItemDAO;
import neyer.PRS.products.ProductsDAO;
import neyer.PRS.requests.RequestsDAO;
import neyer.PRS.users.UsersDAO;

/**
 * Servlet implementation class AddRequestServlet
 */
@WebServlet("/AddRequestServlet")
public class AddRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String url = "";
	private static RequestsDAO requestsDB = DAOFactory.getRequestsDAO();
	private static LineItemDAO lineItemsDB = DAOFactory.getLineItemDAO();
	private static ProductsDAO productsDB = DAOFactory.getProductsDAO();
	private static VendorsDAO vendorsDB = DAOFactory.getVendorsDAO();
	private static UsersDAO usersDB = DAOFactory.getUsersDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		url = "/employeeMenu.jsp";
		String action = request.getParameter("action");
		if (action == null) {
			action = "addRequest"; // default action
		}
		if (action.equals("addRequest")) {
			url = "/employeeMenu.jsp";
		} else if (action.equals("addedRequest")) {
			try {
				int quantity = Integer.parseInt(request.getParameter("quantity"));

				ArrayList<Products> products = productsDB.getAllProducts();
				request.setAttribute("products", products);

				String username = request.getParameter("user");
				Users user = usersDB.getUser(username);
				request.setAttribute("user", user);

				ArrayList<Requests> requests = requestsDB.getUserRequests(user.getID());
				request.setAttribute("requests", requests);

				String code = request.getParameter("vendorCode");
				Vendors vendor = vendorsDB.getVendor(code);
				
				if (vendor != null) {
					String partNumber = request.getParameter("partNumber");
					System.out.println(partNumber);
					System.out.println(vendor.getID());
					Products product = productsDB.getProduct(partNumber, vendor.getID());
					
					if (quantity > 0 && product != null) {
						
						String neededDate = request.getParameter("dateNeeded");
						java.util.Date dateNeeded = Validator.getDate(neededDate);
						java.sql.Date sqlDN = new java.sql.Date(dateNeeded.getTime());
						
						if (dateNeeded != null) {
							String description = request.getParameter("description");
							String justification = request.getParameter("justification");
							String deliveryMode = request.getParameter("deliveryMode");
							String status = "submitted";
							
							Calendar submittedDateCal = Calendar.getInstance();
							java.util.Date submittedDate = submittedDateCal.getTime();
							java.sql.Date sqlDS = new java.sql.Date(submittedDate.getTime());
							
							double total = quantity * product.getPrice();
							if (total < 50) {
								status = "approved";
							}

							if (vendor.isPreapproved() == true) status = "approved";
							
							Requests r = new Requests();
							r.setDateNeeded(sqlDN);
							r.setSubmittedDate(sqlDS);
							r.setDocAttached(false);
							r.setJustification(justification);
							r.setStatus(status);
							r.setTotal(total);
							r.setUsersID(user.getID());
							r.setDeliveryMode(deliveryMode);
							r.setDescription(description);

							requestsDB.addRequest(r);
							int roleID = requestsDB.getRequestID(description, justification);							
							
							LineItems lineItem = new LineItems();
							lineItem.setPartNumber(partNumber);
							lineItem.setQuantity(quantity);
							lineItem.setRequestID(roleID);
							lineItem.setProductID(product.getProductsID());

							lineItemsDB.addLineItem(lineItem);
							requests = requestsDB.getUserRequests(user.getID());
							request.setAttribute("requests", requests);
							
						}
					}
				}
				url = "/employeeMenu.jsp";
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
