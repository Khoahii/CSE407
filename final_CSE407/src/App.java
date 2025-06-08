// src/App.java
import auth.model.Account;
import auth.model.AdminAccount;
import auth.model.StaffAccount;
import common.ConfigManager;
import common.DataStore;
//import auth.model.User;
//import category.model.Category;
//import product.model.Product;
//import account.model.Account;

import java.nio.file.Path;
import java.util.List;

public class App {
	public static void main(String[] args) {
		Account admin = new AdminAccount("1", "adminUser", "hashed_password");
		admin.showInfo();

		Account staff = new StaffAccount("2", "staffUser", "hashed_password");
		staff.showInfo();
	}
}
