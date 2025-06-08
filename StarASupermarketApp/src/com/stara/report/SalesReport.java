package com.stara.report;

import com.stara.order.Order; // Giả sử Main sẽ truyền danh sách Order cho SalesReport
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
		super(generator, "Báo cáo Doanh thu");
		this.orders = orders;
	}

	@Override
	protected List<String> getReportData() {
		List<String> data = new ArrayList<>();
		double totalSales = 0;

		data.add(String.format("Ngày tạo báo cáo: %s", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		data.add("------------------------------------");
		data.add("Chi tiết các đơn hàng:");

		if (orders == null || orders.isEmpty()) {
			data.add("  Không có đơn hàng nào trong khoảng thời gian này.");
		} else {
			for (Order order : orders) {
				data.add(String.format("  Đơn hàng ID: %s | Khách hàng: %s | Tổng tiền: %,.0f VNĐ",
								order.getOrderId(), order.getCustomerName(), order.getFinalAmount()));
				totalSales += order.getFinalAmount();
			}
		}
		data.add("------------------------------------");
		data.add(String.format("Tổng doanh thu: %,.0f VNĐ", totalSales));
		return data;
	}
}