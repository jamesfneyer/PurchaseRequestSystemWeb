package neyer.PRS.business;

import java.io.Serializable;
import java.util.ArrayList;

import neyer.PRS.dbutils.DBException;
import neyer.PRS.javautil.DAOFactory;
import neyer.PRS.lineItems.LineItemDAO;

public class LineItems implements Serializable{
	private String partNumber;
	private String user;
	private int requestID;
	private int productID;
	private int Quantity;
	private double total;
	private int id;
	private boolean preapproved;
		
	public boolean isPreapproved() {
		return preapproved;
	}

	public void setPreapproved(boolean preapproved) {
		this.preapproved = preapproved;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setID(){
		LineItemDAO lineItemsDB = DAOFactory.getLineItemDAO();
		ArrayList<LineItems> lis;
		try {
			lis = lineItemsDB.getAllLineItems();
			for(LineItems l: lis){
				id = l.getId();
			}
			id++;
			setId(id);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
