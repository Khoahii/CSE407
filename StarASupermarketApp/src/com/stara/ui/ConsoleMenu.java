package com.stara.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu {

	/**
	 * Hiển thị menu chính của ứng dụng.
	 */
	public static void displayMainMenu() {
		System.out.println("\n--- HỆ THỐNG QUẢN LÝ SIÊU THỊ STARA ---");
		System.out.println("1. Quản lý Sản phẩm");
		System.out.println("2. Xử lý Đơn hàng mới");
		System.out.println("3. Xử lý Thanh toán");
		System.out.println("4. Xem Báo cáo");
		System.out.println("0. Thoát ứng dụng");
		System.out.print("Vui lòng chọn chức năng: ");
	}

	/**
	 * Hiển thị menu quản lý sản phẩm.
	 */
	public static void displayProductMenu() {
		System.out.println("\n--- QUẢN LÝ SẢN PHẨM ---");
		System.out.println("1. Thêm Sản phẩm mới");
		System.out.println("2. Hiển thị tất cả Sản phẩm");
		// Có thể thêm: 3. Cập nhật Sản phẩm, 4. Xóa Sản phẩm
		System.out.println("0. Quay lại Menu chính");
		System.out.print("Vui lòng chọn chức năng: ");
	}

	/**
	 * Hiển thị menu thanh toán.
	 */
	public static void displayPaymentMenu() {
		System.out.println("\n--- XỬ LÝ THANH TOÁN ---");
		System.out.println("1. Thanh toán bằng Thẻ tín dụng");
		System.out.println("2. Thanh toán bằng Ví điện tử");
		System.out.println("0. Quay lại Menu chính");
		System.out.print("Vui lòng chọn phương thức thanh toán: ");
	}

	/**
	 * Hiển thị menu báo cáo.
	 */
	public static void displayReportMenu() {
		System.out.println("\n--- XEM BÁO CÁO ---");
		System.out.println("1. Báo cáo Doanh thu");
		System.out.println("2. Báo cáo Tồn kho");
		System.out.println("0. Quay lại Menu chính");
		System.out.print("Vui lòng chọn loại báo cáo: ");
	}

	/**
	 * Lấy lựa chọn số nguyên từ người dùng.
	 * @param scanner Đối tượng Scanner để đọc đầu vào.
	 * @return Lựa chọn của người dùng.
	 */
	public static int getChoice(Scanner scanner) {
		int choice = -1;
		try {
			choice = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập một số.");
			scanner.nextLine(); // Tiêu thụ đầu vào không hợp lệ
		}
		scanner.nextLine(); // Tiêu thụ ký tự xuống dòng còn lại sau nextInt()
		return choice;
	}
}