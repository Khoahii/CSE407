// NhanVienBanThoiGian.java
public class NhanVienBanThoiGian extends NhanVien {
	private static final int LUONG_GIO = 30_000;

	public NhanVienBanThoiGian(String ten, int soGioLam) {
		super(ten, soGioLam);
	}

	@Override
	public long tinhLuong() {
		return soGioLam * (long) LUONG_GIO;
	}
}
