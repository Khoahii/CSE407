package com.stara.product;

public interface ProductFactory {
	/**
	 * Phương thức tạo sản phẩm dựa trên các thông tin cơ bản và các đối số cụ thể.
	 * @param id ID sản phẩm
	 * @param name Tên sản phẩm
	 * @param price Giá sản phẩm
	 * @param quantity Số lượng tồn kho
	 * @param specificArgs Các đối số đặc trưng (ví dụ: ngày hết hạn cho FoodProduct, thời gian bảo hành cho ElectronicsProduct)
	 * @return Đối tượng Product đã được tạo
	 */
	Product createProduct(String id, String name, double price, int quantity, String... specificArgs);
}