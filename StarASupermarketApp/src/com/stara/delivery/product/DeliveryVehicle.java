package com.stara.delivery.product;

import com.stara.order.Order; // Lớp Order từ package order của bạn

public interface DeliveryVehicle {
	void deliver(Order order);
}