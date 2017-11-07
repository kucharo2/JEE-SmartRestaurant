package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.ReservationDao;
import cz.kucharo2.data.entity.Reservation;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class ReservationDaoImpl extends AbstractGenericDaoImpl<Reservation> implements ReservationDao {

    public ReservationDaoImpl() {
        super(Reservation.class);
    }

    @Override
    public List<Reservation> getReservationByDate(Date date) {
        String query = Reservation.TIME_FROM + " > :dayStart and " + Reservation.TIME_TO + " < :dayEnd";

        Map<String, Object> params = new HashMap<>();
        setDateParams(date, params);

        return getByWhereCondition(query, params);
    }

    @Override
    public List<Reservation> getReservationByDateAndTable(Date date, int tableID) {
        String query = Reservation.TIME_FROM + " > :dayStart and " + Reservation.TIME_TO + " < :dayEnd"
                + " and " + Reservation.TABLE_ID + " = :tableId";

        Map<String, Object> params = new HashMap<>();
        params.put("tableId", tableID);
        setDateParams(date, params);

        return getByWhereCondition(query, params);
    }

    private void setDateParams(Date date, Map<String, Object> params) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, month, day, 0, 0);
        Date dayStart = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();

        params.put("dayStart", dayStart);
        params.put("dayEnd", dayEnd);
    }
}
