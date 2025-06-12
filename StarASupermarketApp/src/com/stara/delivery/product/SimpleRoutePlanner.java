package com.stara.delivery.product;

import java.util.Arrays;
import java.util.List;

public class SimpleRoutePlanner implements RoutePlanner {
	@Override
	public List<String> planRoute(String address) {
		System.out.println("Lập kế hoạch lộ trình đơn giản: Lập lộ trình cơ bản cho " + address);
		return Arrays.asList("Đoạn đường A", "Đoạn đường B");
	}
}