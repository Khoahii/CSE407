import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyNhanSu {
	private List<NhanVien> danhSach = new ArrayList<>();

	/** Nhập thông tin một nhân viên từ bàn phím */
	public NhanVien nhapNhanVien() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Chọn loại (1=Toàn thời gian, 2=Bán thời gian): ");
		int loai = sc.nextInt();
		sc.nextLine(); // consume newline

		System.out.print("Tên nhân viên: ");
		String ten = sc.nextLine();

		System.out.print("Số giờ làm việc: ");
		int soGio = sc.nextInt();

		if (loai == 1) {
			return new NhanVienToanThoiGian(ten, soGio);
		} else {
			return new NhanVienBanThoiGian(ten, soGio);
		}
	}

	/** Hiển thị bảng lương của danh sách nhân viên */
	public void hienThiBangLuong(List<NhanVien> danhSach) {
		System.out.printf("%-20s %-10s %-15s %-15s%n", "Tên", "Giờ làm", "Loại", "Lương (VND)");
		System.out.println("----------------------------------------------------------------");
		for (NhanVien nv : danhSach) {
			String loai = (nv instanceof NhanVienToanThoiGian)
							? "Toàn thời gian" : "Bán thời gian";
			System.out.printf("%-20s %-10d %-15s %,15.0f%n",
							nv.getTen(), nv.getSoGioLam(), loai, nv.TinhLuong());
		}
	}

	public static void main(String[] args) {
		QuanLyNhanSu qlns = new QuanLyNhanSu();
		Scanner sc = new Scanner(System.in);

		System.out.print("Nhập số nhân viên: ");
		int n = sc.nextInt();

		for (int i = 0; i < n; i++) {
			System.out.println("\n--- Nhập nhân viên thứ " + (i + 1) + " ---");
			NhanVien nv = qlns.nhapNhanVien();
			qlns.danhSach.add(nv);
		}

		System.out.println("\n===== BẢNG LƯƠNG =====");
		qlns.hienThiBangLuong(qlns.danhSach);
	}
}
