package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.RestaurantTableDao;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.service.TableService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link TableService}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class TableServiceImplTest {

    @Mock
    private RestaurantTableDao restaurantTableDao;

    @InjectMocks
    private TableService tableService = new TableServiceImpl();

    private RestaurantTable restaurantTable;

    private static String NAME = "Test";
    private static int ID = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        restaurantTable = new RestaurantTable();
        restaurantTable.setName(NAME);
    }

    @Test
    public void testGetTable() throws Exception {
        when(restaurantTableDao.getById(anyInt())).thenReturn(restaurantTable);

        RestaurantTable testRestaurantTable = tableService.getTable(ID);

        verify(restaurantTableDao).getById(any(Integer.class));

        Assert.assertNotNull(testRestaurantTable);
        Assert.assertEquals(restaurantTable.getName(), testRestaurantTable.getName());
    }

    @Test
    public void testGetAllTables() throws Exception {
        List<RestaurantTable> restaurantTableList = new ArrayList<>();
        restaurantTableList.add(restaurantTable);

        when(restaurantTableDao.getAll()).thenReturn(restaurantTableList);

        List<RestaurantTable> testRestaurantTableList = tableService.getAllTables();

        verify(restaurantTableDao).getAll();

        Assert.assertEquals(restaurantTableList.size(), testRestaurantTableList.size());
        Assert.assertEquals(restaurantTable.getName(), testRestaurantTableList.get(0).getName());
    }

}