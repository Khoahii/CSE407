package com.stara.payment;

/**
 * EWalletAdapter triển khai PaymentProcessor (Target interface) và
 * chứa một thể hiện của EWalletAPI (Adaptee).
 * Nó điều chỉnh giao diện của EWalletAPI để phù hợp với PaymentProcessor.
 */
public class EWalletAdapter implements PaymentProcessor {
	private EWalletAPI eWalletAPI;
	private String walletId; // Giả định ID ví được cung cấp khi tạo adapter

	public EWalletAdapter(String walletId) {
		this.eWalletAPI = new EWalletAPI();
		this.walletId = walletId;
	}

	@Override
	public void processPayment(double amount) {
		// Chuyển đổi lời gọi từ PaymentProcessor sang EWalletAPI
		eWalletAPI.makePayment(walletId, amount);
	}
}