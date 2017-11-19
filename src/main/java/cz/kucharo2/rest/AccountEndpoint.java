package cz.kucharo2.rest;

import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.service.OrderingService;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@Path("account")
public class AccountEndpoint {

    @Inject
    private OrderingService orderingService;

    @Path("{accountId}/createdBill")
    public Bill getCreatedBillOnUser(@PathParam("accountId") Integer accountId) {
        return orderingService.getCreatedBillOnAccount(accountId);
    }

}
