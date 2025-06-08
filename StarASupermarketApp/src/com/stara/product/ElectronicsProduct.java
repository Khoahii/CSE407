package com.stara.product;

public class ElectronicsProduct implements Product {
	private String id;
	private String name;
	private double price;
	private int quantity;
	private String warrantyPeriod; // Thời gian bảo hành (ví dụ: "12 tháng")

	public ElectronicsProduct(String id, String name, double price, int quantity, String warrantyPeriod) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.warrantyPeriod = warrantyPeriod;
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

	public String getWarrantyPeriod() { return warrantyPeriod; } // Getter riêng cho ElectronicsProduct

	@Override
	public void displayInfo() {
		System.out.printf("  %s | %-20s | %,.0f VNĐ | SL: %-5d | BH: %s tháng%n", id, name, price, quantity, warrantyPeriod);
	}
}