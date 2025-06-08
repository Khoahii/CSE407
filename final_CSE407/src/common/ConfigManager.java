// src/common/ConfigManager.java
package common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Singleton class to load configuration properties (e.g. data.json path)
 */
public class ConfigManager {
	private static ConfigManager instance;
	private final Properties props;

	private ConfigManager() {
		props = new Properties();
		Path configPath = Path.of("config.properties");
		try (InputStream in = Files.newInputStream(configPath)) {
			props.load(in);
		} catch (IOException e) {
			System.err.println("Failed to load config.properties: " + e.getMessage());
		}
	}

	public static synchronized ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}

	/**
	 * Returns the configured path to the data JSON file
	 */
	public Path getDataFilePath() {
		String path = props.getProperty("dataFile");
		return Path.of(path);
	}
}