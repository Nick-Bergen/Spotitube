package nl.han.oose.nickbergen.datasource.util;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private Logger logger = Logger.getLogger(getClass().getName());
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            Class.forName(properties.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Can't access property file database.properties: ", e);
        }
    }

    public String getConnectionString()
    {
        return  properties.getProperty("connectionPrefix") + properties.getProperty("server") + "/"
                + properties.getProperty("database") + "?"
                + "user=" + properties.getProperty("user") + "&"
                + "password=" + properties.getProperty("password");
    }
}
