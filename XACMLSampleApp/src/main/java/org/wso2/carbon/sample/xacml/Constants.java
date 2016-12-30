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

public class Constants {

    public static final String AUTH_HOST_NAME = "authServerHostName";

    public static final String AUTH_PORT = "authServerPort";

    public static final String AUTH_USER_NAME = "authServerUserName";

    public static final String AUTH_USER_PASSWORD = "authServerPassword";

    public static final String PDP_HOST_NAME = "pdpServerHostName";

    public static final String PDP_PORT = "pdpServerPort";

    public static final String PDP_USER_NAME = "pdpServerUserName";

    public static final String PDP_USER_PASSWORD = "pdpServerPassword";

    public static final String TRUST_STORE_FILE = "trustStoreFile";

    public static final String TRUST_STORE_PASSWORD = "trustStorePassword";

    public static final String CLIENT = "client";

    public static final String SERVER_URL = "serverUrl";

    public static final String USERNAME = "userName";

    public static final String PASSWORD = "password";

    public static final String THRIFT_HOST = "thriftHost";

    public static final String THRIFT_PORT = "thriftPort";

    public static final String REUSE_SESSION = "reuseSession";

    public static final String CACHE_TYPE = "cacheType";

    public static final String INVALIDATION_INTERVAL = "invalidationInterval";

    public static final String MAX_CACHE_ENTRIES = "maxCacheEntries";

    public static final String JSON = "json";

    public static final String SOAP = "soap";

    public static final String THRIFT = "thrift";

    public static final String BASIC_AUTH = "basicAuth";

    public static final String REMOTE_SERVICE_URL = "remoteServiceUrl";

    public static final String AUTH_REDIRECT_URL = "authRedirectUrl";

    public static final String WSO2_IS = "wso2is";

    public static final String WEB_APP = "webapp";

    public static final String REQUEST_PARAM = "request-param";

    public static final String REQUEST_ATTIBUTE = "request-attribute";

    public static final String SESSION = "session";

    public static final String HTTPS_PORT = "httpsPort";

    public static final String AUTHENTICATION = "authentication";

    public static final String AUTHENTICATION_PAGE = "authenticationPage";

    public static final String AUTHENTICATION_PAGE_URL = "authenticationPageUrl";

    public static final String DEFAULT_CLIENT = "basicAuth";

    public static final String DEFAULT_SUBJECT_SCOPE = "basicAuth";

    public static final String DEFAULT_SUBJECT_ATTRIBUTENAME = "userName";

    public static final String DEFAULT_CACHE_TYPE = "simple";

    public static final String DEFAUL_TTHRIFT_HOST = "localhost";

    public static final String DEFAULT_THRIFT_PORT = "10500";

    public static final String DEFAULT_AUTH_SERVER_USERNAME = "admin";

    public static final String DEFAULT_AUTH_SERVER_PASSWORD = "admin";

    public static final String DEFAULT_AUTH_SERVER_HOSTNAME = "localhost";

    public static final String DEFAULT_AUTH_SERVER_PORT = "9443";

    public static final String DEFAULT_PDP_SERVER_USERNAME = "admin";

    public static final String DEFAULT_PDP_SERVER_PASSWORD = "admin";

    public static final String DEFAULT_PDP_SERVER_HOSTNAME = "localhost";

    public static final String DEFAULT_PDP_SERVER_PORT = "9443";
    
    public static final String DEFAULT_TRUST_STORE_PASSWORD = "wso2carbon";
    
    public static class PEP {
        
        public static final String CLIENT = "client";
        
        public static final String SERVER_URL = "serverUrl";

        public static final String USERNAME = "userName";

        public static final String PASSWORD = "password";
        
        public static final String REUSE_SESSION = "reuseSession";
        
        public static final String THRIFT_HOST = "thriftHost";

        public static final String THRIFT_PORT = "thriftPort";
    }
    
    public static class Decision {
        
        public static final String PERMIT = "Permit";

        public static final String DENY = "Deny";

        public static final String NOT_APPLICABLE = "NotApplicable";

        public static final String INDETERMINATE = "Indeterminate";
    }
    
    public static class AuthorizationMessage {
        
        public static final String AUTHORIZED = "You are authorized";
        
        public static final String NOT_AUTHORIZED = "You are not authorized";
    }
}
