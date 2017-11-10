package cz.kucharo2.rest;

import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.service.MenuService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Pavel Matyáš (matyapav@fel.cvut.cz)
 */

@Path("/menu")
public class MenuEndpoint {

    @Inject
    private MenuService menuService;

    @Inject
    private ItemDao itemDao;

    @GET
    @Path("items")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAllMenuItems() {
        return itemDao.getAll();
    }

    @GET
    @Path("item/{item_id}/sideDish")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getSideDishesForItem(@PathParam("item_id") Integer itemId) {
        return menuService.getItemsByCombinationToAndCategory(itemId, CategoryType.PRILOHA);
    }
}
