package com.stara.payment;

/**
 * EWalletAPI là một hệ thống thanh toán ví điện tử bên ngoài (Adaptee).
 * Nó cũng có một giao diện cụ thể khác với PaymentProcessor của chúng ta.
 */
public class EWalletAPI {
	public void makePayment(String walletId, double amount) {
		System.out.println("📱 Đang xử lý thanh toán qua ví điện tử...");
		System.out.println("   ID Ví: " + walletId);
		System.out.printf("   Số tiền: %,.0f VNĐ%n", amount);
		// Logic thực tế để kết nối với cổng thanh toán ví điện tử
		System.out.println("   Thanh toán ví điện tử thành công.");
	}
}