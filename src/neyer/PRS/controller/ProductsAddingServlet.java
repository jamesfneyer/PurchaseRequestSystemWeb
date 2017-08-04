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
 * Servlet implementation class VendorsAddingServlet
 */
@WebServlet("/ProductsAddingServlet")
public class ProductsAddingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsAddingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VendorsDAO vendorsDB = DAOFactory.getVendorsDAO();
		String url = "/addProduct.jsp";
		try {
			ArrayList<Vendors> vendors = vendorsDB.getAllVendors();
			request.setAttribute("vendors", vendors);
		} catch (DBException e) {
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}


}
