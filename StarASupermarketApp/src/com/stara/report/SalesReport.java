package com.stara.report;

import com.stara.order.Order;
import com.stara.order.ConcreteOrderBuilder; // Import OrderItem để truy cập chi tiết sản phẩm trong đơn hàng
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * SalesReport là một Concrete Abstraction của Report.
 * Nó tạo báo cáo doanh thu.
 */
public class SalesReport extends Report {
	private List<Order> orders; // Dữ liệu đơn hàng để tạo báo cáo doanh thu

	// Constructor để truyền ReportGenerator và dữ liệu đơn hàng
	public SalesReport(ReportGenerator generator, List<Order> orders) {
		super(generator, "Báo cáo Doanh thu"); // Gọi constructor của lớp cha Report
		this.orders = orders;
	}

	@Override
	protected List<String> getReportData() {
		List<String> data = new ArrayList<>();
		double totalOverallSales = 0;

		data.add(String.format("Ngày tạo báo cáo: %s", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		data.add("======================================");
		data.add("           CHI TIẾT CÁC HÓA ĐƠN ĐÃ THANH TOÁN          ");
		data.add("======================================");

		if (orders == null || orders.isEmpty()) {
			data.add("  Không có đơn hàng nào trong hệ thống để báo cáo.");
		} else {
			for (int i = 0; i < orders.size(); i++) {
				Order order = orders.get(i);
				data.add(String.format("\n--- Hóa đơn số %d (ID: %s) ---", (i + 1), order.getOrderId()));
				data.add(String.format("Khách hàng: %s", order.getCustomerName().isEmpty() ? "Khách vãng lai" : order.getCustomerName()));
				data.add(String.format("Phương thức thanh toán: %s", order.getPaymentMethod()));
				data.add("Các mặt hàng:");
				data.add(String.format("  %-5s %-25s %-12s %-12s", "SL", "Sản phẩm", "Đơn giá", "Tổng"));
				data.add("  ----------------------------------------------------"); // Điều chỉnh độ dài gạch ngang

				double orderSubtotal = 0;
//				for (ConcreteOrderBuilder item : order.getItems()) {
//					data.add(String.format("  %-5d %-25s %,-12.0f %,-12.0f", // Đã thêm 12 vào %,-.0f
//									item.getQuantity(),
//									item.getProduct().getName(),
//									item.getProduct().getPrice(),
//									item.getTotalPrice()));
//					orderSubtotal += item.getTotalPrice();
//				}
				data.add("  ----------------------------------------------------"); // Điều chỉnh độ dài gạch ngang
				data.add(String.format("  Tổng phụ: %,-12.0f VNĐ", orderSubtotal)); // Đã thêm 12
				if (order.getDiscount() > 0) {
					data.add(String.format("  Giảm giá: %,-12.0f VNĐ", order.getDiscount())); // Đã thêm 12
				}
				data.add(String.format("  Tổng tiền hóa đơn: %,-12.0f VNĐ", order.getFinalAmount())); // Đã thêm 12
				data.add("--------------------------------------------------");
				totalOverallSales += order.getFinalAmount();
			}
		}
		data.add("\n======================================");
		data.add(String.format("   TỔNG DOANH THU TỪ TẤT CẢ HÓA ĐƠN: %,.0f VNĐ", totalOverallSales));
		data.add("======================================");
		return data;
	}
}