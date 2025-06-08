package com.stara.order;

import com.stara.product.Product; // Import lớp Product từ package product

public class OrderItem {
	private Product product; // Tham chiếu đến sản phẩm
	private int quantity;    // Số lượng sản phẩm trong mục hàng này

	public OrderItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	// --- Getters ---
	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	// Tính tổng tiền cho mục hàng này
	public double getTotalPrice() {
		return product.getPrice() * quantity;
	}

	// Hiển thị thông tin mục hàng
	public void displayInfo() {
		System.out.printf("    - %-20s (ID: %s) | Số lượng: %-5d | Đơn giá: %,.0f VNĐ | Tổng: %,.0f VNĐ%n",
						product.getName(), product.getId(), quantity, product.getPrice(), getTotalPrice());
	}
}