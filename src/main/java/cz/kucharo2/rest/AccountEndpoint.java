package cz.kucharo2.rest;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.filter.Secured;
import cz.kucharo2.service.AccountService;
import cz.kucharo2.service.OrderingService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@Path("account")
public class AccountEndpoint {
    @Inject
    AccountService accountService;

    @Inject
    private OrderingService orderingService;

    @Path("{accountId}/createdBill")
    public Bill getCreatedBillOnUser(@PathParam("accountId") Integer accountId) {
        return orderingService.getCreatedBillOnAccount(accountId);
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Integer registerNewAccount(RegisterNewAccountModel model) {
        return null;
    }

}
