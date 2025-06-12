package com.stara.delivery.product;

import java.util.List;

public interface RoutePlanner {
	List<String> planRoute(String address);
}