package org.wso2.carbon.sample.xacml;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.wso2.carbon.identity.entitlement.stub.EntitlementServiceIdentityException;
import org.wso2.carbon.identity.entitlement.stub.EntitlementServiceStub;
import org.wso2.carbon.identity.entitlement.stub.dto.EntitledAttributesDTO;
import org.wso2.carbon.identity.entitlement.stub.dto.EntitledResultSetDTO;

public class EntitlementServiceClient
{
  private final String serviceName = "EntitlementService";
  private EntitlementServiceStub entitlementServiceStub = null;
  private String endPoint;

  public EntitlementServiceClient(String backEndURL, String sessionCookie)
    throws AxisFault
  {
    this.endPoint = (backEndURL + "/services/" + "EntitlementService");
    ConfigurationContext configContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
    this.entitlementServiceStub = new EntitlementServiceStub(configContext, this.endPoint);
    ServiceClient serviceClient = this.entitlementServiceStub._getServiceClient();
    Options options = serviceClient.getOptions();
    options.setManageSession(true);
    options.setProperty("Cookie", sessionCookie);
  }

  public ArrayList<String[]> getAllowedResourcesForUser(String subjectName, String resourceName, String subjectId, String action, boolean enableChildSearch)
    throws RemoteException, EntitlementServiceIdentityException
  {
    ArrayList results = new ArrayList();
    EntitledResultSetDTO resultSetDTO = this.entitlementServiceStub.getEntitledAttributes(subjectName, resourceName, subjectId, action, enableChildSearch);

    if (resultSetDTO != null) {
      EntitledAttributesDTO[] attributesDTOs = resultSetDTO.getEntitledAttributesDTOs();
      if (attributesDTOs != null) {
        for (int i = 0; i < attributesDTOs.length; i++) {
          String[] resourceEntry = new String[2];
          String allowedAttribute = attributesDTOs[i].getResourceName();
          resourceEntry[0] = allowedAttribute;
          String actionPermitted = attributesDTOs[i].getAction();
          resourceEntry[1] = actionPermitted;
          results.add(resourceEntry);
        }
      }
    }

    System.out.println(" Allowed resources = " + results.toString());

    return results;
  }
}
