package neyer.PRS.products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import neyer.PRS.business.Products;
import neyer.PRS.dbutils.ConnectionPool;
import neyer.PRS.dbutils.DBException;

public class ProductsDB implements ProductsDAO {
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection;

	public ProductsDB() {
	}

	public void addProduct(Products product) throws DBException {
		String sql = "insert into Products (Name, PartNumber, Price, Unit, VendorID, PhotoPath) "
				+ "Values (?,?,?,?,?,?);";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, product.getName());
			ps.setString(2, product.getPartNumber());
			ps.setDouble(3, product.getPrice());
			ps.setString(4, product.getUnit());
			ps.setInt(5, product.getVendorID());
			ps.setString(6, product.getPhotoPath());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	public ArrayList<Products> getAllVendorProducts(int vendorID) throws DBException {
		String sql = "select v.Name, v.Code, p.Name, p.PartNumber, p.Price, p.Unit, p.PhotoPath, p.VendorID, p.ID "
				+ "from Products p, Vendors v "
				+ "WHERE VendorID = ? "
				+ "and p.VendorID = v.ID; ";
		ArrayList<Products> products = new ArrayList<>();
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, vendorID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				products.add(getProductFromRow(rs));
			}
			rs.close();
			return products;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	public ArrayList<Products> getAllProducts() throws DBException {
		String sql = "select v.Name, v.Code, p.Name, PartNumber, Price, Unit, PhotoPath, VendorID, p.ID "
				+ "from Products p, Vendors v "
				+ "where v.ID = p.VendorID "
				+ "ORDER BY VendorID; ";
		ArrayList<Products> products = new ArrayList<>();
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				products.add(getProductFromRow(rs));
			}
			rs.close();
			return products;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}
	public Products getProduct(String partNumber, int vID) throws DBException {
		String sql = "select v.Name, v.Code, p.Name, p.PartNumber, p.Price, p.Unit, p.PhotoPath, p.VendorID, p.ID "
				+ "from Products p, Vendors v "
				+ "where PartNumber = ? and VendorID = ?;";
		connection = pool.getConnection();
		Products p = null;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			
			ps.setString(1, partNumber);
			ps.setInt(2, vID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p = getProductFromRow(rs);
			}
			rs.close();
			return p;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}
	
	public void updateProduct(Products product) throws DBException{
		String sql = "UPDATE Products SET "
				+ "Name = ?, "
				+ "Price = ?, "
				+ "Unit = ?, "
				+ "PhotoPath = ?, "
				+ "VendorID = ? "
				+ "WHERE "
				+ "PartNumber = ? ";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, product.getName());
			ps.setDouble(2, product.getPrice());
			ps.setString(3, product.getUnit());
			ps.setString(4, product.getPhotoPath());
			ps.setInt(5, product.getVendorID());
			ps.setString(6, product.getPartNumber());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}
	
	private Products getProductFromRow(ResultSet rs) throws DBException, SQLException {
		try {
			String vendorsName = rs.getString("v.Name");
			String vendorsCode = rs.getString("v.Code");
			String name = rs.getString("p.Name");
			String partNumber = rs.getString("PartNumber");
			double price = rs.getDouble("Price");
			String unit = rs.getString("Unit");
			int vendorID = rs.getInt("VendorID");
			String photoPath = rs.getString("PhotoPath");
			int ID = rs.getInt("p.ID");

			Products p = new Products();
			p.setVendorCode(vendorsCode);
			p.setVendorName(vendorsName);
			p.setProductsID(ID);
			p.setName(name);
			p.setPartNumber(partNumber);
			p.setPrice(price);
			p.setUnit(unit);
			p.setVendorID(vendorID);
			p.setPhotoPath(photoPath);
			return p;

		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
}
