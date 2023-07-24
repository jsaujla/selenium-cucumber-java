package commons.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Helper class for reading properties files.
 *
 * @author Jaspal Aujla
 */
public class PropertiesManager {

    //********** LOGGER OBJECT DECLARATION/INITIALIZATION **********
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesManager.class);

    //********** OBJECT DECLARATION **********
    private Properties properties;

    /**
     * Constructs a new PropertiesManager instance with the specified file path.
     *
     * @param filePath the path of the properties file to load
     */
    public PropertiesManager(String filePath) {
        LOGGER.info("Constructing PropertiesManager with file path: '{}'", filePath);
        loadPropertiesFile(filePath);
    }

    /**
     * Returns the value of the specified property as a string.
     *
     * @param key the name of the property to retrieve
     * @return the string value of the property
     */
    public String getProperty(String key) {
        LOGGER.info("Returning Property of '" + key + "' as string");
        return properties.getProperty(key);
    }

    /**
     * Returns the value of the specified property as a boolean.
     *
     * @param key the name of the property to retrieve
     * @return the boolean value of the property
     */
    public boolean getPropertyAsBoolean(String key) {
        LOGGER.info("Returning Property of '" + key + "' as boolean");
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    /**
     * Returns the value of the specified property as an integer.
     *
     * @param key the name of the property to retrieve
     * @return the integer value of the property
     * @throws RuntimeException if the property value cannot be converted to an integer
     */
    public int getPropertyAsInt(String key) {
        LOGGER.info("Returning Property of '" + key + "' as int");
        String value = properties.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            String errorMessage = "Failed to convert property '" + key + "' to int: " + value;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage + e);
        }
    }

    /**
     * Returns the value of the specified property as a long.
     *
     * @param key the name of the property to retrieve
     * @return the long value of the property
     * @throws RuntimeException if the property value cannot be converted to a long
     */
    public long getPropertyAsLong(String key) {
        LOGGER.info("Returning Property of '" + key + "' as int");
        String value = properties.getProperty(key);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            String errorMessage = "Failed to convert property '" + key + "' to long: " + value;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage + e);
        }
    }

    /**
     * Loads the properties file with the specified file path.
     *
     * @param filePath the path of the properties file to load
     */
    private void loadPropertiesFile(String filePath) {
        LOGGER.info("Loading properties file");
        FileInputStream fileInputStream = null;
        try {
            properties = new Properties();
            fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            LOGGER.info("Properties file '{}' loaded successfully", filePath);
        }
        catch (IOException e) {
            LOGGER.error("Failed to load properties file '{}'", filePath, e);
        }
        finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (IOException e) {
                    LOGGER.error("Failed to close fileInputStream", e);
                }
            }
        }
    }

}
