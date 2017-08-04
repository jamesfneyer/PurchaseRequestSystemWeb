package neyer.PRS.controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class UpdateProductsServlet
 */
@WebServlet("/UpdateProductsServlet")
public class UpdateProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static VendorsDAO vendorsDB = DAOFactory.getVendorsDAO();
	private static ProductsDAO productsDB = DAOFactory.getProductsDAO();
	private static String url = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateProductsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		url = "/updateProduct";
		String action = request.getParameter("action");
		if (action == null) {
			action = "updateUser"; // default action
		}
		if (action.equals("updateProduct")) {
			url = "/updateProduct"; // the "login" page
		} else if (action.equals("updatedProduct")) {
			String vendorsCode = request.getParameter("vendorCode");
			Vendors vendor;
			try {
				vendor = vendorsDB.getVendor(vendorsCode);
				ArrayList<Products> products = productsDB.getAllProducts();
				request.setAttribute("products", products);
				if (vendor != null) {
					String partNumber = request.getParameter("partNumber");
					Products product = productsDB.getProduct(partNumber, vendor.getID());
					if (product != null) {
						String name = request.getParameter("name");
						String priceString = request.getParameter("price");
						if (!priceString.equalsIgnoreCase("")) {
							double price = Validator.getValidDouble(priceString);
							if (price != 0.0)
								product.setPrice(price);
						}
						String unit = request.getParameter("unit");
						String photoPath = request.getParameter("photoPath");
						if(!name.equals(""))
							product.setName(name);
						if(!partNumber.equals(""))
							product.setPartNumber(partNumber);
						if(!photoPath.equals(""))
							product.setPhotoPath(photoPath);
						if(!unit.equals(""))
							product.setUnit(unit);
						url = "/productsMenu";
						productsDB.updateProduct(product);						
					}
				}
			} catch (DBException e) {
				e.printStackTrace();
			}

		}
		getServletContext().getRequestDispatcher(url).forward(request, response);

	}

}
