package com.triptacular;

import com.google.common.base.Strings;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gets or sets application settings.
 * 
 * @author Mike Atkisson
 */
public class Settings {
    
    public static final String APP_PROPS = "/application.properties";
    public static final String DEPLOY_TYPE_TESTING = "testing";
    public static final String DEPLOY_TYPE_DEVELOPMENT = "development";
    public static final String DEPLOY_TYPE_STAGING = "staging";
    public static final String DEPLOY_TYPE_PRODUCTION = "production";
    
    
    private Boolean isThymeleafCached;
    private String deploymentType;
    private final Properties properties;
    
    /**
     * Creates a new instance.
     * @throws java.io.IOException
     */
    public Settings() throws IOException {
        properties = new Properties();
        Class<? extends Settings> type = getClass();
        InputStream stream = type.getResourceAsStream(APP_PROPS);
        properties.load(stream);
    }
    
    /**
     * Determines if the application is being tested or not.
     * @return True if the deployment type is testing.
     */
    public boolean isTesting() {
        return getDeploymentType().equals(DEPLOY_TYPE_TESTING);
    }
    
    /**
     * Determines if the application is under development or not.
     * @return True if the deployment type is development.
     */
    public boolean isDevelopment() {
        return getDeploymentType().equals(DEPLOY_TYPE_DEVELOPMENT);
    }
    
    /**
     * Determines if the application is being staged or not.
     * @return True if the deployment type is staging.
     */
    public boolean isStaging() {
        return getDeploymentType().equals(DEPLOY_TYPE_STAGING);
    }
    
    /**
     * Determines if the application is in production or not.
     * @return True if the deployment type is production.
     */
    public boolean isProduction() {
        return getDeploymentType().equals(DEPLOY_TYPE_PRODUCTION);
    }
    
    /**
     * Determines the type of database used in this deployment.
     * @return The name of the kind of database being used.
     */
    public String getDbType() {
        String key = "app." + getDeploymentType() + ".db.type";
        return properties.getProperty(key, "fongo");
    }
    
    /**
     * Determines if thyemleaf is cached.
     * @return 
     */
    public boolean isThymeleafCached() {
        if (isThymeleafCached == null) {
            isThymeleafCached = getBooleanValue("spring.thymeleaf.cache");
        }
        return isThymeleafCached;
    }
    
    /**
     * Gets the deployment level for this application.
     * @return A String having valid values of "testing", "development", 
     *         "staging", or "production".
     */
    public String getDeploymentType() {
        if (deploymentType == null) {
            deploymentType = properties.getProperty("app.deployment.type");
        }
        return deploymentType;
    }
    
    /**
     * Gets a property value which can be parsed as true or false.
     * @param key The key of the property value.
     * @return True or false depending on the value of the key.
     */
    private boolean getBooleanValue(String key) {
        boolean value = false;
        String property = properties.getProperty(key);
        if (!Strings.isNullOrEmpty(property)) {
            value = Boolean.parseBoolean(property);
        }
        return value;
    }
}
