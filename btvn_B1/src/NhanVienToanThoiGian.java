public class NhanVienToanThoiGian extends NhanVien {
	private static final double DON_GIA = 50000;

	public NhanVienToanThoiGian(String ten, int soGioLam) {
		super(ten, soGioLam);
	}

	@Override
	public double TinhLuong() {
		return getSoGioLam() * DON_GIA;
	}
}
