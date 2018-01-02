package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for {@link ItemDao}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@RunWith(Arquillian.class)
public class ItemDaoImplTest {

    @Inject
    private ItemDao itemDao;

    @Inject
    private CategoryDao categoryDao;

    private static String DISHES_NAME = "Vepřový řízek";
    private static String DISHES_CODE = "VEPROVY_STEAK";
    private static Long DISHES_PRICE = 80L;

    private static String DRINK_NAME = "Plzeň 12° 0,5L";
    private static String DRINK_CODE = "PLZEN_12_VELKA";
    private static Long DRINK_PRICE = 35L;

    private static int ID = 0;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, ItemDao.class.getPackage())
                .addPackages(true, Item.class.getPackage())
                .addPackages(true, CategoryType.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistItemsByCategory() throws Exception {
        Assert.assertEquals(0, itemDao.getItemsByCategory(CategoryType.MAIN_FOOD).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistItemsByCategory() throws Exception {
        Item item = createDishes();

        itemDao.createOrUpdate(item);

        List<Item> itemList = itemDao.getItemsByCategory(CategoryType.MAIN_FOOD);
        Assert.assertFalse(itemList.isEmpty());

        Item testItem = itemList.stream().filter(x -> item.getId().equals(x.getId())).findAny().orElse(null);

        Assert.assertNotNull(testItem);
        Assert.assertEquals(item.getId(), testItem.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistItemsByListCategories() throws Exception {
        List<CategoryType> categoryTypes = new ArrayList<>();
        categoryTypes.add(CategoryType.MAIN_FOOD);
        categoryTypes.add(CategoryType.DRINKS);

        Assert.assertEquals(0, itemDao.getItemsByListCategories(categoryTypes).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistItemsByListCategories() throws Exception {
        Item item = new Item();
        item.setName(DRINK_NAME);
        item.setCode(DRINK_CODE);
        item.setCategory(categoryDao.getCategoryByCode(CategoryType.DRINKS));
        item.setPrice(DRINK_PRICE);

        itemDao.createOrUpdate(item);

        List<CategoryType> categoryTypes = new ArrayList<>();
        categoryTypes.add(CategoryType.NEALKOHOLICKE_NAPOJE);
        categoryTypes.add(CategoryType.PANAKY);
        categoryTypes.add(CategoryType.PIVO);
        categoryTypes.add(CategoryType.DRINKS);

        List<Item> itemList = itemDao.getItemsByListCategories(categoryTypes);
        Assert.assertFalse(itemList.isEmpty());

        Item testItem = itemList.stream().filter(x -> item.getId().equals(x.getId())).findAny().orElse(null);

        Assert.assertNotNull(testItem);
        Assert.assertEquals(item.getId(), testItem.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistItemsByCategoryCount() throws Exception {
        Assert.assertEquals(Long.valueOf(0), itemDao.getItemsByCategoryCount(CategoryType.DRINKS));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistItemsByCategoryCount() throws Exception {
        Assert.assertTrue(0 < itemDao.getItemsByCategoryCount(CategoryType.MASO));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistItem() throws Exception {
        Assert.assertNull(itemDao.getItemsWithCombinations(ID));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistItem() throws Exception {
        Item item = createDishes();
        Set<Item> itemSet = new HashSet<>();
        itemSet.add(item);
        item.setItemCombination(itemSet);
        item.setItemCombinationTo(itemSet);

        itemDao.createOrUpdate(item);

        Item testItem = itemDao.getItemsWithCombinations(item.getId());
        Assert.assertEquals(item.getId(), testItem.getId());
        Assert.assertEquals(item.getPrice(), testItem.getPrice());
        Assert.assertEquals(item.getName(), testItem.getName());
        Assert.assertEquals(item.getCode(), testItem.getCode());

        Assert.assertNotNull(testItem.getCategory());
        Assert.assertNotNull(testItem.getItemCombination());
        Assert.assertNotNull(testItem.getItemCombinationTo());
    }

    private Item createDishes(){
        Item item = new Item();
        item.setPrice(DISHES_PRICE);
        item.setName(DISHES_NAME);
        item.setCode(DISHES_CODE);
        item.setCategory(categoryDao.getCategoryByCode(CategoryType.MAIN_FOOD));

        return item;
    }
}
