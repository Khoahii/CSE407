// File: src/main/java/com/stara/order/Order.java
package com.stara.order;

import java.time.LocalDateTime;
// import java.util.List; // Nếu bạn có List<OrderItem> trong Order

public class Order {
	private String orderId;
	private String customerName;
	private double totalAmount;
	private double discount;
	private double finalAmount;
	private String paymentMethod;
	private LocalDateTime orderDate;
	private String paymentStatus;
	protected Order() {
		this.orderId = "N/A_INIT";
		this.customerName = "Guest Customer";
		this.totalAmount = 0.0;
		this.discount = 0.0;
		this.finalAmount = 0.0;
		this.paymentMethod = "None";
		this.orderDate = LocalDateTime.now();
		this.paymentStatus = "INIT";
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setFinalAmount(double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getOrderId() { return orderId; }
	public String getCustomerName() { return customerName; }
	public double getTotalAmount() { return totalAmount; }
	public double getDiscount() { return discount; }
	public double getFinalAmount() { return finalAmount; }
	public String getPaymentMethod() { return paymentMethod; }
	public LocalDateTime getOrderDate() { return orderDate; }
	public String getPaymentStatus() { return paymentStatus; }
	// public List<OrderItem> getItems() { return items; }

	@Override
	public String toString() {
		return "Order [\n" +
						"  orderId='" + orderId + "',\n" +
						"  customerName='" + customerName + "',\n" +
						"  totalAmount=" + String.format("%.2f", totalAmount) + ",\n" +
						"  discount=" + String.format("%.2f", discount) + ",\n" +
						"  finalAmount=" + String.format("%.2f", finalAmount) + ",\n" +
						"  paymentMethod='" + paymentMethod + "',\n" +
						"  orderDate=" + orderDate + ",\n" +
						"  paymentStatus='" + paymentStatus + "'\n" +
						"]";
	}
}