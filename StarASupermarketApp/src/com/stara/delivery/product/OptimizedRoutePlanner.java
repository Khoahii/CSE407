package com.stara.delivery.product;

import java.util.Arrays;
import java.util.List;

public class OptimizedRoutePlanner implements RoutePlanner {
	@Override
	public List<String> planRoute(String address) {
		System.out.println("Lập kế hoạch lộ trình tối ưu: Lập lộ trình nhanh nhất cho " + address + " có tính đến giao thông.");
		return Arrays.asList("Đoạn đường nhanh nhất X", "Đoạn đường nhanh nhất Y");
	}
}