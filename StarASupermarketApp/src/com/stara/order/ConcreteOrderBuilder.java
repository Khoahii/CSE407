// File: src/main/java/com/stara/order/ConcreteOrderBuilder.java
package com.stara.order;

import java.time.LocalDateTime;

public class ConcreteOrderBuilder implements OrderBuilder {
	private Order order;
	public ConcreteOrderBuilder() {
		this.reset(); // Khởi tạo một Order mới khi Builder được tạo
	}

	@Override
	public OrderBuilder reset() {
		this.order = new Order();
		return this;
	}

	@Override
	public OrderBuilder withOrderId(String orderId) {
		// Lỗi của bạn xảy ra ở đây, vì 'this.order' đang là null
		this.order.setOrderId(orderId);
		return this;
	}

	@Override
	public OrderBuilder withCustomer(String customerName) {
		this.order.setCustomerName(customerName);
		return this;
	}

	@Override
	public OrderBuilder withAmounts(double totalAmount, double discount) {
		this.order.setTotalAmount(totalAmount);
		this.order.setDiscount(discount);
		this.order.setFinalAmount(totalAmount - discount);
		return this;
	}

	@Override
	public OrderBuilder withPaymentDetails(String paymentMethod, String paymentStatus) {
		this.order.setPaymentMethod(paymentMethod);
		this.order.setPaymentStatus(paymentStatus);
		return this;
	}

	@Override
	public OrderBuilder withOrderDate(LocalDateTime orderDate) {
		this.order.setOrderDate(orderDate);
		return this;
	}

	@Override
	public Order getResult() {
		if ("N/A_INIT".equals(order.getOrderId()) || order.getTotalAmount() <= 0) {
			System.err.println("Cảnh báo: Đơn hàng thiếu ID hoặc Tổng tiền không hợp lệ. Vẫn trả về đối tượng, nhưng có thể cần xử lý lỗi.");
		}

		Order builtOrder = this.order;
		return builtOrder;
	}
}