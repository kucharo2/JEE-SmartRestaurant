package cz.kucharo2.service;

import cz.kucharo2.data.entity.RestaurantTable;

import java.util.List;

/**
 * Created by Roman on 12/3/2014.
 */
public interface TableService {
	/**
	 * Return a table in restaurant by id
	 *
	 * @param id table id
	 *
	 * @return table
	 */
	RestaurantTable getTable(int id);

	/**
	 * Returns all table in restaurant
	 */
	List<RestaurantTable> getAllTables();
}
