package org.wso2.carbon.sample.xacml;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.axis2.AxisFault;
import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.identity.entitlement.filter.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.proxy.PEPProxy;
import org.wso2.carbon.identity.entitlement.proxy.PEPProxyConfig;
import org.wso2.carbon.identity.entitlement.proxy.exception.EntitlementProxyException;
import org.wso2.carbon.identity.entitlement.stub.EntitlementServiceIdentityException;

public class AuthorizationManager {

    private static final Logger log = Logger.getLogger(AuthorizationManager.class.getName());
    private static volatile PEPProxy pepProxy;

    public static void init() {
        buildPepProxy();
    }

    private static void buildPepProxy() {
        if (pepProxy == null) {
            synchronized (PEPProxy.class) {
                if (pepProxy == null) {
                    try {
                        Map<String, Map<String, String>> appToPDPClientConfigMap = new HashMap<String, Map<String, String>>();
                        Map<String, String> clientConfigMap = new HashMap<String, String>();
                        AuthorizationConfigs configs = AuthorizationConfigs.getInstance();
                        String client = configs.getClient();
                        String pdpServiceUrl = configs.getPdpServiceUrl();
                        String pdpServiceUserName = configs.getPdpServerUserName();
                        String pdpServicePassword = configs.getPdpServerPassword();
                        String thriftAuthServiceUrl = configs.getThriftAuthServiceUrl();
                        String thriftHost = configs.getThriftHost();
                        String thriftPort = configs.getThriftPort();
                        String reuseSession = "true";
                        if (client != null && client.equals(EntitlementConstants.SOAP)) {
                            clientConfigMap.put(Constants.PEP.CLIENT, client);
                            clientConfigMap.put(Constants.PEP.SERVER_URL, pdpServiceUrl);
                            clientConfigMap.put(Constants.PEP.USERNAME, pdpServiceUserName);
                            clientConfigMap.put(Constants.PEP.PASSWORD, pdpServicePassword);
                            clientConfigMap.put(Constants.PEP.REUSE_SESSION, reuseSession);
                        } else if (client != null && client.equals(EntitlementConstants.BASIC_AUTH)) {
                            clientConfigMap.put(Constants.PEP.CLIENT, client);
                            clientConfigMap.put(Constants.PEP.SERVER_URL, pdpServiceUrl);
                            clientConfigMap.put(Constants.PEP.USERNAME, pdpServiceUserName);
                            clientConfigMap.put(Constants.PEP.PASSWORD, pdpServicePassword);
                        } else if (client != null && client.equals(EntitlementConstants.THRIFT)) {
                            clientConfigMap.put(Constants.PEP.CLIENT, client);
                            clientConfigMap.put(Constants.PEP.SERVER_URL, thriftAuthServiceUrl);
                            clientConfigMap.put(Constants.PEP.USERNAME, pdpServiceUserName);
                            clientConfigMap.put(Constants.PEP.PASSWORD, pdpServicePassword);
                            clientConfigMap.put(Constants.PEP.REUSE_SESSION, reuseSession);
                            clientConfigMap.put(Constants.PEP.THRIFT_HOST, thriftHost);
                            clientConfigMap.put(Constants.PEP.THRIFT_PORT, thriftPort);
                        } else if (client == null) {
                            clientConfigMap.put(Constants.PEP.SERVER_URL, pdpServiceUrl);
                            clientConfigMap.put(Constants.PEP.USERNAME, pdpServiceUserName);
                            clientConfigMap.put(Constants.PEP.PASSWORD, pdpServicePassword);
                        } else {
                            // log.error("EntitlementMediator initialization error: Unsupported client");
                            /*
                             * throw new EntitlementFilterException(
                             * "ETrade App initialization error: Unsupported client");
                             */
                        }
                        appToPDPClientConfigMap.put("ETradeApp", clientConfigMap);
                        PEPProxyConfig config = new PEPProxyConfig(appToPDPClientConfigMap,
                                "ETradeApp", configs.getCacheType(),
                                configs.getInvalidationInterval(), configs.getMaxCacheEntries());
                        pepProxy = new PEPProxy(config);
                    } catch (EntitlementProxyException e) {
                        log.log(Level.SEVERE, e.toString(), e);
                    }
                }
            }
        }
    }

    public static boolean getSimpleDecision(String subject, String action, String resource,
            String environment) {
        String response = null;
        boolean permit = false;
        try {
            response = pepProxy.getDecision(subject, resource, action, environment);
            if (response.contains("Permit")) {
                permit = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return permit;
    }

    public static ArrayList<String[]> getEntitlements(String subject, String subjectId,
            String action, String resource, boolean enableChildSearch, String tenant) {
        ArrayList<String[]> allowedResources = new ArrayList<String[]>();
        AuthorizationConfigs configs = AuthorizationConfigs.getInstance();
        try {
            String adminUsername = configs.getAuthServerUserName();
            String adminPassword = configs.getAuthServerPassword();
            if (tenant != null && !tenant.isEmpty()) {
                adminUsername = adminUsername + "@" + tenant;
                adminPassword = "123456";
            }
            LoginAdminServiceClient loginAdminServiceClient = new LoginAdminServiceClient(
                    configs.getAuthServiceUrl());
            String cookie = loginAdminServiceClient.authenticate(adminUsername, adminPassword);
            EntitlementServiceClient entitlementServiceClient = new EntitlementServiceClient(
                    configs.getPdpServiceUrl(), cookie);
            allowedResources = entitlementServiceClient.getAllowedResourcesForUser(subject,
                    resource, subjectId, action, enableChildSearch);
        } catch (AxisFault e) {
            log.log(Level.SEVERE, e.toString(), e);
        } catch (RemoteException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } catch (EntitlementServiceIdentityException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } catch (LoginAuthenticationExceptionException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
        return allowedResources;
    }
}
