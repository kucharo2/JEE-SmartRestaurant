package cz.kucharo2.rest;

import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.filter.Secured;
import cz.kucharo2.service.MenuService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * @Author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
@Path("/menu")
public class MenuEndpoint {

    @Inject
    private MenuService menuService;

    @GET
    @Path("{item_id}/sideDish")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public List<Item> getSideDishesForItem(@PathParam("item_id") Integer itemId) {
        return menuService.getItemsByCombinationToAndCategory(itemId, CategoryType.PRILOHA);
    }

    @GET
    @Path("dishes")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Map<String, List<Item>> getAllDishes() {
        return menuService.getAllItemsByCategoryCodeKeyedByCategoryName(CategoryType.MAIN_FOOD);
    }

    @GET
    @Path("drinks")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Map<String, List<Item>> getAllDrinks() {
        return menuService.getAllItemsByCategoryCodeKeyedByCategoryName(CategoryType.DRINKS);
    }
}
