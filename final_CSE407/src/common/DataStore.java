package common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton to hold in-memory data and read/write JSON file
 */
public class DataStore {
	private static DataStore instance;
	private final Path dataFile;
	private final ObjectMapper mapper = new ObjectMapper();
	private RootData root;

	private DataStore() {
		dataFile = ConfigManager.getInstance().getDataFilePath();
		load();
	}

	public static synchronized DataStore getInstance() {
		if (instance == null) {
			instance = new DataStore();
		}
		return instance;
	}

	private void load() {
		try {
			if (!Files.exists(dataFile)) {
				root = new RootData();
				save();
			} else {
				root = mapper.readValue(dataFile.toFile(), RootData.class);
			}
		} catch (IOException e) {
			System.err.println("Failed to load data.json: " + e.getMessage());
			root = new RootData();
		}
	}

	public synchronized void save() {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(dataFile.toFile(), root);
		} catch (IOException e) {
			System.err.println("Failed to save data.json: " + e.getMessage());
		}
	}

	// Accessors for data
//	public List<auth.model.User> getUsers() {
//		return root.users;
//	}
//
//	public List<account.model.Account> getAccounts() {
//		return root.accounts;
//	}
//
//	public List<category.model.Category> getCategories() {
//		return root.categories;
//	}
//
//	public List<product.model.Product> getProducts() {
//		return root.products;
//	}

	// RootData class holding the four lists
	public static class RootData {
//		public List<auth.model.User> users = new ArrayList<>();
//		public List<account.model.Account> accounts = new ArrayList<>();
//		public List<category.model.Category> categories = new ArrayList<>();
//		public List<product.model.Product> products = new ArrayList<>();
	}
}
