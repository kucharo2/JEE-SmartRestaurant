package cz.kucharo2.rest;

import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Pavel Matyáš (matyapav@fel.cvut.cz)
 */

//TODO dummy endpoint only for angular ajax testing - remove in future
@Path("/menu")
public class MenuEndpoint {

    @GET
    @Path("items")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAllMenuItems() {
        List<Item> itemsList = new ArrayList<>();

        Category category = new Category();
        category.setName("Hotovky");
        category.setCode(CategoryType.HLAVNI_JIDLO);

        Item item1 = new Item();
        item1.setId(1);
        item1.setCategory(category);
        item1.setName("Smažený sýr");
        item1.setCode("120");
        item1.setDescription("S eidamem to je jako se ženskými... třicítka je lepší jak pětačtyřicítka");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setId(2);
        item2.setCategory(category);
        item2.setName("Krkovička");
        item2.setCode("121");
        item2.setDescription("Chro chro ...");
        item2.setPrice(150);

        itemsList.add(item1);
        itemsList.add(item2);
        return itemsList;
    }
}
