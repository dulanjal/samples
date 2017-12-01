package org.wso2.sample.authenticator.extended.basicauth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;
import org.wso2.carbon.identity.application.authenticator.basicauth.BasicAuthenticator;
import org.wso2.carbon.identity.application.common.model.ClaimMapping;

/**
 * <p>
 * This sample custom authenticator extends the behaviour of BasicAuthenticator
 * to read the User selected "fleet" and then persists it in a database table
 * against a special scope that has been sent by the Service Provider - a.k.a
 * portal.
 * </p>
 * 
 * <p>
 * The Service Provider will send this scope by generating a unique random 
 * value and appending the portal identifier to it. The portal identifier
 * will be the Service Provider name registered in Identity Server. E.g.
 * company1-a67df790degi609fkhi7
 * </p>
 * 
 * <p>
 * This authenticator also adds the selected "fleet" to a claim map, so it can
 * be included in the ID Token getting returned to the Service Provider
 * </p>
 */
public class ExtendedBasicAuthenticator extends BasicAuthenticator {
	private static final long serialVersionUID = -3662907695092520116L;

	@Override
	public boolean canHandle(HttpServletRequest request) {
		String userName = request.getParameter(ExtendedBasicAuthenticatorConstants.USER_NAME);
		String password = request.getParameter(ExtendedBasicAuthenticatorConstants.PASSWORD);
		String fleet = request.getParameter(ExtendedBasicAuthenticatorConstants.FLEET);
		if (userName != null && password != null && fleet != null) {
			return true;
		}
		return false;
	}

	@Override
	protected void processAuthenticationResponse(HttpServletRequest request, HttpServletResponse response,
			AuthenticationContext context) throws AuthenticationFailedException {
		// Let the BasicAuthenticator handle the authentication and default
		// processing logic
		super.processAuthenticationResponse(request, response, context);
		Map<ClaimMapping, String> claims = new HashMap<ClaimMapping, String>();
		// Read the "fleet" value from the submission from the login page
		String userSelectedFleet = request.getParameter(ExtendedBasicAuthenticatorConstants.FLEET);
		// Read the list of scope values from the initial request to the
		// Identity Server
		String scopesParam = context.getAuthenticationRequest()
				.getRequestQueryParam(ExtendedBasicAuthenticatorConstants.SCOPE)[0];
		String[] scopesParamValues = scopesParam.split(" ");
		// Try to identify the special scope from the scopes list.
		for (String scope : scopesParamValues) {
			if (scope.startsWith(context.getServiceProviderName() + "-")) {
				// Persists the user selected fleet against the special scope
				storeFleetSelection(scope, userSelectedFleet);
				break;
			}
		}
		// Add the selected fleet as a claim of the user, so it will be read
		// by the CustomClaimHandler and added to the claim set returned in
		// the ID Token.
		claims.put(ClaimMapping.build(ExtendedBasicAuthenticatorConstants.FLEET,
				ExtendedBasicAuthenticatorConstants.FLEET, null, false), userSelectedFleet);
		context.getSubject().setUserAttributes(claims);
	}

	@Override
	public String getFriendlyName() {
		return ExtendedBasicAuthenticatorConstants.AUTHENTICATOR_FRIENDLY_NAME;
	}

	@Override
	public String getName() {
		return ExtendedBasicAuthenticatorConstants.AUTHENTICATOR_NAME;
	}

	private void storeFleetSelection(String scope, String fleet) {
		Context ctx = null;
		DataSource dataSource = null;
		try {
			ctx = new InitialContext();
			dataSource = ((DataSource) ctx.lookup("jdbc/CustomDB"));
		} catch (NamingException e) {

		}
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			String sql = " INSERT INTO FLEET_SELECTION (SCOPE, FLEET) VALUES (?, ?) ";
			preparedStmt = connection.prepareStatement(sql);
			preparedStmt.setString(1, scope);
			preparedStmt.setString(2, fleet);
			preparedStmt.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				preparedStmt.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connection.close();
			} catch (Exception e) {
				/* ignored */ }
		}
	}
}
