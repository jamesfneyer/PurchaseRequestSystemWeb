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
@WebServlet("/UpdateVendorServlet")
public class UpdateVendorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static VendorsDAO vendorsDB = DAOFactory.getVendorsDAO();
	static String url = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateVendorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		url = "/updateVendor.jsp";
		String action = request.getParameter("action");
		if (action == null) {
			action = "updateVendor"; // default action
		}
		if (action.equals("updateUser")) {
			url = "/updateVendor.jsp"; // the "login" page
		}else if (action.equals("updatedVendor")) {
			String code = request.getParameter("code");
			try {
				Vendors vendor = vendorsDB.getVendor(code);
				ArrayList<Vendors> vendors = vendorsDB.getAllVendors();
				request.setAttribute("vendors", vendors);
				if(vendor != null){
					String name = request.getParameter("name");
					String address = request.getParameter("address");
					String city = request.getParameter("city");
					String state = request.getParameter("state");
					String zipCode = request.getParameter("zipCode");
					String phone = request.getParameter("phone");
					String email = request.getParameter("email");
					String preapprovedString = request.getParameter("preapproved");
					
					if (!preapprovedString.equals("")) {
						boolean preapproved = false;
						if (preapprovedString.equalsIgnoreCase("true"))
							preapproved = true;
						else if (preapprovedString.equalsIgnoreCase("false"))
							preapproved = false;
						vendor.setPreapproved(preapproved);
					}
					if(!name.equals(""))
						vendor.setName(name);
					if(!address.equals(""))
						vendor.setAddress(address);
					if(!city.equals(""))
						vendor.setCity(city);
					if(!state.equals(""))
						vendor.setState(state);
					if(!zipCode.equals(""))
						vendor.setZipCode(zipCode);
					if(!phone.equals(""))
						vendor.setPhone(phone);
					if(!email.equals(""))
						vendor.setEmail(email);
					
					vendorsDB.updateVendor(vendor);
					url = "/vendorsMenu.jsp";
				} else{
				url = "/updateVendor.jsp";
				}
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
