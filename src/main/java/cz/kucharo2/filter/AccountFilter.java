package cz.kucharo2.filter;

import cz.kucharo2.data.dao.AccountDao;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.data.enums.AccountRole;
import cz.kucharo2.rest.model.SessionContext;
import cz.kucharo2.utils.PasswordHashUtil;
import org.jboss.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

/**
 * Filter for authentication and authorization via Basic auth method.
 * <p>
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AccountFilter implements ContainerRequestFilter {

    @Inject
    private AccountDao accountDao;

    @Inject
    private Logger logger;

    @Inject
    private SessionContext sessionContext;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            abortOnUnauthorized(requestContext, "Authorization header must be provided");
            return;
        }

        String base64hash = authorizationHeader.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64hash));
        String[] split = credentials.split(":");
        String username = split[0];
        String pass = split[1];

        // find user by username
        Account account = accountDao.findByUsername(username);
        if (account == null) {
            abortOnUnauthorized(requestContext, "Invalid username %s", username);
            return;
        }

        // password validation
        if (!PasswordHashUtil.encrypt(pass).equals(account.getPassword())) {
            abortOnUnauthorized(requestContext, "Invalid password for username %s", username);
            return;
        }

        // validate role
        if (resourceInfo.getResourceMethod() != null) {
            AccountRole[] roles = resourceInfo.getResourceMethod().getAnnotation(Secured.class).roles();
            boolean valid = false;
            for (AccountRole r : roles) {
                if (r == account.getAccountRole()) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                abortOnUnauthorized(requestContext, "User role %s can't do this!", account.getAccountRole());
                return;
            }
        }
        this.sessionContext.setLoggedAccount(account);
    }

    private void abortOnUnauthorized(ContainerRequestContext context, String reason, Object... reasonArgs) {
        String text = String.format(reason, reasonArgs);
        context.abortWith(Response.status(
                Response.Status.UNAUTHORIZED)
                .type(MediaType.TEXT_HTML)
                .entity(String.format(
                        "<div style=\"text-align:center\">\n" +
                                "  <h2>%s</h2>\n" +
                                "  <img src=\"https://assets-auto.rbl.ms/b5421a0c1143063f9f19454ed0c6eabbad37125" +
                                "23d5fc470fde844194cca4d21\">\n" +
                                "</div",
                        text))
                .build());
        logger.info(text);
    }
}
