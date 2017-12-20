package cz.kucharo2.rest;

import cz.kucharo2.data.entity.Account;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.filter.Secured;
import cz.kucharo2.service.CashDeskService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @Author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
@Path("/cashDesk")
public class CashDeskEndpoint {

    @Inject
    private CashDeskService cashDeskService;

    @GET
    @Path("{table_id}/unpaidOrderItems")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public List<OrderItem> getUnpaidFinishedOrdersOnTable(@PathParam("table_id") Integer tableId) {
        return cashDeskService.getUnpaidFinishedOrderItemsOnTable(tableId, null);
    }

    @GET
    @Path("{table_id}/unpaidUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public List<Account> getUsersHavingUnpaidFinishedOrdersOnTable(@PathParam("table_id") Integer tableId) {
        return cashDeskService.getUsersHavingUnpaidFinishedOrdersOnTable(tableId);
    }

    @GET
    @Path("{table_id}/unpaidOrderItems/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public List<OrderItem> getUnpaidFinishedOrdersOnTable(@PathParam("table_id") Integer tableId, @PathParam("user_id") Integer userId) {
        return cashDeskService.getUnpaidFinishedOrderItemsOnTable(tableId, userId);
    }
}
