// File: src/main/java/com/stara/main/Main.java
package com.stara.main;

import com.stara.product.*;
import com.stara.order.Order;
import com.stara.order.OrderBuilder; // Import giao diện OrderBuilder
import com.stara.order.ConcreteOrderBuilder; // Import lớp ConcreteOrderBuilder

import com.stara.payment.PaymentService;
import com.stara.payment.CreditCardAdapter;
import com.stara.payment.EWalletAdapter;

import com.stara.report.ConsoleReportGenerator;
import com.stara.report.TextFileReportGenerator;
import com.stara.report.SalesReport;
import com.stara.report.InventoryReport;

import com.stara.ui.ConsoleMenu;

import java.time.LocalDateTime; // Thêm import này
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	private static List<Product> productList = new ArrayList<>();
	private static List<Order> orderList = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("Đang khởi động hệ thống quản lý siêu thị StarA...");
		productList = ProductDataManager.loadProducts();

		int choice;
		do {
			ConsoleMenu.displayMainMenu();
			choice = ConsoleMenu.getChoice(scanner);

			switch (choice) {
				case 1:
					manageProducts();
					break;
				case 2:
					manageOrders();
					break;
				case 3:
					handlePayments();
					break;
				case 4:
					generateReports();
					break;
				case 0:
					ProductDataManager.saveProducts(productList);
					System.out.println("Cảm ơn bạn đã sử dụng hệ thống quản lý siêu thị StarA. Tạm biệt!");
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
			}
			if (choice != 0) {
				System.out.println("\nNhấn Enter để tiếp tục...");
				scanner.nextLine();
			}
		} while (choice != 0);

		scanner.close();
	}

	// --- Các phương thức quản lý sản phẩm (giữ nguyên) ---
	/**
	 * Quản lý các chức năng liên quan đến sản phẩm (thêm, hiển thị, xóa, tìm kiếm).
	 */
	private static void manageProducts() {
		int subChoice;
		do {
			ConsoleMenu.displayProductMenu();
			subChoice = ConsoleMenu.getChoice(scanner);

			switch (subChoice) {
				case 1: // Thêm sản phẩm
					System.out.println("\n--- Thêm Sản phẩm mới ---");
					System.out.println("Chọn loại sản phẩm muốn thêm:");
					System.out.println("1. Thực phẩm");
					System.out.println("2. Điện tử");
					System.out.print("Lựa chọn: ");
					int typeChoice = ConsoleMenu.getChoice(scanner);

					System.out.print("Nhập ID sản phẩm: "); String id = scanner.nextLine();
					if (productList.stream().anyMatch(p -> p.getId().equalsIgnoreCase(id))) {
						System.out.println("ID sản phẩm đã tồn tại. Vui lòng nhập ID khác.");
						break;
					}

					System.out.print("Nhập tên sản phẩm: "); String name = scanner.nextLine();
					System.out.print("Nhập giá: "); double price = scanner.nextDouble();
					System.out.print("Nhập số lượng: "); int quantity = scanner.nextInt();
					scanner.nextLine();

					Product newProduct = null;
					// Khởi tạo ProductFactory ở đây để sử dụng phương thức someOperation() nếu có
					ProductFactory productFactory = null;

					if (typeChoice == 1) {
						System.out.print("Nhập ngày hết hạn (VD: 2025-12-31): "); String expiry = scanner.nextLine();
						try {
							productFactory = new FoodProductFactory(); // Khởi tạo Factory cụ thể
							// Sử dụng phương thức createAndConfirmProduct() hoặc processAndStageNewProduct()
//							newProduct = productFactory.createAndConfirmProduct(id, name, price, quantity, expiry);
							// Hoặc: newProduct = productFactory.processAndStageNewProduct(id, name, price, quantity, expiry);
						} catch (IllegalArgumentException e) {
							System.err.println("Lỗi khi tạo sản phẩm thực phẩm: " + e.getMessage());
						}
					} else if (typeChoice == 2) {
						System.out.print("Nhập thời gian bảo hành (VD: 12 tháng): "); String warranty = scanner.nextLine();
						try {
							productFactory = new ElectronicsProductFactory(); // Khởi tạo Factory cụ thể
							// Sử dụng phương thức createAndConfirmProduct() hoặc processAndStageNewProduct()
//							newProduct = productFactory.createAndConfirmProduct(id, name, price, quantity, warranty);
							// Hoặc: newProduct = productFactory.processAndStageNewProduct(id, name, price, quantity, warranty);
						} catch (IllegalArgumentException e) {
							System.err.println("Lỗi khi tạo sản phẩm điện tử: " + e.getMessage());
						}
					} else {
						System.out.println("Loại sản phẩm không hợp lệ.");
					}

					// Xóa phần confirm cũ, vì nó đã được xử lý bên trong phương thức của Factory
					if (newProduct != null) {
						productList.add(newProduct);
						// Phương thức createAndConfirmProduct() đã in xác nhận.
						// Nếu dùng processAndStageNewProduct(), bạn có thể in thêm xác nhận cuối cùng ở đây.
						System.out.println("Sản phẩm đã được thêm vào danh sách quản lý.");
					}
					break;
				case 2: // Hiển thị sản phẩm
					displayAllProducts();
					break;
				case 3: // Xóa sản phẩm theo ID
					deleteProductById();
					break;
				case 4: // Tìm kiếm sản phẩm theo tên
					searchProductByName();
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

	// Phương thức displayAllProducts, deleteProductById, searchProductByName (giữ nguyên)
	private static void displayAllProducts() {
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
	}

	private static void deleteProductById() {
		System.out.println("\n--- Xóa Sản phẩm ---");
		if (productList.isEmpty()) {
			System.out.println("Không có sản phẩm nào để xóa.");
			return;
		}

		System.out.print("Nhập ID sản phẩm muốn xóa: ");
		String idToDelete = scanner.nextLine();

		boolean removed = productList.removeIf(p -> p.getId().equalsIgnoreCase(idToDelete));

		if (removed) {
			System.out.println("Đã xóa sản phẩm có ID '" + idToDelete + "' thành công.");
		} else {
			System.out.println("Không tìm thấy sản phẩm có ID '" + idToDelete + "'.");
		}
	}

	private static void searchProductByName() {
		System.out.println("\n--- Tìm kiếm Sản phẩm ---");
		if (productList.isEmpty()) {
			System.out.println("Chưa có sản phẩm nào trong kho để tìm kiếm.");
			return;
		}

		System.out.print("Nhập tên (hoặc một phần tên) sản phẩm muốn tìm: ");
		String searchTerm = scanner.nextLine().toLowerCase();

		List<Product> foundProducts = productList.stream()
						.filter(p -> p.getName().toLowerCase().contains(searchTerm))
						.collect(Collectors.toList());

		if (foundProducts.isEmpty()) {
			System.out.println("Không tìm thấy sản phẩm nào có tên chứa '" + searchTerm + "'.");
		} else {
			System.out.println("\n--- Kết quả tìm kiếm cho '" + searchTerm + "' ---");
			System.out.println("  ID   | Tên sản phẩm         | Giá tiền   | Số lượng | Chi tiết đặc trưng");
			System.out.println("----------------------------------------------------------------------");
			for (Product p : foundProducts) {
				p.displayInfo();
			}
			System.out.println("----------------------------------------------------------------------");
		}
	}


	/**
	 * Quản lý các chức năng liên quan đến đơn hàng (tạo, hiển thị, xóa, tìm kiếm).
	 * Đã điều chỉnh để sử dụng OrderBuilder mới.
	 */
	private static void manageOrders() {
		int subChoice;
		do {
			ConsoleMenu.displayOrderManagementMenu(); // Hiển thị menu quản lý đơn hàng
			subChoice = ConsoleMenu.getChoice(scanner);

			switch (subChoice) {
				case 1: // Tạo đơn hàng mới
					createNewOrder();
					break;
				case 2: // Hiển thị tất cả đơn hàng
					displayAllOrders();
					break;
				case 3: // Xóa đơn hàng theo ID
					deleteOrderById();
					break;
				case 4: // Tìm kiếm đơn hàng theo tên khách hàng
					searchOrderByCustomerName();
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
	 * Tạo một đơn hàng mới sử dụng Builder Pattern.
	 */
	private static void createNewOrder() {
		System.out.println("\n--- TẠO ĐƠN HÀNG MỚI ---");
		if (productList.isEmpty()) {
			System.out.println("Không thể tạo đơn hàng vì chưa có sản phẩm nào trong kho.");
			System.out.println("Vui lòng thêm sản phẩm trước.");
			return;
		}

		// Khởi tạo ConcreteOrderBuilder
		OrderBuilder builder = new ConcreteOrderBuilder();
		builder.reset(); // Đảm bảo Builder ở trạng thái sạch cho đơn hàng mới

		String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		builder.withOrderId(orderId); // Thiết lập Order ID

		System.out.print("Nhập tên khách hàng (để trống nếu là khách vãng lai): ");
		String customerName = scanner.nextLine();
		builder.withCustomer(customerName.isEmpty() ? "Khách vãng lai" : customerName); // Gán tên mặc định nếu rỗng

		// --- XỬ LÝ DANH SÁCH SẢN PHẨM TRONG ĐƠN HÀNG ---
		// (Lưu ý: Logic này sẽ phức tạp hơn nếu Order của bạn cần quản lý List<OrderItem>
		// và bạn muốn Builder xây dựng cả OrderItem. Hiện tại, chúng ta tập trung vào thuộc tính Order.)
		// Để giữ đơn giản cho Builder chỉ xây dựng các thuộc tính của Order,
		// chúng ta sẽ tính tổng tiền và chiết khấu TỪ BÊN NGOÀI Builder, rồi truyền vào.
		// Nếu bạn muốn Builder quản lý OrderItem, bạn cần thêm List<OrderItem> vào Order và các phương thức tương ứng vào Builder.

		double currentTotalAmount = 0.0;
		// Để đơn giản, phần thêm sản phẩm vào đơn hàng (OrderItems) chưa được tích hợp vào OrderBuilder
		// của Order hiện tại (vì Order của bạn không có List<OrderItem>).
		// Chúng ta sẽ giả định tính toán totalAmount và discount từ đây, rồi truyền vào Builder.
		List<String> selectedProductDetails = new ArrayList<>(); // Dùng để hiển thị chi tiết sản phẩm đã chọn

		boolean addingItems = true;
		while (addingItems) {
			System.out.println("\n--- Chọn sản phẩm cho đơn hàng ---");
			displayAllProducts();

			System.out.print("Nhập ID sản phẩm muốn thêm vào đơn hàng (hoặc 'xong' để kết thúc): ");
			String productId = scanner.nextLine();

			if (productId.equalsIgnoreCase("xong")) {
				addingItems = false;
				continue;
			}

			Optional<Product> selectedProductOpt = productList.stream()
							.filter(p -> p.getId().equalsIgnoreCase(productId))
							.findFirst();

			if (!selectedProductOpt.isPresent()) {
				System.out.println("Không tìm thấy sản phẩm với ID này. Vui lòng thử lại.");
				continue;
			}
			Product selectedProduct = selectedProductOpt.get();

			System.out.printf("Nhập số lượng cho sản phẩm '%s' (tồn kho: %d): ", selectedProduct.getName(), selectedProduct.getQuantity());
			int quantityToAdd = scanner.nextInt();
			scanner.nextLine();

			if (quantityToAdd <= 0 || quantityToAdd > selectedProduct.getQuantity()) {
				System.out.println("Số lượng không hợp lệ hoặc vượt quá số lượng tồn kho.");
			} else {
				currentTotalAmount += selectedProduct.getPrice() * quantityToAdd;
				selectedProduct.setQuantity(selectedProduct.getQuantity() - quantityToAdd); // Giảm số lượng tồn kho
				selectedProductDetails.add(String.format("  - %s (x%d) : %.2f VNĐ", selectedProduct.getName(), quantityToAdd, selectedProduct.getPrice() * quantityToAdd));
				System.out.println("Đã thêm sản phẩm vào tổng tiền đơn hàng.");
			}
		}

		System.out.println("\n--- Chi tiết các sản phẩm đã chọn ---");
		if (selectedProductDetails.isEmpty()) {
			System.out.println("Không có sản phẩm nào được chọn.");
			builder.withAmounts(0.0, 0.0); // Đặt tổng tiền và chiết khấu về 0
		} else {
			selectedProductDetails.forEach(System.out::println);
			System.out.printf("Tổng tiền tạm thời: %.2f VNĐ%n", currentTotalAmount);

			System.out.print("Nhập số tiền giảm giá (0 nếu không có): ");
			double discount = scanner.nextDouble();
			scanner.nextLine();
			builder.withAmounts(currentTotalAmount, discount); // Thiết lập tổng tiền và chiết khấu
		}

		System.out.print("Nhập phương thức thanh toán (ví dụ: 'Thẻ tín dụng', 'Ví điện tử', 'Tiền mặt'): ");
		String paymentMethod = scanner.nextLine();
		System.out.print("Nhập trạng thái thanh toán (ví dụ: 'PAID', 'PENDING', 'FAILED'): ");
		String paymentStatus = scanner.nextLine();
		builder.withPaymentDetails(paymentMethod, paymentStatus); // Thiết lập chi tiết thanh toán

		builder.withOrderDate(LocalDateTime.now()); // Thiết lập ngày tạo đơn hàng

		Order newOrder = builder.getResult(); // Lấy đối tượng Order đã hoàn thành từ Builder
		orderList.add(newOrder);
		System.out.println("\nĐơn hàng đã được tạo thành công!");
		System.out.println(newOrder); // In thông tin đơn hàng đã tạo
	}

	/**
	 * Hiển thị tất cả đơn hàng hiện có.
	 */
	private static void displayAllOrders() {
		System.out.println("\n--- Danh sách Đơn hàng Hiện có ---");
		if (orderList.isEmpty()) {
			System.out.println("Chưa có đơn hàng nào.");
		} else {
			System.out.println("ID Đơn hàng   | Khách hàng         | Tổng tiền  | PT Thanh toán | Trạng thái");
			System.out.println("--------------------------------------------------------------------------------");
			for (Order order : orderList) {
				System.out.printf("%-13s | %-18s | %,-10.0f | %-13s | %s%n",
								order.getOrderId(),
								order.getCustomerName().isEmpty() ? "Khách vãng lai" : order.getCustomerName(),
								order.getFinalAmount(),
								order.getPaymentMethod(),
								order.getPaymentStatus());
			}
			System.out.println("--------------------------------------------------------------------------------");
		}
	}

	/**
	 * Xóa đơn hàng khỏi danh sách theo ID.
	 * Cần lưu ý: Nếu bạn có OrderItem, logic hoàn trả tồn kho sẽ phức tạp hơn.
	 * Hiện tại, logic này không hoàn trả tồn kho sản phẩm, vì Order không chứa OrderItem.
	 */
	private static void deleteOrderById() {
		System.out.println("\n--- Xóa Đơn hàng ---");
		if (orderList.isEmpty()) {
			System.out.println("Không có đơn hàng nào để xóa.");
			return;
		}

		System.out.print("Nhập ID đơn hàng muốn xóa: ");
		String idToDelete = scanner.nextLine();

		Optional<Order> orderToRemoveOpt = orderList.stream()
						.filter(o -> o.getOrderId().equalsIgnoreCase(idToDelete))
						.findFirst();

		if (orderToRemoveOpt.isPresent()) {
			orderList.remove(orderToRemoveOpt.get());
			System.out.println("Đã xóa đơn hàng có ID '" + idToDelete + "' thành công.");
		} else {
			System.out.println("Không tìm thấy đơn hàng có ID '" + idToDelete + "'.");
		}
	}


	/**
	 * Tìm kiếm đơn hàng theo tên khách hàng.
	 */
	private static void searchOrderByCustomerName() {
		System.out.println("\n--- Tìm kiếm Đơn hàng theo tên khách hàng ---");
		if (orderList.isEmpty()) {
			System.out.println("Chưa có đơn hàng nào để tìm kiếm.");
			return;
		}

		System.out.print("Nhập tên (hoặc một phần tên) khách hàng muốn tìm: ");
		String searchTerm = scanner.nextLine().toLowerCase();

		List<Order> foundOrders = orderList.stream()
						.filter(o -> o.getCustomerName().toLowerCase().contains(searchTerm))
						.collect(Collectors.toList());

		if (foundOrders.isEmpty()) {
			System.out.println("Không tìm thấy đơn hàng nào của khách hàng có tên chứa '" + searchTerm + "'.");
		} else {
			System.out.println("\n--- Kết quả tìm kiếm cho khách hàng '" + searchTerm + "' ---");
			System.out.println("ID Đơn hàng   | Khách hàng         | Tổng tiền  | PT Thanh toán | Trạng thái");
			System.out.println("--------------------------------------------------------------------------------");
			for (Order order : foundOrders) {
				System.out.printf("%-13s | %-18s | %,-10.0f | %-13s | %s%n",
								order.getOrderId(),
								order.getCustomerName().isEmpty() ? "Khách vãng lai" : order.getCustomerName(),
								order.getFinalAmount(),
								order.getPaymentMethod(),
								order.getPaymentStatus());
			}
			System.out.println("--------------------------------------------------------------------------------");
		}
	}

	// --- Các phương thức xử lý thanh toán và tạo báo cáo (giữ nguyên) ---
	private static void handlePayments() {
		System.out.println("\n--- XỬ LÝ THANH TOÁN ---");
		if (orderList.isEmpty()) {
			System.out.println("Không có đơn hàng nào để thanh toán. Vui lòng tạo đơn hàng trước.");
			return;
		}

		displayAllOrders(); // Hiển thị danh sách đơn hàng để người dùng chọn
		System.out.print("Nhập ID đơn hàng muốn thanh toán: ");
		String orderIdToPay = scanner.nextLine();

		Optional<Order> orderToPayOpt = orderList.stream()
						.filter(o -> o.getOrderId().equalsIgnoreCase(orderIdToPay))
						.findFirst();

		if (!orderToPayOpt.isPresent()) {
			System.out.println("Không tìm thấy đơn hàng với ID '" + orderIdToPay + "'.");
			return;
		}

		Order orderToPay = orderToPayOpt.get();
		System.out.printf("Đang xử lý thanh toán cho đơn hàng ID: %s (Tổng tiền: %,.0f VNĐ)%n",
						orderToPay.getOrderId(), orderToPay.getFinalAmount());

		ConsoleMenu.displayPaymentMenu();
		int paymentChoice = ConsoleMenu.getChoice(scanner);

		PaymentService paymentService = new PaymentService();

		switch (paymentChoice) {
			case 1:
				System.out.print("Nhập số thẻ tín dụng (giả định): ");
				String cardNumber = scanner.nextLine();
				paymentService.setPaymentProcessor(new CreditCardAdapter(cardNumber));
				paymentService.checkout(orderToPay.getFinalAmount());
				break;
			case 2:
				System.out.print("Nhập ID ví điện tử (giả định): ");
				String walletId = scanner.nextLine();
				paymentService.setPaymentProcessor(new EWalletAdapter(walletId));
				paymentService.checkout(orderToPay.getFinalAmount());
				break;
			case 0:
				System.out.println("Đã hủy thanh toán.");
				break;
			default:
				System.out.println("Phương thức thanh toán không hợp lệ.");
				break;
		}
	}

	private static void generateReports() {
		int subChoice;
		do {
			ConsoleMenu.displayReportMenu();
			subChoice = ConsoleMenu.getChoice(scanner);

			switch (subChoice) {
				case 1: // Báo cáo Doanh thu
					System.out.println("\n--- Báo cáo Doanh thu ---");
					SalesReport salesReportConsole = new SalesReport(new ConsoleReportGenerator(), orderList);
					salesReportConsole.generateAndDisplayReport();

					SalesReport salesReportFile = new SalesReport(new TextFileReportGenerator("sales_report.txt"), orderList);
					salesReportFile.generateAndDisplayReport();
					break;
				case 2: // Báo cáo Tồn kho
					System.out.println("\n--- Báo cáo Tồn kho ---");
					InventoryReport inventoryReportConsole = new InventoryReport(new ConsoleReportGenerator(), productList);
					inventoryReportConsole.generateAndDisplayReport();

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