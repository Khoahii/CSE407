package com.stara.product; // Đặt lớp này trong package product/

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDataManager {
	// Chỉ lưu trữ dữ liệu sản phẩm trong data.txt
	private static final String FILE_PATH = "resources/data.txt";
	private static final String PRODUCT_PREFIX = "PRODUCT:"; // Tiền tố để nhận diện dòng sản phẩm
	private static final String DELIMITER = ","; // Dùng dấu phẩy để phân tách các trường

	public static List<Product> loadProducts() {
		List<Product> products = new ArrayList<>();
		File file = new File(FILE_PATH);

		if (!file.exists()) {
			System.out.println("Tạo file dữ liệu mới: " + FILE_PATH + " (nếu chưa tồn tại).");
			try {
				// Tạo thư mục resources nếu nó chưa tồn tại
				File resourcesDir = new File("resources");
				if (!resourcesDir.exists()) {
					resourcesDir.mkdirs(); // Tạo tất cả các thư mục cha nếu cần
				}
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Lỗi: Không thể tạo file dữ liệu: " + e.getMessage());
				return products;
			}
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(PRODUCT_PREFIX)) {
					String productData = line.substring(PRODUCT_PREFIX.length()).trim();
					String[] parts = productData.split(DELIMITER);

					if (parts.length < 5) {
						System.err.println("Dữ liệu sản phẩm không hợp lệ, bỏ qua: " + line);
						continue;
					}

					try {
						String type = parts[0];
						String id = parts[1];
						String name = parts[2];
						double price = Double.parseDouble(parts[3]);
						int quantity = Integer.parseInt(parts[4]);

						Product product = null;
						if (type.equalsIgnoreCase("Food")) {
							if (parts.length >= 6) {
								String expiryDate = parts[5];
								product = new FoodProductFactory().createProduct(id, name, price, quantity, expiryDate);
							}
						} else if (type.equalsIgnoreCase("Electronics")) {
							if (parts.length >= 6) {
								String warrantyPeriod = parts[5];
								product = new ElectronicsProductFactory().createProduct(id, name, price, quantity, warrantyPeriod);
							}
						}

						if (product != null) {
							products.add(product);
						} else {
							System.err.println("Không thể tạo sản phẩm từ dòng: " + line);
						}
					} catch (IllegalArgumentException e) {
						System.err.println("Lỗi phân tích cú pháp dữ liệu sản phẩm: " + line + " - " + e.getMessage());
					}
				}
			}
			System.out.println("Đã tải " + products.size() + " sản phẩm từ file: " + FILE_PATH);
		} catch (IOException e) {
			System.err.println("Lỗi khi đọc dữ liệu sản phẩm từ file: " + e.getMessage());
		}
		return products;
	}

	public static void saveProducts(List<Product> products) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
			for (Product product : products) {
				StringBuilder sb = new StringBuilder(PRODUCT_PREFIX);
				if (product instanceof FoodProduct) {
					FoodProduct fp = (FoodProduct) product;
					sb.append("Food").append(DELIMITER)
									.append(fp.getId()).append(DELIMITER)
									.append(fp.getName()).append(DELIMITER)
									.append(fp.getPrice()).append(DELIMITER)
									.append(fp.getQuantity()).append(DELIMITER)
									.append(fp.getExpiryDate());
				} else if (product instanceof ElectronicsProduct) {
					ElectronicsProduct ep = (ElectronicsProduct) product;
					sb.append("Electronics").append(DELIMITER)
									.append(ep.getId()).append(DELIMITER)
									.append(ep.getName()).append(DELIMITER)
									.append(ep.getPrice()).append(DELIMITER)
									.append(ep.getQuantity()).append(DELIMITER)
									.append(ep.getWarrantyPeriod());
				}
				writer.write(sb.toString());
				writer.newLine();
			}
			System.out.println("Đã lưu dữ liệu sản phẩm vào file: " + FILE_PATH);
		} catch (IOException e) {
			System.err.println("Lỗi khi ghi dữ liệu sản phẩm vào file: " + e.getMessage());
		}
	}
}