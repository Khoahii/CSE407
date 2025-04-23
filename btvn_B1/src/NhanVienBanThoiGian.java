public class NhanVienBanThoiGian extends NhanVien {
	private static final double DON_GIA = 30000;

	public NhanVienBanThoiGian(String ten, int soGioLam) {
		super(ten, soGioLam);
	}

	@Override
	public double TinhLuong() {
		return getSoGioLam() * DON_GIA;
	}
}
