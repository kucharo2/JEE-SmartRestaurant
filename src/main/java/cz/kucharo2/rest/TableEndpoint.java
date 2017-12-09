package cz.kucharo2.rest;

import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.filter.Secured;
import cz.kucharo2.service.OrderingService;
import cz.kucharo2.service.TableService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Path("table")
public class TableEndpoint {

    @Inject
    private TableService tableService;

    @Inject
    private OrderingService orderingService;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public List<RestaurantTable> getAllTables() {
        return tableService.getAllTables();
    }

    @GET
    @Path("{tableId}/createdOrder")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Order getCreatedOrderOnTable(@PathParam("tableId") Integer tableId) {
        return orderingService.getCreatedOrderOnTable(tableId);
    }


}
