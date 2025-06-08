package com.stara.report;

import java.util.List;

/**
 * Report là Abstraction (lớp trừu tượng) trong mẫu Bridge.
 * Nó định nghĩa các chức năng chung của một báo cáo và chứa một tham chiếu đến ReportGenerator (Implementation).
 */
public abstract class Report {
	protected ReportGenerator generator; // Tham chiếu đến Implementation
	protected String title;

	public Report(ReportGenerator generator, String title) {
		this.generator = generator;
		this.title = title;
	}

	// Phương thức template để tạo và hiển thị báo cáo
	public void generateAndDisplayReport() {
		generator.generateHeader(title);
		generator.generateBody(getReportData()); // Phương thức trừu tượng để lấy dữ liệu cụ thể
		generator.generateFooter();
	}

	// Phương thức trừu tượng mà các lớp con phải triển khai để cung cấp dữ liệu báo cáo
	protected abstract List<String> getReportData();
}