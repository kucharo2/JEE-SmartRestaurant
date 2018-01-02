package cz.kucharo2.rest;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.data.enums.AccountRole;
import cz.kucharo2.filter.Secured;
import cz.kucharo2.rest.model.FieldError;
import cz.kucharo2.rest.model.FormResponse;
import cz.kucharo2.rest.validator.RegistrationValidator;
import cz.kucharo2.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@Path("account")
public class AccountEndpoint {

    @Inject
    private AccountService accountService;

    @Inject
    private RegistrationValidator registrationValidator;

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public FormResponse registerNewAccount(RegisterNewAccountModel model) {
        List<FieldError> errors = registrationValidator.validate(model);

        if (!errors.isEmpty()) {
            return new FormResponse(null, errors);
        }

        accountService.createNewAccount(model);
        return new FormResponse(true, null);
    }

    @POST
    @Path("login")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Account checkCredentials(String base64credentials) {
        return accountService.checkCorrectCredentials(base64credentials, false);
    }

    @POST
    @Path("login/waiter")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Account checkWaiterCredentials(String base64credentials) {
        return accountService.checkCorrectCredentials(base64credentials, true);
    }
}
