// NhanVienToanThoiGian.java
public class NhanVienToanThoiGian extends NhanVien {
	private static final int LUONG_GIO = 50_000;

	public NhanVienToanThoiGian(String ten, int soGioLam) {
		super(ten, soGioLam);
	}

	@Override
	public long tinhLuong() {
		return soGioLam * (long) LUONG_GIO;
	}
}
