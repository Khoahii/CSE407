package com.stara.product;

public class FoodProduct implements Product {
	private String id;
	private String name;
	private double price;
	private int quantity;
	private String expiryDate; // Ngày hết hạn

	public FoodProduct(String id, String name, double price, int quantity, String expiryDate) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.expiryDate = expiryDate;
	}

	@Override
	public String getId() { return id; }
	@Override
	public String getName() { return name; }
	@Override
	public double getPrice() { return price; }
	@Override
	public int getQuantity() { return quantity; }
	@Override
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public String getExpiryDate() { return expiryDate; } // Getter riêng cho FoodProduct

	@Override
	public void displayInfo() {
		System.out.printf("  %s | %-20s | %,.0f VNĐ | SL: %-5d | HSD: %s%n", id, name, price, quantity, expiryDate);
	}
}