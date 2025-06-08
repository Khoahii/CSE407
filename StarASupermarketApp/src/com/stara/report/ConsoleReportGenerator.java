package com.stara.report;

import java.util.List;

/**
 * ConsoleReportGenerator là một Concrete Implementation của ReportGenerator.
 * Nó tạo báo cáo và hiển thị trực tiếp trên console.
 */
public class ConsoleReportGenerator implements ReportGenerator {
	@Override
	public void generateHeader(String title) {
		System.out.println("\n======================================");
		System.out.println("       " + title.toUpperCase());
		System.out.println("======================================");
	}

	@Override
	public void generateBody(List<String> data) {
		if (data == null || data.isEmpty()) {
			System.out.println("Không có dữ liệu để hiển thị.");
			return;
		}
		for (String line : data) {
			System.out.println(line);
		}
	}

	@Override
	public void generateFooter() {
		System.out.println("======================================");
		System.out.println("       Kết thúc báo cáo");
		System.out.println("======================================");
	}
}