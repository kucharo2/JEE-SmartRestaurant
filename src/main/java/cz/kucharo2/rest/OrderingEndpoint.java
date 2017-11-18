package cz.kucharo2.rest;

import cz.kucharo2.rest.model.AddOrderItemModel;
import cz.kucharo2.service.OrderingService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Integer addItems(AddOrderItemModel model) {
        return orderingService.orderItem(model.getBillId(), model.getTableId(), model.getItemsToAdd()).getId();
    }


}
