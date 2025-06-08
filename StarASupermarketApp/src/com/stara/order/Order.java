package com.stara.order;

import com.stara.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private String orderId;
	private List<OrderItem> items;
	private double totalAmount; // Tổng tiền trước giảm giá
	private double discount;    // Số tiền giảm giá
	private String customerName;
	private String paymentMethod;
	private double finalAmount; // Tổng tiền cuối cùng sau giảm giá

	// Constructor là private để chỉ OrderBuilder có thể tạo đối tượng Order
	private Order(OrderBuilder builder) {
		this.orderId = builder.orderId;
		this.items = builder.items;
		this.discount = builder.discount;
		this.customerName = builder.customerName;
		this.paymentMethod = builder.paymentMethod;
		calculateTotalAndFinalAmount(); // Tính toán tổng tiền sau khi các thuộc tính được thiết lập
	}

	// Phương thức nội bộ để tính toán tổng tiền
	private void calculateTotalAndFinalAmount() {
		this.totalAmount = items.stream()
						.mapToDouble(OrderItem::getTotalPrice)
						.sum();
		this.finalAmount = totalAmount - discount;
		if (this.finalAmount < 0) { // Đảm bảo tổng tiền không âm
			this.finalAmount = 0;
		}
	}

	// --- Getters ---
	public String getOrderId() {
		return orderId;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public double getDiscount() {
		return discount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public double getFinalAmount() {
		return finalAmount;
	}

	// Hiển thị chi tiết đơn hàng
	public void displayOrderDetails() {
		System.out.println("\n--- CHI TIẾT ĐƠN HÀNG ---");
		System.out.printf("  ID Đơn hàng: %s%n", orderId);
		System.out.printf("  Khách hàng: %s%n", customerName != null && !customerName.isEmpty() ? customerName : "Khách vãng lai");
		System.out.println("  Sản phẩm:");
		if (items.isEmpty()) {
			System.out.println("    (Không có sản phẩm nào)");
		} else {
			for (OrderItem item : items) {
				item.displayInfo();
			}
		}
		System.out.printf("  Tổng tiền (chưa giảm giá): %,.0f VNĐ%n", totalAmount);
		System.out.printf("  Giảm giá: %,.0f VNĐ%n", discount);
		System.out.printf("  Tổng tiền cần thanh toán: %,.0f VNĐ%n", finalAmount);
		System.out.printf("  Phương thức thanh toán: %s%n", paymentMethod);
		System.out.println("-------------------------");
	}

	// --- Lớp Builder (Nested Class) ---
	// Lớp Builder này là một inner static class của Order
	public static class OrderBuilder {
		private String orderId;
		private List<OrderItem> items = new ArrayList<>();
		private double discount = 0;
		private String customerName = "";
		private String paymentMethod = "Chưa xác định";

		public OrderBuilder(String orderId) {
			if (orderId == null || orderId.trim().isEmpty()) {
				throw new IllegalArgumentException("ID đơn hàng không được để trống.");
			}
			this.orderId = orderId;
		}

		public OrderBuilder addItem(Product product, int quantity) {
			if (product == null || quantity <= 0) {
				throw new IllegalArgumentException("Sản phẩm hoặc số lượng không hợp lệ.");
			}
			this.items.add(new OrderItem(product, quantity));
			return this; // Trả về chính builder để cho phép gọi chuỗi (method chaining)
		}

		public OrderBuilder addDiscount(double discountPercentage) {
			this.discount = discountPercentage;
			return this;
		}

		public OrderBuilder setCustomer(String customerName) {
			if (customerName != null) {
				this.customerName = customerName;
			}
			return this;
		}

		public OrderBuilder setPaymentMethod(String paymentMethod) {
			if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
				this.paymentMethod = paymentMethod;
			}
			return this;
		}

		public Order build() {
			// Khi build, chúng ta tạo một đối tượng Order mới và truyền chính builder này vào
			return new Order(this);
		}
	}
}