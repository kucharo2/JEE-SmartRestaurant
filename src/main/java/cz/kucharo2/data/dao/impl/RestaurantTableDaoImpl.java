package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.RestaurantTableDao;
import cz.kucharo2.data.entity.RestaurantTable;

import javax.enterprise.context.ApplicationScoped;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class RestaurantTableDaoImpl extends AbstractGenericDaoImpl<RestaurantTable> implements RestaurantTableDao {

    public RestaurantTableDaoImpl() {
        super(RestaurantTable.class);
    }
}
