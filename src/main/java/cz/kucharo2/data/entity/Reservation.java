package cz.kucharo2.data.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = Reservation.TABLE_NAME)
@Proxy(lazy = false)
public class Reservation extends DtoEntity {

    public static final String TABLE_NAME = "Reservation";
    public static final String RESERVATION_ID = "reservation_id";
    public static final String TABLE_ID = "table_id";
    public static final String TIME_FROM = "time_from";
    public static final String TIME_TO = "time_to";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String DESCRIPTION = "description";
    public static final String MAIL = "mail";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String STORNO = "storno";

    @Id
    @Column(name = RESERVATION_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = TABLE_ID)
    private RestaurantTable table;

    @Column(name = TIME_FROM)
    private Date timeFrom;

    @Column(name = TIME_TO)
    private Date timeTo;

    @Column(name = CUSTOMER_NAME)
    private String customerName;

    @Column(name = DESCRIPTION)
    private String description;

    @Column(name = MAIL)
    private String mail;

    @Column(name = PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = STORNO)
    private boolean storno;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStorno() {
        return storno;
    }

    public void setStorno(boolean storno) {
        this.storno = storno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (storno != that.storno) return false;
        if (!id.equals(that.id)) return false;
        if (!table.equals(that.table)) return false;
        if (!timeFrom.equals(that.timeFrom)) return false;
        if (!timeTo.equals(that.timeTo)) return false;
        if (!customerName.equals(that.customerName)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        return phoneNumber.equals(that.phoneNumber);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + table.hashCode();
        result = 31 * result + timeFrom.hashCode();
        result = 31 * result + timeTo.hashCode();
        result = 31 * result + customerName.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + (storno ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", table=" + table +
                ", timeFrom=" + timeFrom +
                ", timeTo=" + timeTo +
                ", customerName='" + customerName + '\'' +
                ", description='" + description + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", storno=" + storno +
                '}';
    }
}
