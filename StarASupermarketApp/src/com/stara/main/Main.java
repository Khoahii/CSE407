package com.stara.main;

import com.stara.ui.ConsoleMenu;
import com.stara.order.Order; // Lớp Order từ package order của bạn
import com.stara.delivery.factory.DeliveryServiceFactory;
import com.stara.delivery.factory.StandardDeliveryFactory;
import com.stara.delivery.factory.ExpressDeliveryFactory;
import com.stara.delivery.product.DeliveryVehicle;
import com.stara.delivery.product.RoutePlanner;

import java.util.List;
import java.util.Scanner;

public class Main {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		int mainChoice;
		do {
			ConsoleMenu.displayMainMenu();
			mainChoice = ConsoleMenu.getChoice(scanner);

			switch (mainChoice) {
				case 1:
					// Logic Quản lý Sản phẩm
					handleProductManagement();
					break;
				case 2:
					// Logic Quản lý Đơn hàng (bao gồm vận chuyển)
					handleOrderManagement();
					break;
				case 3:
					// Logic Xử lý Thanh toán
					handlePaymentProcessing();
					break;
				case 4:
					// Logic Xem Báo cáo
					handleReportViewing();
					break;
				case 0:
					System.out.println("Thoát ứng dụng. Tạm biệt!");
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
			}
		} while (mainChoice != 0);

		scanner.close();
	}

	// --- Các phương thức xử lý menu chính ---

	private static void handleProductManagement() {
		int choice;
		do {
			ConsoleMenu.displayProductMenu();
			choice = ConsoleMenu.getChoice(scanner);
			switch (choice) {
				case 1: System.out.println("Thêm sản phẩm mới..."); /* logic của bạn */ break;
				case 2: System.out.println("Hiển thị tất cả sản phẩm..."); /* logic của bạn */ break;
				case 3: System.out.println("Xóa sản phẩm..."); /* logic của bạn */ break;
				case 4: System.out.println("Tìm kiếm sản phẩm..."); /* logic của bạn */ break;
				case 0: break;
				default: System.out.println("Lựa chọn không hợp lệ.");
			}
		} while (choice != 0);
	}

	private static void handleOrderManagement() {
		int choice;
		do {
			ConsoleMenu.displayOrderManagementMenu();
			choice = ConsoleMenu.getChoice(scanner);
			switch (choice) {
				case 1: System.out.println("Tạo đơn hàng mới..."); /* logic của bạn */ break;
				case 2: System.out.println("Hiển thị tất cả đơn hàng..."); /* logic của bạn */ break;
				case 3: System.out.println("Xóa đơn hàng..."); /* logic của bạn */ break;
				case 4: System.out.println("Tìm kiếm đơn hàng..."); /* logic của bạn */ break;
				case 5:
					handleDeliveryManagement(); // GỌI ĐẾN LOGIC QUẢN LÝ VẬN CHUYỂN
					break;
				case 0: break;
				default: System.out.println("Lựa chọn không hợp lệ.");
			}
		} while (choice != 0);
	}

	private static void handlePaymentProcessing() {
		int choice;
		do {
			ConsoleMenu.displayPaymentMenu();
			choice = ConsoleMenu.getChoice(scanner);
			switch (choice) {
				case 1: System.out.println("Thanh toán bằng Thẻ tín dụng..."); /* logic của bạn */ break;
				case 2: System.out.println("Thanh toán bằng Ví điện tử..."); /* logic của bạn */ break;
				case 0: break;
				default: System.out.println("Lựa chọn không hợp lệ.");
			}
		} while (choice != 0);
	}

	private static void handleReportViewing() {
		int choice;
		do {
			ConsoleMenu.displayReportMenu();
			choice = ConsoleMenu.getChoice(scanner);
			switch (choice) {
				case 1: System.out.println("Báo cáo Doanh thu..."); /* logic của bạn */ break;
				case 2: System.out.println("Báo cáo Tồn kho..."); /* logic của bạn */ break;
				case 0: break;
				default: System.out.println("Lựa chọn không hợp lệ.");
			}
		} while (choice != 0);
	}

	// --- LOGIC XỬ LÝ VẬN CHUYỂN MỚI (Tích hợp trực tiếp vào Main) ---
	private static void handleDeliveryManagement() {
		int choice;
		do {
			ConsoleMenu.displayDeliveryManagementMenu(); // Hiển thị menu vận chuyển mới
			choice = ConsoleMenu.getChoice(scanner);

			switch (choice) {
				case 1:
					processStandardDelivery();
					break;
				case 2:
					processExpressDelivery();
					break;
				case 0:
					System.out.println("Quay lại Menu Quản lý Đơn hàng...");
					break;
				default:
					System.out.println("Lựa chọn vận chuyển không hợp lệ. Vui lòng thử lại.");
			}
		} while (choice != 0);
	}

	private static void processStandardDelivery() {
		System.out.println("\n===== Đang xử lý Giao hàng tiêu chuẩn =====");
		System.out.print("Nhập ID Đơn hàng cho Giao hàng tiêu chuẩn: ");
		String orderId = scanner.nextLine();
		System.out.print("Nhập Địa chỉ Khách hàng: ");
		String address = scanner.nextLine();
		System.out.print("Nhập Tổng số tiền: ");
		double amount = scanner.nextDouble();
		scanner.nextLine(); // Tiêu thụ ký tự xuống dòng

		// Tạo đối tượng Order (giả sử Order nằm trong com.stara.order)
		Order standardOrder = new Order(orderId, address, amount, "Standard");

		// Sử dụng Abstract Factory để tạo các thành phần
		DeliveryServiceFactory standardFactory = new StandardDeliveryFactory();
		DeliveryVehicle standardVehicle = standardFactory.createVehicle();
		RoutePlanner standardPlanner = standardFactory.createRoutePlanner();

		// Thực hiện logic giao hàng
		List<String> route = standardPlanner.planRoute(" ");
		System.out.println("  Lộ trình đã lập: " + String.join(", ", route));
		standardVehicle.deliver(standardOrder);
		System.out.println("===== Giao hàng tiêu chuẩn đã được điều phối =====");
	}

	private static void processExpressDelivery() {
		System.out.println("\n===== Đang xử lý Giao hàng nhanh =====");
		System.out.print("Nhập ID Đơn hàng cho Giao hàng nhanh: ");
		String orderId = scanner.nextLine();
		System.out.print("Nhập Địa chỉ Khách hàng: ");
		String address = scanner.nextLine();
		System.out.print("Nhập Tổng số tiền: ");
		double amount = scanner.nextDouble();
		scanner.nextLine(); // Tiêu thụ ký tự xuống dòng

		// Tạo đối tượng Order (giả sử Order nằm trong com.stara.order)
		Order expressOrder = new Order(orderId, address, amount, "Express");

		// Sử dụng Abstract Factory để tạo các thành phần
		DeliveryServiceFactory expressFactory = new ExpressDeliveryFactory();
		DeliveryVehicle expressVehicle = expressFactory.createVehicle();
		RoutePlanner expressPlanner = expressFactory.createRoutePlanner();

		// Thực hiện logic giao hàng
		List<String> route = expressPlanner.planRoute("");
		System.out.println("  Lộ trình đã lập: " + String.join(", ", route));
		expressVehicle.deliver(expressOrder);
		System.out.println("===== Giao hàng nhanh đã được điều phối =====");
	}
}