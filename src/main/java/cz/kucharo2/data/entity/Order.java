package cz.kucharo2.data.entity;

import cz.kucharo2.data.enums.OrderStatus;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = Order.TABLE_NAME)
@Proxy(lazy = false)
public class Order extends DtoEntity {

    public static final String TABLE_NAME = "O_Order";
    public static final String ORDER_ID = "order_id";
    public static final String DATE = "date";
    public static final String STATUS = "status";
    public static final String TABLE_ID = "table_id";
    public static final String ACCOUNT_ID = "account_id";

    @Id
    @Column(name = ORDER_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = TABLE_ID)
    private RestaurantTable table;

    @Column(name = DATE)
    private Date date = new Date();

    @Column(name = STATUS)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = ACCOUNT_ID)
    private Account account;

    @OneToMany(mappedBy = "order")
    @OrderBy(OrderItem.CREATED + " asc")
    private Collection<OrderItem> orderItems;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (table != null ? !table.equals(order.table) : order.table != null) return false;
        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        if (account != null ? !account.equals(order.account) : order.account != null) return false;
        return orderItems != null ? orderItems.equals(order.orderItems) : order.orderItems == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (table != null ? table.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (orderItems != null ? orderItems.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", table=" + table +
                ", date=" + date +
                ", account=" + account +
                ", orderItems=" + orderItems +
                '}';
    }
}
