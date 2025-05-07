// NhanVien.java
public abstract class NhanVien {
	protected String ten;
	protected int soGioLam;

	public NhanVien(String ten, int soGioLam) {
		this.ten = ten;
		this.soGioLam = soGioLam;
	}

	public String getTen() {
		return ten;
	}

	public int getSoGioLam() {
		return soGioLam;
	}

	// Phương thức trừu tượng để lớp con override
	public abstract long tinhLuong();
}
