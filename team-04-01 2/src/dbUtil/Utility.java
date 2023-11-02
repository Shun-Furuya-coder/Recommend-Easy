package dbUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utility {
	
	private static Properties properties = null;
	
	static {
		FileInputStream fileInputStream = null;
		try {
			properties = new Properties();
			fileInputStream = new FileInputStream("data.properties");
		
			properties.load(fileInputStream);
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.getStackTrace();
				}
			}
		}
	}
	
	public static String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
				

}
