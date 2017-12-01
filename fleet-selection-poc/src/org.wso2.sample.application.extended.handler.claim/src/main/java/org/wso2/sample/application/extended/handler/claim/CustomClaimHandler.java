package org.wso2.sample.application.extended.handler.claim;

import java.util.Map;

import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.FrameworkException;
import org.wso2.carbon.identity.application.authentication.framework.handler.claims.impl.DefaultClaimHandler;
import org.wso2.carbon.identity.application.common.model.ClaimMapping;

/**
 * This sample custom claim handler adds the User selected "fleet" to the claim
 * set returned to the Service Provider. This should be configured in
 * IS_HOME/repository/conf/security/application-authentication.xml at
 * ApplicationAuthentication.Extensions.ClaimHandler after putting at
 * components/lib, to be effective.
 */
public class CustomClaimHandler extends DefaultClaimHandler {

	private static final String FLEET_CLAIM_URI="fleet";
	private static volatile CustomClaimHandler instance;

	public static CustomClaimHandler getInstance() {
		if (instance == null) {
			synchronized (CustomClaimHandler.class) {
				if (instance == null) {
					instance = new CustomClaimHandler();
				}
			}
		}
		return instance;
	}

	@Override
	protected Map<String, String> handleLocalClaims(String spStandardDialect, StepConfig stepConfig,
			AuthenticationContext context) throws FrameworkException {
		//First execute the default claim handling logic in the super class
		Map<String, String> handledClaimsMap = super.handleLocalClaims(spStandardDialect, stepConfig, context);
		//Get all the claims set from the ExtendedBasicAuthenticator
		Map<ClaimMapping, String> stepClaimMap = stepConfig.getAuthenticatedUser().getUserAttributes();
		//Specifically find the "fleet" claim
		for (Map.Entry<ClaimMapping, String> claimEntry : stepClaimMap.entrySet()) {
			if (FLEET_CLAIM_URI.equals(claimEntry.getKey().getLocalClaim().getClaimUri())) {
				//Add the "fleet" claim to the set of claims that will be returned to the SP side
				handledClaimsMap.put(FLEET_CLAIM_URI, claimEntry.getValue());
			}
		}
		return handledClaimsMap;
	}
}
