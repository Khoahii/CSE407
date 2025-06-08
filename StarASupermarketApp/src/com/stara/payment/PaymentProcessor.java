package com.stara.payment;

/**
 * PaymentProcessor là giao diện mục tiêu (Target Interface) mà hệ thống của chúng ta sử dụng.
 * Mọi cổng thanh toán cần phải tuân thủ giao diện này.
 */
public interface PaymentProcessor {
	void processPayment(double amount);
}