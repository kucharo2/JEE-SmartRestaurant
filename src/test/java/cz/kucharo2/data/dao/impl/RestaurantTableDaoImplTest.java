package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.AbstractGenericDao;
import cz.kucharo2.data.dao.RestaurantTableDao;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.data.enums.CategoryType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

/**
 * Tests for {@link RestaurantTableDao} and {@link AbstractGenericDao}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@RunWith(Arquillian.class)
public class RestaurantTableDaoImplTest {

    @Inject
    private RestaurantTableDao restaurantTableDao;

    private RestaurantTable restaurantTable;

    private int numberOfRestaurantTable;

    private static String NAME = "test";
    private static int TABLE_SIZE = 2;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, RestaurantTableDao.class.getPackage())
                .addPackages(true, RestaurantTable.class.getPackage())
                .addPackages(true, CategoryType.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    /**
     * Create new {@link RestaurantTable} then put the entity into db
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        restaurantTable = new RestaurantTable();

        restaurantTable.setSize(TABLE_SIZE);
        restaurantTable.setName(NAME);

        numberOfRestaurantTable = restaurantTableDao.getAll().size();

        restaurantTableDao.createOrUpdate(restaurantTable);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetElementById() throws Exception{
        int id = restaurantTable.getId();

        RestaurantTable testRestaurantTable = restaurantTableDao.getById(id);

        Assert.assertNotNull(testRestaurantTable);
        Assert.assertEquals(restaurantTable.getId(), testRestaurantTable.getId());
        Assert.assertEquals(restaurantTable.getName(), testRestaurantTable.getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testDeleteElement() throws Exception{
        int id = restaurantTable.getId();
        Assert.assertEquals(restaurantTable, restaurantTableDao.getById(id));

        restaurantTableDao.delete(restaurantTable);

        Assert.assertNull(restaurantTableDao.getById(id));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testGetAllElements() throws Exception{
        List<RestaurantTable> restaurantTableList = restaurantTableDao.getAll();
        Assert.assertEquals(numberOfRestaurantTable + 1, restaurantTableList.size());

        RestaurantTable testRestaurantTable = restaurantTableList.stream().filter(x -> restaurantTable.getId().equals(x.getId()))
                .findAny().orElse(null);

        Assert.assertNotNull(testRestaurantTable);
        Assert.assertEquals(restaurantTable.getId(), testRestaurantTable.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testNumberOfElement() throws Exception{
        Long count = (long) (numberOfRestaurantTable + 1);
        Assert.assertEquals(count, restaurantTableDao.getAllCount(restaurantTable));
    }
}
