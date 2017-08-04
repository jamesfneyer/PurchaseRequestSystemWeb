package neyer.PRS.business;

import java.io.Serializable;
import java.text.NumberFormat;

public class Products implements Serializable{
	private String name;
	private String partNumber;
	private String formattedPrice;
	private double price;
	private String unit;
	private String photoPath;
	private int productsID;
	private int vendorID;
	private String vendorName;
	private String vendorCode;
	
	
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	NumberFormat currency = NumberFormat.getCurrencyInstance();
	

	public int getVendorID() {
		return vendorID;
	}

	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}

	public int getProductsID() {
		return productsID;
	}

	public void setProductsID(int productsID) {
		this.productsID = productsID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getFormattedPrice() {
		formattedPrice = currency.format(price);
		return formattedPrice;
	}

	
	
}
