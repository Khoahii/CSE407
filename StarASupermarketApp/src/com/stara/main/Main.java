package com.stara.main;

import com.stara.product.Product;
import com.stara.product.ProductDataManager; // Import ProductDataManager để tải/lưu sản phẩm
import com.stara.product.FoodProductFactory;
import com.stara.product.ElectronicsProductFactory;

import com.stara.order.Order;
import com.stara.order.OrderItem;

import com.stara.payment.PaymentService;
import com.stara.payment.CreditCardAdapter;
import com.stara.payment.EWalletAdapter;

import com.stara.report.ConsoleReportGenerator;
import com.stara.report.TextFileReportGenerator;
import com.stara.report.SalesReport;
import com.stara.report.InventoryReport;

import com.stara.ui.ConsoleMenu; // Import ConsoleMenu để hiển thị menu và lấy lựa chọn

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID; // Dùng để tạo ID duy nhất cho đơn hàng

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	private static List<Product> productList = new ArrayList<>(); // Danh sách sản phẩm
	private static List<Order> orderList = new ArrayList<>();     // Danh sách đơn hàng đã tạo

	public static void main(String[] args) {
		// 1. Tải dữ liệu sản phẩm khi khởi động ứng dụng
		System.out.println("Đang khởi động hệ thống quản lý siêu thị StarA...");
		productList = ProductDataManager.loadProducts();

		int choice;
		do {
			ConsoleMenu.displayMainMenu(); // Hiển thị menu chính
			choice = ConsoleMenu.getChoice(scanner); // Lấy lựa chọn từ người dùng

			switch (choice) {
				case 1:
					manageProducts(); // Quản lý sản phẩm
					break;
				case 2:
					processNewOrder(); // Xử lý đơn hàng mới
					break;
				case 3:
					handlePayments(); // Xử lý thanh toán (tổng hợp từ một đơn hàng giả định)
					break;
				case 4:
					generateReports(); // Tạo và xem báo cáo
					break;
				case 0:
					// 2. Lưu dữ liệu sản phẩm khi thoát ứng dụng
					ProductDataManager.saveProducts(productList);
					System.out.println("Cảm ơn bạn đã sử dụng hệ thống quản lý siêu thị StarA. Tạm biệt!");
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
			}
			if (choice != 0) { // Chỉ dừng lại để Enter nếu không phải thoát
				System.out.println("\nNhấn Enter để tiếp tục...");
				scanner.nextLine(); // Đợi người dùng nhấn Enter
			}
		} while (choice != 0);

		scanner.close(); // Đóng Scanner khi thoát ứng dụng
	}

	/**
	 * Quản lý các chức năng liên quan đến sản phẩm (thêm, hiển thị).
	 */
	private static void manageProducts() {
		int subChoice;
		do {
			ConsoleMenu.displayProductMenu(); // Hiển thị menu sản phẩm
			subChoice = ConsoleMenu.getChoice(scanner);

			switch (subChoice) {
				case 1: // Thêm sản phẩm
					System.out.println("\n--- Thêm Sản phẩm mới ---");
					System.out.println("Chọn loại sản phẩm muốn thêm:");
					System.out.println("1. Thực phẩm");
					System.out.println("2. Điện tử");
					System.out.print("Lựa chọn: ");
					int typeChoice = ConsoleMenu.getChoice(scanner); // Dùng ConsoleMenu để lấy lựa chọn

					System.out.print("Nhập ID sản phẩm: "); String id = scanner.nextLine();
					// Kiểm tra ID trùng lặp
					if (productList.stream().anyMatch(p -> p.getId().equalsIgnoreCase(id))) {
						System.out.println("ID sản phẩm đã tồn tại. Vui lòng nhập ID khác.");
						break;
					}

					System.out.print("Nhập tên sản phẩm: "); String name = scanner.nextLine();
					System.out.print("Nhập giá: "); double price = scanner.nextDouble();
					System.out.print("Nhập số lượng: "); int quantity = scanner.nextInt();
					scanner.nextLine(); // Consume newline

					Product newProduct = null;
					if (typeChoice == 1) {
						System.out.print("Nhập ngày hết hạn (VD: 2025-12-31): "); String expiry = scanner.nextLine();
						try {
							newProduct = new FoodProductFactory().createProduct(id, name, price, quantity, expiry);
						} catch (IllegalArgumentException e) {
							System.err.println("Lỗi khi tạo sản phẩm thực phẩm: " + e.getMessage());
						}
					} else if (typeChoice == 2) {
						System.out.print("Nhập thời gian bảo hành (VD: 12 tháng): "); String warranty = scanner.nextLine();
						try {
							newProduct = new ElectronicsProductFactory().createProduct(id, name, price, quantity, warranty);
						} catch (IllegalArgumentException e) {
							System.err.println("Lỗi khi tạo sản phẩm điện tử: " + e.getMessage());
						}
					} else {
						System.out.println("Loại sản phẩm không hợp lệ.");
					}

					if (newProduct != null) {
						productList.add(newProduct);
						System.out.println("Đã thêm sản phẩm thành công!");
					}
					break;
				case 2: // Hiển thị sản phẩm
					System.out.println("\n--- Danh sách Sản phẩm Hiện có ---");
					if (productList.isEmpty()) {
						System.out.println("Chưa có sản phẩm nào trong kho.");
					} else {
						System.out.println("  ID   | Tên sản phẩm         | Giá tiền   | Số lượng | Chi tiết đặc trưng");
						System.out.println("----------------------------------------------------------------------");
						for (Product p : productList) {
							p.displayInfo();
						}
						System.out.println("----------------------------------------------------------------------");
					}
					break;
				case 0:
					System.out.println("Quay lại menu chính.");
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ.");
			}
			if (subChoice != 0) {
				System.out.println("\nNhấn Enter để tiếp tục...");
				scanner.nextLine();
			}
		} while (subChoice != 0);
	}

	/**
	 * Xử lý tạo một đơn hàng mới.
	 */
	private static void processNewOrder() {
		System.out.println("\n--- TẠO ĐƠN HÀNG MỚI ---");
		if (productList.isEmpty()) {
			System.out.println("Không thể tạo đơn hàng vì chưa có sản phẩm nào trong kho.");
			System.out.println("Vui lòng thêm sản phẩm trước.");
			return;
		}

		// Tạo ID đơn hàng duy nhất
		String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		Order.OrderBuilder orderBuilder = new Order.OrderBuilder(orderId);

		System.out.print("Nhập tên khách hàng (để trống nếu là khách vãng lai): ");
		String customerName = scanner.nextLine();
		orderBuilder.setCustomer(customerName);

		boolean addingItems = true;
		while (addingItems) {
			System.out.println("\n--- Chọn sản phẩm cho đơn hàng ---");
			// Hiển thị sản phẩm để chọn
			System.out.println("  ID   | Tên sản phẩm         | Giá tiền   | Số lượng | Chi tiết đặc trưng");
			System.out.println("----------------------------------------------------------------------");
			for (Product p : productList) {
				p.displayInfo();
			}
			System.out.println("----------------------------------------------------------------------");

			System.out.print("Nhập ID sản phẩm muốn thêm vào đơn hàng (hoặc 'xong' để kết thúc): ");
			String productId = scanner.nextLine();

			if (productId.equalsIgnoreCase("xong")) {
				addingItems = false;
				continue;
			}

			Product selectedProduct = productList.stream()
							.filter(p -> p.getId().equalsIgnoreCase(productId))
							.findFirst()
							.orElse(null);

			if (selectedProduct == null) {
				System.out.println("Không tìm thấy sản phẩm với ID này. Vui lòng thử lại.");
				continue;
			}

			System.out.printf("Nhập số lượng cho sản phẩm '%s' (tồn kho: %d): ", selectedProduct.getName(), selectedProduct.getQuantity());
			int quantityToAdd = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			if (quantityToAdd <= 0 || quantityToAdd > selectedProduct.getQuantity()) {
				System.out.println("Số lượng không hợp lệ hoặc vượt quá số lượng tồn kho.");
			} else {
				orderBuilder.addItem(selectedProduct, quantityToAdd);
				selectedProduct.reduceQuantity(quantityToAdd); // Giảm số lượng tồn kho của sản phẩm
				System.out.println("Đã thêm sản phẩm vào đơn hàng.");
			}
		}

		System.out.print("Nhập số tiền giảm giá: ");
		double discount = scanner.nextDouble();
		scanner.nextLine(); // Consume newline
		orderBuilder.addDiscount(discount);

		System.out.print("Nhập phương thức thanh toán (ví dụ: 'Thẻ tín dụng', 'Ví điện tử', 'Tiền mặt'): ");
		String paymentMethod = scanner.nextLine();
		orderBuilder.setPaymentMethod(paymentMethod);

		Order newOrder = orderBuilder.build(); // Hoàn thành xây dựng đơn hàng
		orderList.add(newOrder); // Thêm đơn hàng vào danh sách
		System.out.println("\nĐơn hàng đã được tạo thành công!");
		newOrder.displayOrderDetails(); // Hiển thị chi tiết đơn hàng
	}

	/**
	 * Xử lý thanh toán cho một đơn hàng (chúng ta sẽ giả định là đơn hàng cuối cùng vừa tạo).
	 */
	private static void handlePayments() {
		System.out.println("\n--- XỬ LÝ THANH TOÁN ---");
		if (orderList.isEmpty()) {
			System.out.println("Không có đơn hàng nào để thanh toán. Vui lòng tạo đơn hàng trước.");
			return;
		}

		// Lấy đơn hàng cuối cùng để thanh toán (có thể phát triển để chọn đơn hàng cụ thể)
		Order latestOrder = orderList.get(orderList.size() - 1);
		System.out.printf("Đang xử lý thanh toán cho đơn hàng ID: %s (Tổng tiền: %,.0f VNĐ)%n",
						latestOrder.getOrderId(), latestOrder.getFinalAmount());

		ConsoleMenu.displayPaymentMenu(); // Hiển thị menu thanh toán
		int paymentChoice = ConsoleMenu.getChoice(scanner);

		PaymentService paymentService = new PaymentService();

		switch (paymentChoice) {
			case 1: // Thanh toán bằng Thẻ tín dụng
				System.out.print("Nhập số thẻ tín dụng (giả định): ");
				String cardNumber = scanner.nextLine();
				paymentService.setPaymentProcessor(new CreditCardAdapter(cardNumber));
				paymentService.checkout(latestOrder.getFinalAmount());
				break;
			case 2: // Thanh toán bằng Ví điện tử
				System.out.print("Nhập ID ví điện tử (giả định): ");
				String walletId = scanner.nextLine();
				paymentService.setPaymentProcessor(new EWalletAdapter(walletId));
				paymentService.checkout(latestOrder.getFinalAmount());
				break;
			case 0:
				System.out.println("Đã hủy thanh toán.");
				break;
			default:
				System.out.println("Phương thức thanh toán không hợp lệ.");
				break;
		}
	}

	/**
	 * Tạo và hiển thị các loại báo cáo.
	 */
	private static void generateReports() {
		int subChoice;
		do {
			ConsoleMenu.displayReportMenu(); // Hiển thị menu báo cáo
			subChoice = ConsoleMenu.getChoice(scanner);

			switch (subChoice) {
				case 1: // Báo cáo Doanh thu
					System.out.println("\n--- Báo cáo Doanh thu ---");
					// Sử dụng ConsoleReportGenerator để in ra console
					SalesReport salesReportConsole = new SalesReport(new ConsoleReportGenerator(), orderList);
					salesReportConsole.generateAndDisplayReport();

					// Sử dụng TextFileReportGenerator để ghi vào file
					SalesReport salesReportFile = new SalesReport(new TextFileReportGenerator("sales_report.txt"), orderList);
					salesReportFile.generateAndDisplayReport();
					break;
				case 2: // Báo cáo Tồn kho
					System.out.println("\n--- Báo cáo Tồn kho ---");
					// Sử dụng ConsoleReportGenerator để in ra console
					InventoryReport inventoryReportConsole = new InventoryReport(new ConsoleReportGenerator(), productList);
					inventoryReportConsole.generateAndDisplayReport();

					// Sử dụng TextFileReportGenerator để ghi vào file
					InventoryReport inventoryReportFile = new InventoryReport(new TextFileReportGenerator("inventory_report.txt"), productList);
					inventoryReportFile.generateAndDisplayReport();
					break;
				case 0:
					System.out.println("Quay lại menu chính.");
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ.");
			}
			if (subChoice != 0) {
				System.out.println("\nNhấn Enter để tiếp tục...");
				scanner.nextLine();
			}
		} while (subChoice != 0);
	}
}