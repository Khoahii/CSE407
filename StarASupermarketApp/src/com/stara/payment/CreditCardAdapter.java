package com.stara.payment;

/**
 * CreditCardAdapter triển khai PaymentProcessor (Target interface) và
 * chứa một thể hiện của CreditCardAPI (Adaptee).
 * Nó điều chỉnh giao diện của CreditCardAPI để phù hợp với PaymentProcessor.
 */
public class CreditCardAdapter implements PaymentProcessor {
	private CreditCardAPI creditCardAPI;
	private String cardNumber; // Giả định số thẻ được cung cấp khi tạo adapter

	public CreditCardAdapter(String cardNumber) {
		this.creditCardAPI = new CreditCardAPI();
		this.cardNumber = cardNumber;
	}

	@Override
	public void processPayment(double amount) {
		// Chuyển đổi lời gọi từ PaymentProcessor sang CreditCardAPI
		creditCardAPI.chargeCard(amount, cardNumber);
	}
}