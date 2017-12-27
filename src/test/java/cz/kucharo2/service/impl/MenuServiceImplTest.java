package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.service.MenuService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link MenuService}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */

public class MenuServiceImplTest {

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private ItemDao itemDao;

    @InjectMocks
    private MenuService menuService = new MenuServiceImpl();

    private static String NAME_CATEGORY = "main";
    private static String NAME_CHILD_CATEGORY = "child";
    private static String NAME_ITEM = "item";
    private static int ID = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCategoriesByParentCategory() throws Exception {
        Category category = createCategory();
        Category childCategory= new Category();
        childCategory.setName(NAME_CHILD_CATEGORY);
        category.setChildCategories(Arrays.asList(childCategory));

        when(categoryDao.getCategoryByCode(any(CategoryType.class))).thenReturn(category);

        List<Category> testCategories = menuService.getAllCategoriesByParentCategory(CategoryType.DRINKS);

        verify(categoryDao).getCategoryByCode(any(CategoryType.class));

        Assert.assertFalse(testCategories.isEmpty());
        Assert.assertEquals(childCategory.getName(), testCategories.get(0).getName());
    }

    @Test
    public void testGetAllItemsByCategoryCode() throws Exception {
        Category category = createCategory();
        Category childCategory= new Category();
        childCategory.setName(NAME_CHILD_CATEGORY);
        category.setChildCategories(Arrays.asList(childCategory));

        when(categoryDao.getCategoryByCode(any(CategoryType.class))).thenReturn(category);

        Item item = new Item();
        item.setCategory(childCategory);
        item.setName(NAME_ITEM);

        when(itemDao.getItemsByListCategories(anyList())).thenReturn(Arrays.asList(item));

        Map<Category, List<Item>> map = menuService.getAllItemsByCategoryCode(CategoryType.DRINKS);

        verify(categoryDao).getCategoryByCode(any(CategoryType.class));
        verify(itemDao).getItemsByListCategories(anyList());

        Assert.assertFalse(map.isEmpty());

        List<Item> list = map.get(childCategory);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(item, list.get(0));
    }

    @Test
    public void testGetAllItemsByCategoryCodeKeyedByCategoryName() throws Exception {
        Category category = createCategory();
        Category childCategory= new Category();
        childCategory.setName(NAME_CHILD_CATEGORY);
        category.setChildCategories(Arrays.asList(childCategory));

        when(categoryDao.getCategoryByCode(any(CategoryType.class))).thenReturn(category);

        Item item = new Item();
        item.setCategory(childCategory);
        item.setName(NAME_ITEM);

        when(itemDao.getItemsByListCategories(anyList())).thenReturn(Arrays.asList(item));

        Map<String, List<Item>> map = menuService.getAllItemsByCategoryCodeKeyedByCategoryName(CategoryType.DRINKS);

        verify(categoryDao).getCategoryByCode(any(CategoryType.class));
        verify(itemDao).getItemsByListCategories(anyList());

        Assert.assertFalse(map.isEmpty());

        List<Item> list = map.get(childCategory.getName());
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(item, list.get(0));
    }

    @Test
    public void testGetItemsByCategory() throws Exception {
        Item item = new Item();
        item.setName(NAME_ITEM);

        when(itemDao.getItemsByCategory(any(CategoryType.class))).thenReturn(Arrays.asList(item));

        List<Item> testItems = menuService.getItemsByCategory(CategoryType.MAIN_FOOD);

        verify(itemDao).getItemsByCategory(any(CategoryType.class));

        Assert.assertFalse(testItems.isEmpty());
        Assert.assertEquals(item, testItems.get(0));
    }

    @Test
    public void testGetItemsByCombinationToAndCategory() throws Exception {
        Item item = new Item();
        item.setName(NAME_ITEM);

        Category category = new Category();
        category.setCode(CategoryType.PRILOHA);

        Set<Item> itemSet = new HashSet<>();
        Item item2 = new Item();
        item2.setCategory(category);
        itemSet.add(item2);
        item.setItemCombinationTo(itemSet);

        Category category2 = new Category();
        category2.setCode(CategoryType.MASO);

        Set<Item> itemSet2 = new HashSet<>();
        Item item3 = new Item();
        item3.setCategory(category2);
        itemSet.add(item3);
        item.setItemCombination(itemSet2);

        when(itemDao.getItemsWithCombinations(anyInt())).thenReturn(item);

        List<Item> testItems = menuService.getItemsByCombinationToAndCategory(ID, CategoryType.PRILOHA);

        verify(itemDao).getItemsWithCombinations(anyInt());

        Assert.assertFalse(testItems.isEmpty());
        Assert.assertEquals(item2, testItems.get(0));
    }

    @Test
    public void testHetItemById() throws Exception {
        Item item = new Item();
        item.setName(NAME_ITEM);

        when(itemDao.getById(anyInt())).thenReturn(item);

        Item testItem = menuService.getItemById(ID);

        verify(itemDao).getById(anyInt());

        Assert.assertNotNull(testItem);
        Assert.assertEquals(item, testItem);
    }

    private Category createCategory(){
        Category category = new Category();
        category.setCode(CategoryType.DRINKS);
        category.setName(NAME_CATEGORY);

        return category;
    }
}