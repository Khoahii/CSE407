package com.stara.delivery.factory;

import com.stara.delivery.product.DeliveryVehicle;
import com.stara.delivery.product.RoutePlanner;
import com.stara.delivery.product.StandardVan;
import com.stara.delivery.product.SimpleRoutePlanner;

public class StandardDeliveryFactory implements DeliveryServiceFactory {
	@Override
	public DeliveryVehicle createVehicle() {
		return new StandardVan();
	}

	@Override
	public RoutePlanner createRoutePlanner() {
		return new SimpleRoutePlanner();
	}
}