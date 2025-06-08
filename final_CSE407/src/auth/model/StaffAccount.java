package auth.model;

public class StaffAccount extends Account {

	public StaffAccount(String id, String username, String passwordHash) {
		super(id, username, passwordHash, "STAFF");
	}

	@Override
	public void showInfo() {
		System.out.println(">> Staff: " + username + " (ID: " + id + ")");
	}
}
