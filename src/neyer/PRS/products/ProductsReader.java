package neyer.PRS.products;

import java.util.ArrayList;
import neyer.PRS.dbutils.DBException;
import neyer.PRS.business.Products;

public interface ProductsReader {
	ArrayList<Products> getAllVendorProducts(int vendorID) throws DBException;
	ArrayList<Products> getAllProducts() throws DBException;
	Products getProduct(String partNumber, int vID) throws DBException;
}
