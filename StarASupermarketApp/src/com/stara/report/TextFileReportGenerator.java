package com.stara.report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * TextFileReportGenerator là một Concrete Implementation của ReportGenerator.
 * Nó tạo báo cáo và ghi vào một file văn bản.
 */
public class TextFileReportGenerator implements ReportGenerator {
	private String fileName;

	public TextFileReportGenerator(String fileName) {
		this.fileName = fileName;
	}

	private void writeToFile(String content) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // true để append
			writer.write(content);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Lỗi khi ghi vào file báo cáo " + fileName + ": " + e.getMessage());
		}
	}

	@Override
	public void generateHeader(String title) {
		// Xóa nội dung file cũ trước khi ghi header mới
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) { // false để overwrite
			writer.write(""); // Xóa nội dung
		} catch (IOException e) {
			System.err.println("Lỗi khi xóa nội dung file cũ: " + e.getMessage());
		}

		writeToFile("======================================");
		writeToFile("       " + title.toUpperCase());
		writeToFile("======================================");
		System.out.println("Báo cáo đã được ghi vào file: " + fileName);
	}

	@Override
	public void generateBody(List<String> data) {
		if (data == null || data.isEmpty()) {
			writeToFile("Không có dữ liệu để hiển thị.");
			return;
		}
		for (String line : data) {
			writeToFile(line);
		}
	}

	@Override
	public void generateFooter() {
		writeToFile("======================================");
		writeToFile("       Kết thúc báo cáo");
		writeToFile("======================================");
	}
}