package com.stara.delivery.product;

import com.stara.order.Order; // Lớp Order từ package order của bạn

public class Motorbike implements DeliveryVehicle {
	@Override
	public void deliver(Order order) {
		System.out.println("Xe máy tốc hành: Đang giao đơn hàng tới vị trí của bạn(Giao hàng nhanh)");
	}
}