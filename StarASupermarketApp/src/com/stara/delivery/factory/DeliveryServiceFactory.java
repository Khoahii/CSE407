package com.stara.delivery.factory;

import com.stara.delivery.product.DeliveryVehicle;
import com.stara.delivery.product.RoutePlanner;

public interface DeliveryServiceFactory {
	DeliveryVehicle createVehicle();
	RoutePlanner createRoutePlanner();
}