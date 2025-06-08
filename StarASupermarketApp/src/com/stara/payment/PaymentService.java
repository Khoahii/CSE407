package com.stara.payment;

/**
 * PaymentService là Client trong mẫu Adapter.
 * Nó tương tác với PaymentProcessor (Target interface) để thực hiện thanh toán,
 * mà không cần biết về các lớp Adaptee cụ thể.
 */
public class PaymentService {
	private PaymentProcessor processor;

	public void setPaymentProcessor(PaymentProcessor processor) {
		this.processor = processor;
	}

	public void checkout(double amount) {
		if (processor == null) {
			System.err.println("Lỗi: Chưa thiết lập bộ xử lý thanh toán.");
			return;
		}
		System.out.println("Đang tiến hành thanh toán cho đơn hàng...");
		processor.processPayment(amount);
		System.out.println("Thanh toán hoàn tất.");
	}
}