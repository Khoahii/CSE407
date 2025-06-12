package com.stara.delivery.factory;

import com.stara.delivery.product.DeliveryVehicle;
import com.stara.delivery.product.RoutePlanner;
import com.stara.delivery.product.Motorbike;
import com.stara.delivery.product.OptimizedRoutePlanner;

public class ExpressDeliveryFactory implements DeliveryServiceFactory {
	@Override
	public DeliveryVehicle createVehicle() {
		return new Motorbike();
	}

	@Override
	public RoutePlanner createRoutePlanner() {
		return new OptimizedRoutePlanner();
	}
}