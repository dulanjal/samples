package org.wso2.carbon.sample.xacml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = -6161453398989741214L;
    private static final Logger log = Logger.getLogger(ControllerServlet.class.getName());

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        try {
            AuthorizationConfigs.getInstance().initConfig(servletConfig);
        } catch (AuthorizationException e) {
            throw new ServletException(e.getMessage(), e);
        }
        Utils.setTrustAnchor(AuthorizationConfigs.getInstance().getTrustStorePath(),
                AuthorizationConfigs.getInstance().getTrustStorePassword());
        AuthorizationManager.init();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String returnLocation = null;
        // request parameter 'uc' is sent from each usecase page
        // it's used to identify the usecase.
        String usecase = request.getParameter("uc");
        if (usecase != null && !usecase.isEmpty()) {
            if (usecase.equals("uc1")) {
                doCourseGrainUsecase(request, response);
                returnLocation = "course-grain.jsp";
            } else if (usecase.equals("uc2")) {
                doFineGrainUsecase(request, response);
                returnLocation = "fine-grain.jsp";
            } else if (usecase.equals("uc3")) {
                doMultipleRolesUsecase(request, response);
                returnLocation = "multi-roles.jsp";
            } else if (usecase.equals("uc4")) {
                doOverlappingEntitlementsUsecase(request, response);
                returnLocation = "entitlement-overlap.jsp";
            } else if (usecase.equals("uc5")) {
                doHierarchicalUserRolesUsecase(request, response);
                returnLocation = "hierarchical-roles.jsp";
            } else if (usecase.equals("uc6")) {
                doHierarchicalResourcesUsecase(request, response);
                returnLocation = "hierarchical-resources.jsp";
            } else if (usecase.equals("uc7")) {
                doExternalDataUsecase(request, response);
                returnLocation = "external-data.jsp";
            } else if (usecase.equals("uc8")) {
                doReverseLookupUsecase(request, response);
                returnLocation = "reverse-lookup.jsp";
            }
        } else {
            // TODO go to error page
            returnLocation = "error.jsp";
        }
        try {
            request.getRequestDispatcher(returnLocation).forward(request, response);
        } catch (ServletException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void doCourseGrainUsecase(HttpServletRequest request, HttpServletResponse response) {
        String subject = request.getParameter("user");
        String action = "apply";
        String resource = "loan";
        boolean permit = AuthorizationManager.getSimpleDecision(subject, action, resource, null);
        request.setAttribute("uc1-authorized", permit);
    }

    private void doFineGrainUsecase(HttpServletRequest request, HttpServletResponse response) {
        String subject = request.getParameter("user");
        String action = "pay";
        String resource = "payment";
        boolean permit = AuthorizationManager.getSimpleDecision(subject, action, resource, null);
        request.setAttribute("uc2-authorized", permit);
    }

    private void doMultipleRolesUsecase(HttpServletRequest request, HttpServletResponse response) {
        String subject = request.getParameter("user");
        String action = "reversal";
        String resource = "credit-card";
        boolean permit = AuthorizationManager.getSimpleDecision(subject, action, resource, null);
        request.setAttribute("uc3-authorized", permit);
    }

    private void doOverlappingEntitlementsUsecase(HttpServletRequest request,
            HttpServletResponse response) {
        String subject = request.getParameter("user");
        String action = "transfer";
        String resource = "money-transfer";
        boolean permit = AuthorizationManager.getSimpleDecision(subject, action, resource, null);
        request.setAttribute("uc4-authorized", permit);
    }

    private void doHierarchicalUserRolesUsecase(HttpServletRequest request,
            HttpServletResponse response) {
        String subject = request.getParameter("user");
        String action = "create";
        String resource = "account";
        boolean permit = AuthorizationManager.getSimpleDecision(subject, action, resource, null);
        request.setAttribute("uc5-authorized", permit);
    }

    private void doHierarchicalResourcesUsecase(HttpServletRequest request,
            HttpServletResponse response) {
        String subject = request.getParameter("user");
        String action = "close";
        String resource = "account";
        boolean permit = AuthorizationManager.getSimpleDecision(subject, action, resource, null);
        request.setAttribute("uc6-authorized", permit);
    }

    private void doExternalDataUsecase(HttpServletRequest request, HttpServletResponse response) {
        String subject = request.getParameter("user");
        String action = "request";
        String resource = "cheque-book";
        boolean permit = AuthorizationManager.getSimpleDecision(subject, action, resource, null);
        request.setAttribute("uc7-authorized", permit);
    }

    private void doReverseLookupUsecase(HttpServletRequest request, HttpServletResponse response) {
        String tenant = request.getParameter("tenant");
        String subjectType = request.getParameter("subjectType");
        String subject = request.getParameter("subject");
        String subjectId = request.getParameter("subjectId");
        String action = request.getParameter("action");
        String resource = request.getParameter("resource");
        boolean enableChildSearch = (request.getParameter("recursive") != null ? true : false);
        request.setAttribute("allowedResources", AuthorizationManager.getEntitlements(subject,
                subjectId, action, resource, enableChildSearch, tenant));
    }
}
