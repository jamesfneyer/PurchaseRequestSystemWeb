package neyer.PRS.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neyer.PRS.Vendors.VendorsDAO;
import neyer.PRS.business.Vendors;
import neyer.PRS.dbutils.DBException;
import neyer.PRS.javautil.DAOFactory;

/**
 * Servlet implementation class AddVendorServlet
 */
@WebServlet("/AddVendorServlet")
public class AddVendorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static VendorsDAO vendorsDB = DAOFactory.getVendorsDAO();
	static String url = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddVendorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		url = "/addVendor.jsp";
		String action = request.getParameter("action");
		if (action == null) {
			action = "addVendor"; // default action
		}
		if (action.equals("addRequest")) {
			url = "/addVendor.jsp"; // the "login" page
		}else if (action.equals("addedVendor")) {
			String code = request.getParameter("code");
			try {
				Vendors vendor = vendorsDB.getVendor(code);
				ArrayList<Vendors> vendors = vendorsDB.getAllVendors();
				request.setAttribute("vendors", vendors);
				if(vendor == null){
					String name = request.getParameter("name");
					String address = request.getParameter("address");
					String city = request.getParameter("city");
					String state = request.getParameter("state");
					String zipCode = request.getParameter("zipCode");
					String phone = request.getParameter("phone");
					String email = request.getParameter("email");
					String preapprovedString = request.getParameter("preapproved");
					boolean preapproved = false;
					if(preapprovedString.equalsIgnoreCase("true"))
						preapproved = true;
					else if(preapprovedString.equalsIgnoreCase("false"))
						preapproved = false;
					vendor = new Vendors();
					vendor.setCode(code);
					vendor.setName(name);
					vendor.setAddress(address);
					vendor.setCity(city);
					vendor.setState(state);
					vendor.setZipCode(zipCode);
					vendor.setPhone(phone);
					vendor.setEmail(email);
					vendor.setPreapproved(preapproved);
					vendorsDB.addVendor(vendor);
					url = "/vendorsMenu.jsp";
				} else{
				url = "/addVendor.jsp";
				}
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
