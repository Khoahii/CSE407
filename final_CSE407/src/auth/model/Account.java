package auth.model;

public abstract class Account {
	protected String id;
	protected String username;
	protected String passwordHash;
	protected String role;

	public Account(String id, String username, String passwordHash, String role) {
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getRole() {
		return role;
	}

	public abstract void showInfo();
}
