package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
		
	private static ApplicationProperties applicationProperties;

	public Properties getApplicationProperties() throws IOException {
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties/application.properties");
		Properties proterties = new Properties();
		proterties.load(inputStream);
		return proterties;
	}
	
	private ApplicationProperties() {
		
	}
	
	public static ApplicationProperties getInstance() {
		if(applicationProperties == null) {
			applicationProperties = new ApplicationProperties();
		}
		return applicationProperties;
	}
	
	
}
