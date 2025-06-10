package com.stara.product;

public class FoodProductFactory implements ProductFactory {
	@Override
	public Product createProduct(String id, String name, double price, int quantity, String... specificArgs) {
		if (specificArgs.length > 0) {
			String expiryDate = specificArgs[0]; // Đối số đầu tiên là ngày hết hạn
			return new FoodProduct(id, name, price, quantity, expiryDate);
		}
		throw new IllegalArgumentException("Ngày hết hạn là bắt buộc cho FoodProduct.");
	}

	@Override
	public String confirAddProduct(String name){
		return "Sản phẩm " + '"' + name + '"' + " đã được thêm.";
	}
}