package cz.kucharo2.rest;

import cz.kucharo2.rest.model.AddOrderItemModel;
import cz.kucharo2.service.OrderingService;
import cz.kucharo2.service.exception.ServiceException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Path("order")
public class OrderingEndpoint {

    @Inject
    private OrderingService orderingService;

    @POST
    @Path("addItems")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Integer addItems(AddOrderItemModel model) throws ServiceException {
        return orderingService.orderItem(model.getBillId(), model.getTableId(), model.getItemsToAdd()).getId();
    }

    @POST
    @Path("{orderId}/confirm")
    public void confirmOrder(@PathParam("orderId") Integer orderId) throws ServiceException {
        orderingService.confirmBill(orderId);
    }
    @DELETE
    @Path("deleteItems/{orderId}")
    public void deleteItems(@PathParam("orderId") Integer orderId) throws ServiceException {
        orderingService.removeItemFomOrder(orderId);
    }

}
