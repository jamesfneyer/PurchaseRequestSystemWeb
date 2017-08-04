package neyer.PRS.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neyer.PRS.javautil.DAOFactory;
import neyer.PRS.Vendors.VendorsDAO;
import neyer.PRS.business.Vendors;
import neyer.PRS.dbutils.DBException;

/**
 * Servlet implementation class VendorsServlet
 */
@WebServlet("/VendorsServlet")
public class VendorsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static VendorsDAO vendorsDB = DAOFactory.getVendorsDAO();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VendorsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "/vendorsMenu.jsp";
		try {
			ArrayList<Vendors> vendors = vendorsDB.getAllVendors();
			request.setAttribute("vendors", vendors);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
}
