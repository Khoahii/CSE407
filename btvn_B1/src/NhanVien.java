public abstract class NhanVien {
	private String ten;
	private int soGioLam;

	public NhanVien(String ten, int soGioLam) {
		this.ten = ten;
		this.soGioLam = soGioLam;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public int getSoGioLam() {
		return soGioLam;
	}

	public void setSoGioLam(int soGioLam) {
		this.soGioLam = soGioLam;
	}

	/** Phương thức trừu tượng để tính lương */
	public abstract double TinhLuong();
}
