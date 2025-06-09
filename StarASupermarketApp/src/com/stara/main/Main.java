package com.stara.main;

import com.stara.product.Product;
import com.stara.product.ProductDataManager;
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

import com.stara.ui.ConsoleMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Import để sử dụng Optional
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
					manageOrders(); // Đổi tên từ processNewOrder
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

	/**
	 * Phương thức tiện ích để hiển thị tất cả sản phẩm.
	 */
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

	/**
	 * Xóa sản phẩm khỏi danh sách theo ID.
	 */
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

	/**
	 * Tìm kiếm sản phẩm theo tên và hiển thị kết quả.
	 */
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
	 * Tạo một đơn hàng mới. Đây là logic của processNewOrder cũ.
	 */
	private static void createNewOrder() {
		System.out.println("\n--- TẠO ĐƠN HÀNG MỚI ---");
		if (productList.isEmpty()) {
			System.out.println("Không thể tạo đơn hàng vì chưa có sản phẩm nào trong kho.");
			System.out.println("Vui lòng thêm sản phẩm trước.");
			return;
		}

		String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		Order.OrderBuilder orderBuilder = new Order.OrderBuilder(orderId);

		System.out.print("Nhập tên khách hàng (để trống nếu là khách vãng lai): ");
		String customerName = scanner.nextLine();
		orderBuilder.setCustomer(customerName);

		boolean addingItems = true;
		while (addingItems) {
			System.out.println("\n--- Chọn sản phẩm cho đơn hàng ---");
			displayAllProducts(); // Tái sử dụng phương thức hiển thị sản phẩm

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
			scanner.nextLine();

			if (quantityToAdd <= 0 || quantityToAdd > selectedProduct.getQuantity()) {
				System.out.println("Số lượng không hợp lệ hoặc vượt quá số lượng tồn kho.");
			} else {
				orderBuilder.addItem(selectedProduct, quantityToAdd);
				selectedProduct.reduceQuantity(quantityToAdd);
				System.out.println("Đã thêm sản phẩm vào đơn hàng.");
			}
		}

		System.out.print("Nhập số tiền giảm giá (0 nếu không có): ");
		double discount = scanner.nextDouble();
		scanner.nextLine();
		orderBuilder.addDiscount(discount);

		System.out.print("Nhập phương thức thanh toán (ví dụ: 'Thẻ tín dụng', 'Ví điện tử', 'Tiền mặt'): ");
		String paymentMethod = scanner.nextLine();
		orderBuilder.setPaymentMethod(paymentMethod);

		Order newOrder = orderBuilder.build();
		orderList.add(newOrder);
		System.out.println("\nĐơn hàng đã được tạo thành công!");
		newOrder.displayOrderDetails();
	}

	/**
	 * Hiển thị tất cả đơn hàng hiện có.
	 */
	private static void displayAllOrders() {
		System.out.println("\n--- Danh sách Đơn hàng Hiện có ---");
		if (orderList.isEmpty()) {
			System.out.println("Chưa có đơn hàng nào.");
		} else {
			System.out.println("ID Đơn hàng   | Khách hàng         | Tổng tiền  | PT Thanh toán");
			System.out.println("------------------------------------------------------------------");
			for (Order order : orderList) {
				System.out.printf("%-13s | %-18s | %,-10.0f | %s%n",
								order.getOrderId(),
								order.getCustomerName().isEmpty() ? "Khách vãng lai" : order.getCustomerName(),
								order.getFinalAmount(),
								order.getPaymentMethod());
			}
			System.out.println("------------------------------------------------------------------");
		}
	}

	/**
	 * Xóa đơn hàng khỏi danh sách theo ID.
	 */
	private static void deleteOrderById() {
		System.out.println("\n--- Xóa Đơn hàng ---");
		if (orderList.isEmpty()) {
			System.out.println("Không có đơn hàng nào để xóa.");
			return;
		}

		System.out.print("Nhập ID đơn hàng muốn xóa: ");
		String idToDelete = scanner.nextLine();

		// Optional<Order> để tìm đối tượng và kiểm tra sự tồn tại
		Optional<Order> orderToRemove = orderList.stream()
						.filter(o -> o.getOrderId().equalsIgnoreCase(idToDelete))
						.findFirst();

		if (orderToRemove.isPresent()) {
			// Hoàn trả số lượng sản phẩm vào kho khi đơn hàng bị xóa
			Order removedOrder = orderToRemove.get();
			for (OrderItem item : removedOrder.getItems()) {
				Product productInOrder = item.getProduct();
				if (productList.contains(productInOrder)) { // Đảm bảo sản phẩm vẫn còn trong productList
					productInOrder.setQuantity(productInOrder.getQuantity() + item.getQuantity());
					System.out.println("Đã hoàn trả " + item.getQuantity() + " '" + productInOrder.getName() + "' vào kho.");
				}
			}
			orderList.remove(removedOrder); // Xóa đơn hàng khỏi danh sách
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
			System.out.println("ID Đơn hàng   | Khách hàng         | Tổng tiền  | PT Thanh toán");
			System.out.println("------------------------------------------------------------------");
			for (Order order : foundOrders) {
				System.out.printf("%-13s | %-18s | %,-10.0f | %s%n",
								order.getOrderId(),
								order.getCustomerName().isEmpty() ? "Khách vãng lai" : order.getCustomerName(),
								order.getFinalAmount(),
								order.getPaymentMethod());
			}
			System.out.println("------------------------------------------------------------------");
		}
	}

	/**
	 * Xử lý thanh toán cho một đơn hàng cụ thể được chọn bằng ID.
	 */
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

	/**
	 * Tạo và hiển thị các loại báo cáo.
	 */
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