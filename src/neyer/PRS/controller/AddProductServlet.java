package neyer.PRS.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neyer.PRS.Vendors.VendorsDAO;
import neyer.PRS.business.Products;
import neyer.PRS.business.Vendors;
import neyer.PRS.dbutils.DBException;
import neyer.PRS.javautil.DAOFactory;
import neyer.PRS.javautil.Validator;
import neyer.PRS.products.ProductsDAO;

/**
 * Servlet implementation class AddProductServlet
 */
@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static VendorsDAO vendorsDB = DAOFactory.getVendorsDAO();
	private static ProductsDAO productsDB = DAOFactory.getProductsDAO();
	private static String url = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "addProduct"; // default action
		}
		if (action.equals("addProduct")) {
			url = "/PRSWeb/ProductsAddingServlet"; // the "login" page
		} else if (action.equals("addedProduct")) {
			String vendorsCode = request.getParameter("vendorCode");
			Vendors vendor;
			try {
				vendor = vendorsDB.getVendor(vendorsCode);
				if (vendor != null) {
					String partNumber = request.getParameter("partNumber");
					Products product = productsDB.getProduct(partNumber, vendor.getID());
					if (product == null) {
						String priceString = request.getParameter("price");
						double price = Validator.getValidDouble(priceString);
						if (price != 0.0) {
							product = new Products();
							String name = request.getParameter("name");
							String unit = request.getParameter("unit");
							String photoPath = request.getParameter("photoPath");
							int vendorID = vendor.getID();
							product.setName(name);
							product.setPartNumber(partNumber);
							product.setPhotoPath(photoPath);
							product.setPrice(price);
							product.setUnit(unit);
							product.setVendorID(vendorID);
							productsDB.addProduct(product);
							url = "/PRSWeb/ProductsAddingServlet";
						} else {
							url = "/PRSWeb/ProductsAddingServlet";
						}
					} else
						url = "/PRSWeb/ProductsAddingServlet";
				} else
					url = "/PRSWeb/ProductsAddingServlet";
			} catch (DBException e) {
				e.printStackTrace();
			}

		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
