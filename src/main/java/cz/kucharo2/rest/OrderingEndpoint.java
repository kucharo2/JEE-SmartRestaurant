package cz.kucharo2.rest;

import cz.kucharo2.common.model.AddOrderItemModel;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.filter.Secured;
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
    @Secured
    public Order addItems(AddOrderItemModel model) throws ServiceException {
        Integer orderId = orderingService.orderItem(model);
        return orderingService.getOrderById(orderId);
    }

    @POST
    @Path("{orderId}/confirm")
    @Secured
    public void confirmOrder(@PathParam("orderId") Integer orderId) throws ServiceException {
        orderingService.confirmOrder(orderId);
    }

    @POST
    @Path("{orderId}/cancel")
    @Secured
    public void cancelOrder(@PathParam("orderId") Integer orderId) throws ServiceException {
        orderingService.cancelBIll(orderId);
    }

    @DELETE
    @Path("deleteItem/{orderItemId}")
    @Secured
    public Order deleteItems(@PathParam("orderItemId") Integer orderItemId) throws ServiceException {
        Integer orderId = orderingService.removeItemFomOrder(orderItemId);
        return orderingService.getOrderById(orderId);
    }

}
