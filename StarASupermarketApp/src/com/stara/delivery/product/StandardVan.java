package com.stara.delivery.product;

import com.stara.order.Order; // Lớp Order từ package order của bạn

public class StandardVan implements DeliveryVehicle {
	@Override
	public void deliver(Order order) {
		System.out.println("Xe tải tiêu chuẩn: Đang giao đơn hàng tới vị trí của bạn");
	}
}