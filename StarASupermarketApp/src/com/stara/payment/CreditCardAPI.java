package com.stara.payment;

/**
 * CreditCardAPI là một hệ thống thanh toán thẻ tín dụng bên ngoài (Adaptee).
 * Nó có một giao diện cụ thể không tương thích trực tiếp với PaymentProcessor của chúng ta.
 */
public class CreditCardAPI {
	public void chargeCard(double total, String cardNumber) {
		System.out.println("💳 Đang xử lý thanh toán thẻ tín dụng...");
		System.out.println("   Số thẻ: " + cardNumber);
		System.out.printf("   Số tiền: %,.0f VNĐ%n", total);
		// Logic thực tế để kết nối với cổng thanh toán thẻ tín dụng
		System.out.println("   Thanh toán thẻ tín dụng thành công.");
	}
}