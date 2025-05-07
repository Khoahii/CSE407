// PayrollSystem.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PayrollSystem {
	private List<NhanVien> dsNhanVien = new ArrayList<>();

	// Nhập thông tin nhân viên từ bàn phím
	public void nhapNV() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Nhập số lượng nhân viên: ");
		int n = Integer.parseInt(sc.nextLine().trim());

		for (int i = 1; i <= n; i++) {
			System.out.println("\nNhân viên thứ " + i + ":");
			System.out.print("  Loại (1 = toàn thời gian, 2 = bán thời gian): ");
			int loai = Integer.parseInt(sc.nextLine().trim());

			System.out.print("  Tên: ");
			String ten = sc.nextLine().trim();

			System.out.print("  Số giờ làm: ");
			int soGio = Integer.parseInt(sc.nextLine().trim());

			if (loai == 1) {
				dsNhanVien.add(new NhanVienToanThoiGian(ten, soGio));
			} else if (loai == 2) {
				dsNhanVien.add(new NhanVienBanThoiGian(ten, soGio));
			} else {
				System.out.println("  Loại không hợp lệ, bỏ qua nhân viên này.");
			}
		}
	}

	// Xuất bảng lương
	public void xuatBangLuong() {
		System.out.println("\n--------- BẢNG LƯƠNG ---------");
		System.out.printf("%-20s %-10s %-15s%n", "Tên", "Giờ làm", "Lương (VND)");
		System.out.println("---------------------------------------------");
		for (NhanVien nv : dsNhanVien) {
			System.out.printf(
							"%-20s %-10d %,15d%n",
							nv.getTen(),
							nv.getSoGioLam(),
							nv.tinhLuong()
			);
		}
	}

	public static void main(String[] args) {
		PayrollSystem ps = new PayrollSystem();
		ps.nhapNV();
		ps.xuatBangLuong();
	}
}
