/*
 *  Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.sample.xacml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;

public class AuthorizationConfigs {

    private static Logger LOGGER = Logger.getLogger("InfoLogging");

    private static volatile AuthorizationConfigs instance;

    private String authServiceUrl;
    private String authServerHostName;
    private String authServerPort;
    private String authServerUserName;
    private String authServerPassword;
    private String pdpServiceUrl;
    private String pdpServerHostName;
    private String pdpServerPort;
    private String pdpServerUserName;
    private String pdpServerPassword;
    private String client;
    private String thriftAuthServiceUrl;
    private String thriftHost;
    private String thriftPort;
    private String cacheType;
    private int invalidationInterval;
    private int maxCacheEntries;
    private String trustStorePath;
    private String trustStorePassword;

    /* private String authRedirectURL; */

    public static AuthorizationConfigs getInstance() {
        if (instance == null) {
            synchronized (AuthorizationConfigs.class) {
                if (instance == null) {
                    instance = new AuthorizationConfigs();
                }
            }
        }
        return instance;
    }

    public void initConfig(ServletConfig servletConfig) throws AuthorizationException {

        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            properties.load(classLoader.getResourceAsStream("xacml.properties"));
            initConfig(properties);
        } catch (IOException e) {
            LOGGER.severe("Error Loading Properties : " + e.getMessage());
        }

    }

    public void initConfig(String propertiesFilePath) throws AuthorizationException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFilePath));
            initConfig(properties);
        } catch (FileNotFoundException e) {
            throw new AuthorizationException("Properties file not found at " + propertiesFilePath);
        } catch (IOException e) {
            throw new AuthorizationException("Error reading properties file at "
                    + propertiesFilePath);
        }
    }

    public void initConfig(Properties properties) throws AuthorizationException {

        String authServiceUrl = properties.getProperty("authServiceUrl");
        if (authServiceUrl == null || authServiceUrl.trim().length() == 0) {
            authServiceUrl = "https://localhost:9443/services/";
        }
        this.authServiceUrl = authServiceUrl;

        String authServerHostName = properties.getProperty(Constants.AUTH_HOST_NAME);
        if (authServerHostName == null || authServerHostName.trim().length() == 0) {
            authServerHostName = Constants.DEFAULT_AUTH_SERVER_HOSTNAME;
        }
        this.authServerHostName = authServerHostName;

        String authServerPort = properties.getProperty(Constants.AUTH_PORT);
        if (authServerPort == null || authServerPort.trim().length() == 0) {
            authServerPort = Constants.DEFAULT_AUTH_SERVER_PORT;
        }
        this.authServerPort = authServerPort;

        String authServerUserName = properties.getProperty(Constants.AUTH_USER_NAME);
        if (authServerUserName == null || authServerUserName.trim().length() == 0) {
            authServerUserName = Constants.DEFAULT_AUTH_SERVER_USERNAME;
        }
        this.authServerUserName = authServerUserName;

        String authServerPassword = properties.getProperty(Constants.AUTH_USER_PASSWORD);
        if (authServerPassword == null || authServerPassword.trim().length() == 0) {
            authServerPassword = Constants.DEFAULT_AUTH_SERVER_PASSWORD;
        }
        this.authServerPassword = authServerPassword;

        String pdpServiceUrl = properties.getProperty("pdpServiceUrl");
        if (pdpServiceUrl == null || pdpServiceUrl.trim().length() == 0) {
            pdpServiceUrl = "https://localhost:9443/services/";
        }
        this.pdpServiceUrl = pdpServiceUrl;

        String pdpServerHostName = properties.getProperty(Constants.PDP_HOST_NAME);
        if (pdpServerHostName == null || pdpServerHostName.trim().length() == 0) {
            pdpServerHostName = Constants.DEFAULT_PDP_SERVER_HOSTNAME;
        }
        this.pdpServerHostName = pdpServerHostName;

        String pdpServerPort = properties.getProperty(Constants.PDP_PORT);
        if (pdpServerPort == null || pdpServerPort.trim().length() == 0) {
            pdpServerPort = Constants.DEFAULT_PDP_SERVER_PORT;
        }
        this.pdpServerPort = pdpServerPort;

        String pdpServerUserName = properties.getProperty(Constants.PDP_USER_NAME);
        if (pdpServerUserName == null || pdpServerUserName.trim().length() == 0) {
            pdpServerUserName = Constants.DEFAULT_PDP_SERVER_USERNAME;
        }
        this.pdpServerUserName = pdpServerUserName;

        String pdpServerPassword = properties.getProperty(Constants.PDP_USER_PASSWORD);
        if (pdpServerPassword == null || pdpServerPassword.trim().length() == 0) {
            pdpServerPassword = Constants.DEFAULT_PDP_SERVER_PASSWORD;
        } else {
            this.pdpServerPassword = pdpServerPassword;
        }

        String client = properties.getProperty(Constants.CLIENT);
        if (client == null) {
            client = Constants.DEFAULT_CLIENT;
        }
        this.client = client;

        this.cacheType = properties.getProperty(Constants.CACHE_TYPE);

        String maxCacheEntries = properties.getProperty(Constants.MAX_CACHE_ENTRIES);
        if (maxCacheEntries != null) {
            this.maxCacheEntries = Integer.parseInt(maxCacheEntries);
        } else {
            this.maxCacheEntries = 0;
        }

        String invalidationInterval = properties.getProperty(Constants.INVALIDATION_INTERVAL);
        if (invalidationInterval != null) {
            this.invalidationInterval = Integer.parseInt(invalidationInterval);
        } else {
            this.invalidationInterval = 0;
        }

        String thriftAuthServiceUrl = properties.getProperty("thriftAuthServiceUrl");
        if (thriftAuthServiceUrl == null || thriftAuthServiceUrl.trim().length() == 0) {
            thriftAuthServiceUrl = "https://localhost:9443/";
        }
        this.thriftAuthServiceUrl = thriftAuthServiceUrl;

        String thriftHost = properties.getProperty(Constants.THRIFT_HOST);
        if (thriftHost == null) {
            thriftHost = Constants.DEFAUL_TTHRIFT_HOST;
        }
        this.thriftHost = thriftHost;

        String thriftPort = properties.getProperty(Constants.THRIFT_PORT);
        if (thriftPort == null) {
            thriftPort = Constants.DEFAULT_THRIFT_PORT;
        }
        this.thriftPort = thriftPort;

        String trustStorePath = properties.getProperty(Constants.TRUST_STORE_FILE);
        if (trustStorePath == null || trustStorePath.trim().length() == 0) {
            try {
                String path = Thread.currentThread().getContextClassLoader()
                        .getResource("wso2carbon.jks").getFile();
                trustStorePath = path;
            } catch (Exception exx) {
                LOGGER.warning("There are no any key store file set in to the system property env."
                        + exx.getMessage());
            }
        }
        this.trustStorePath = trustStorePath;

        String trustStorePassword = properties.getProperty(Constants.TRUST_STORE_PASSWORD);
        if (trustStorePassword == null || trustStorePassword.trim().length() == 0) {
            trustStorePassword = Constants.DEFAULT_TRUST_STORE_PASSWORD;
        }
        this.trustStorePassword = trustStorePassword;
    }

    public String getAuthServerHostName() {
        return authServerHostName;
    }

    public String getAuthServerPort() {
        return authServerPort;
    }

    public String getAuthServerPassword() {
        return authServerPassword;
    }

    public String getPdpServerHostName() {
        return pdpServerHostName;
    }

    public String getPdpServerPort() {
        return pdpServerPort;
    }

    public String getPdpServerUserName() {
        return pdpServerUserName;
    }

    public String getPdpServerPassword() {
        return pdpServerPassword;
    }

    public String getClient() {
        return client;
    }

    public String getThriftHost() {
        return thriftHost;
    }

    public String getThriftPort() {
        return thriftPort;
    }

    public String getCacheType() {
        return cacheType;
    }

    public int getInvalidationInterval() {
        return invalidationInterval;
    }

    public int getMaxCacheEntries() {
        return maxCacheEntries;
    }

    public void setAuthServerHostName(String authServerHostName) {
        this.authServerHostName = authServerHostName;
    }

    public void setAuthServerPort(String authServerPort) {
        this.authServerPort = authServerPort;
    }

    public void setAuthServerPassword(String authServerPassword) {
        this.authServerPassword = authServerPassword;
    }

    public void setPdpServerHostName(String pdpServerHostName) {
        this.pdpServerHostName = pdpServerHostName;
    }

    public void setPdpServerPort(String pdpServerPort) {
        this.pdpServerPort = pdpServerPort;
    }

    public void setPdpServerUserName(String pdpServerUserName) {
        this.pdpServerUserName = pdpServerUserName;
    }

    public void setPdpServerPassword(String pdpServerPassword) {
        this.pdpServerPassword = pdpServerPassword;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setThriftHost(String thriftHost) {
        this.thriftHost = thriftHost;
    }

    public void setThriftPort(String thriftPort) {
        this.thriftPort = thriftPort;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public void setInvalidationInterval(int invalidationInterval) {
        this.invalidationInterval = invalidationInterval;
    }

    public void setMaxCacheEntries(int maxCacheEntries) {
        this.maxCacheEntries = maxCacheEntries;
    }

    public String getAuthServerUserName() {
        return authServerUserName;
    }

    public void setAuthServerUserName(String authServerUserName) {
        this.authServerUserName = authServerUserName;
    }

    public String getTrustStorePath() {
        return trustStorePath;
    }

    public void setTrustStorePath(String trustStorePath) {
        this.trustStorePath = trustStorePath;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public String getAuthServiceUrl() {
        return authServiceUrl;
    }

    public void setAuthServiceUrl(String authServiceUrl) {
        this.authServiceUrl = authServiceUrl;
    }

    public String getPdpServiceUrl() {
        return pdpServiceUrl;
    }

    public void setPdpServiceUrl(String pdpServiceUrl) {
        this.pdpServiceUrl = pdpServiceUrl;
    }

    public String getThriftAuthServiceUrl() {
        return thriftAuthServiceUrl;
    }

    public void setThriftAuthServiceUrl(String thriftAuthServiceUrl) {
        this.thriftAuthServiceUrl = thriftAuthServiceUrl;
    }
}
