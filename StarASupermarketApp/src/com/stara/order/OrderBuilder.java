// File: src/main/java/com/stara/order/OrderBuilder.java
package com.stara.order;

import java.time.LocalDateTime;

public interface OrderBuilder {
	OrderBuilder reset();

	// Các bước xây dựng tương ứng với buildStepA(), buildStepB(), buildStepZ()
	OrderBuilder withOrderId(String orderId);
	OrderBuilder withCustomer(String customerName);
	OrderBuilder withAmounts(double totalAmount, double discount); // Sẽ tính finalAmount bên trong
	OrderBuilder withPaymentDetails(String paymentMethod, String paymentStatus);
	OrderBuilder withOrderDate(LocalDateTime orderDate);
	// OrderBuilder addOrderItem(OrderItem item); // Nếu OrderBuilder cũng xây dựng các OrderItem

	// Phương thức cuối cùng để lấy đối tượng Order đã hoàn thành
	Order getResult();
}