package cz.kucharo2.data.dao;

import cz.kucharo2.data.entity.Reservation;

import java.util.Date;
import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public interface ReservationDao extends AbstractGenericDao<Reservation> {

	/**
	 * Returns list of reservation by specific date
	 *
	 * @param date date
	 * @return list of reservations by specific date
	 *
	 */
	List<Reservation> getReservationByDate(Date date);

	/**
	 * Returns list of reservation by specific date and table
	 *
	 * @param date date
	 * @param tableID table id
	 * @return list of reservation by specific date and table
	 *
	 */
	List<Reservation> getReservationByDateAndTable(Date date, int tableID);

}
