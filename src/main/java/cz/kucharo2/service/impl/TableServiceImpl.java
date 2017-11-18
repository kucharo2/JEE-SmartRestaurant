package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.RestaurantTableDao;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.service.TableService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Roman on 12/3/2014.
 */
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class TableServiceImpl implements TableService {

	@Inject
	private RestaurantTableDao tableDao;

	@Override
	public RestaurantTable getTable(int id) {
		return tableDao.getById(id);
	}

	@Override
	public List<RestaurantTable> getAllTables() {
		return tableDao.getAll();
	}
}
