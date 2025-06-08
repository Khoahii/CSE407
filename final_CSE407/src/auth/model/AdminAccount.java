package auth.model;

public class AdminAccount extends Account {

	public AdminAccount(String id, String username, String passwordHash) {
		super(id, username, passwordHash, "ADMIN");
	}

	@Override
	public void showInfo() {
		System.out.println(">> Admin: " + username + " (ID: " + id + ")");
	}
}
