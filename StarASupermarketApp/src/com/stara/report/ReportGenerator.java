package com.stara.report;

import java.util.List;

/**
 * ReportGenerator là giao diện cho Implementation (cách thức tạo báo cáo).
 * Nó định nghĩa các bước để tạo header, body và footer của báo cáo.
 */
public interface ReportGenerator {
	void generateHeader(String title);
	void generateBody(List<String> data);
	void generateFooter();
}