package com.stara.report;

import com.stara.product.Product; // Giả sử Main sẽ truyền danh sách Product cho InventoryReport
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * InventoryReport là một Concrete Abstraction của Report.
 * Nó tạo báo cáo tồn kho.
 */
public class InventoryReport extends Report {
	private List<Product> products; // Dữ liệu sản phẩm để tạo báo cáo tồn kho

	// Constructor để truyền ReportGenerator và dữ liệu sản phẩm
	public InventoryReport(ReportGenerator generator, List<Product> products) {
		super(generator, "Báo cáo Tồn kho");
		this.products = products;
	}

	@Override
	protected List<String> getReportData() {
		List<String> data = new ArrayList<>();
		int totalItemsInStock = 0;

		data.add(String.format("Ngày tạo báo cáo: %s", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		data.add("------------------------------------");
		data.add(String.format("%-10s %-25s %-10s", "ID", "Tên Sản phẩm", "Số lượng"));
		data.add("------------------------------------");

		if (products == null || products.isEmpty()) {
			data.add("  Không có sản phẩm nào trong kho.");
		} else {
			for (Product product : products) {
				data.add(String.format("%-10s %-25s %-10d", product.getId(), product.getName(), product.getQuantity()));
				totalItemsInStock += product.getQuantity();
			}
		}
		data.add("------------------------------------");
		data.add(String.format("Tổng số mặt hàng trong kho: %d", totalItemsInStock));
		return data;
	}
}