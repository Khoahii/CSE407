package com.stara.product;

public interface Product {
	String getId();
	String getName();
	double getPrice();
	int getQuantity();
	void setQuantity(int quantity); // Để cập nhật số lượng khi bán hàng hoặc thêm vào kho
	void displayInfo(); // Phương thức để hiển thị thông tin sản phẩm

	// Phương thức mặc định để giảm số lượng, các lớp con có thể override
	default void reduceQuantity(int amount) {
		if (amount > 0 && this.getQuantity() >= amount) {
			setQuantity(this.getQuantity() - amount);
		} else {
			System.out.println("Không đủ số lượng để giảm hoặc số lượng giảm không hợp lệ.");
		}
	}
}