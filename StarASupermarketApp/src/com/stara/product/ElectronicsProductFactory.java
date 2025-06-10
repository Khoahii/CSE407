package com.stara.product;

public class ElectronicsProductFactory implements ProductFactory {
	@Override
	public Product createProduct(String id, String name, double price, int quantity, String... specificArgs) {
		if (specificArgs.length > 0) {
			String warrantyPeriod = specificArgs[0]; // Đối số đầu tiên là thời gian bảo hành
			return new ElectronicsProduct(id, name, price, quantity, warrantyPeriod);
		}
		throw new IllegalArgumentException("Thời gian bảo hành là bắt buộc cho ElectronicsProduct.");
	}

	@Override
	public String confirAddProduct(String name){
		return "Sản phẩm " + '"' + name + '"' + " đã được thêm.";
	}
}