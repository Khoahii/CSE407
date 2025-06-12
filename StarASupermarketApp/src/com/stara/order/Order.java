// File: src/com/stara/order/Order.java
package com.stara.order;

import java.time.LocalDateTime;
// import java.util.List; // Nếu bạn có List<OrderItem> trong Order

public class Order {
	private String orderId;
	private String customerName; // Để giữ tương thích với các thuộc tính khác
	private String customerAddress; // THÊM THUỘC TÍNH NÀY CHO VẬN CHUYỂN
	private double totalAmount;
	private double discount;
	private double finalAmount;
	private String paymentMethod;
	private LocalDateTime orderDate;
	private String paymentStatus;
	private String deliveryMethod; // THUỘC TÍNH ĐÃ CÓ

	// Constructor mặc định (đã sửa lỗi khởi tạo deliveryMethod)
	public Order() {
		this.orderId = "N/A_INIT";
		this.customerName = "Guest Customer";
		this.customerAddress = "Unknown"; // Khởi tạo giá trị mặc định
		this.totalAmount = 0.0;
		this.discount = 0.0;
		this.finalAmount = 0.0;
		this.paymentMethod = "None";
		this.orderDate = LocalDateTime.now();
		this.paymentStatus = "INIT";
		this.deliveryMethod = "None"; // Sửa lỗi khởi tạo, gán giá trị mặc định rõ ràng
	}

	// CONSTRUCTOR MỚI ĐỂ HỖ TRỢ VIỆC TẠO ĐƠN HÀNG TỪ UI CHO TÍNH NĂNG VẬN CHUYỂN
	public Order(String orderId, String customerAddress, double totalAmount, String deliveryMethod) {
		this.orderId = orderId;
		this.customerAddress = customerAddress;
		this.totalAmount = totalAmount;
		this.deliveryMethod = deliveryMethod;
		this.customerName = "N/A"; // Gán giá trị mặc định nếu không được cung cấp
		this.discount = 0.0;
		this.finalAmount = totalAmount; // Ban đầu finalAmount = totalAmount
		this.paymentMethod = "Unpaid";
		this.orderDate = LocalDateTime.now();
		this.paymentStatus = "Pending";
	}

	// Các Setters
	public void setOrderId(String orderId) { this.orderId = orderId; }
	public void setCustomerName(String customerName) { this.customerName = customerName; }
	public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; } // THÊM SETTER
	public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
	public void setDiscount(double discount) { this.discount = discount; }
	public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
	public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
	public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
	public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
	public void setDeliveryMethod(String deliveryMethod) { this.deliveryMethod = deliveryMethod; } // THÊM SETTER MỚI


	// Các Getters
	public String getOrderId() { return orderId; }
	public String getCustomerName() { return customerName; }
	public String getCustomerAddress() { return customerAddress; } // THÊM GETTER MỚI
	public double getTotalAmount() { return totalAmount; }
	public double getDiscount() { return discount; }
	public double getFinalAmount() { return finalAmount; }
	public String getPaymentMethod() { return paymentMethod; }
	public LocalDateTime getOrderDate() { return orderDate; }
	public String getPaymentStatus() { return paymentStatus; }
	public String getDeliveryMethod() { return deliveryMethod; } // THÊM GETTER MỚI
	// public List<OrderItem> getItems() { return items; }

	@Override
	public String toString() {
		return "Order [\n" +
						"  orderId='" + orderId + "',\n" +
						"  customerName='" + customerName + "',\n" +
						"  customerAddress='" + customerAddress + "',\n" + // Thêm vào toString
						"  totalAmount=" + String.format("%.2f", totalAmount) + ",\n" +
						"  discount=" + String.format("%.2f", discount) + ",\n" +
						"  finalAmount=" + String.format("%.2f", finalAmount) + ",\n" +
						"  paymentMethod='" + paymentMethod + "',\n" +
						"  orderDate=" + orderDate + ",\n" +
						"  paymentStatus='" + paymentStatus + "',\n" +
						"  deliveryMethod='" + deliveryMethod  + "'\n" +
						"]";
	}
}