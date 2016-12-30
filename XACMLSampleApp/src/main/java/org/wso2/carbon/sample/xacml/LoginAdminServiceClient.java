package org.wso2.carbon.sample.xacml;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.wso2.carbon.authenticator.stub.AuthenticationAdminStub;
import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.authenticator.stub.LogoutAuthenticationExceptionException;

public class LoginAdminServiceClient {
    
    private AuthenticationAdminStub authenticationAdminStub;
    private String endPoint;
    private String sessionCookie;

    public LoginAdminServiceClient(String backEndUrl) throws AxisFault {
        this.endPoint = (backEndUrl + "/services/" + "AuthenticationAdmin");
        this.authenticationAdminStub = new AuthenticationAdminStub(this.endPoint);
    }

    public String authenticate(String userName, String password) throws RemoteException,
            LoginAuthenticationExceptionException {
        ServiceClient client = authenticationAdminStub._getServiceClient();
        Options option = client.getOptions();
        option.setProperty(HTTPConstants.COOKIE_STRING, this.sessionCookie);
        try {
            if (this.authenticationAdminStub.login(userName, password, "localhost")) {
                ServiceContext serviceContext = this.authenticationAdminStub._getServiceClient()
                        .getLastOperationContext().getServiceContext();
                this.sessionCookie = (String) serviceContext.getProperty("Cookie");
            }
        } finally {
            client.cleanupTransport();
        }
        return sessionCookie;
    }

    public void logOut() throws RemoteException, LogoutAuthenticationExceptionException {
        this.authenticationAdminStub.logout();
    }
}
