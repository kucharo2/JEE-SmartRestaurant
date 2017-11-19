package cz.kucharo2.rest;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.filter.Secured;
import cz.kucharo2.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@Path("account")
public class AccountEndpoint {

    @Inject
    AccountService accountService;

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Integer registerNewAccount(RegisterNewAccountModel model) {
        return null;
    }

}
