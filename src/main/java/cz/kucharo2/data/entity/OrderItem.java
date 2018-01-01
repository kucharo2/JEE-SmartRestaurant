package cz.kucharo2.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = OrderItem.TABLE_NAME)
@Proxy(lazy = false)
public class OrderItem extends DtoEntity {

    public static final String TABLE_NAME = "Order_item";
    public static final String ID_COLUMN = "order_item_id";
    public static final String PAID = "paid";
    public static final String PRICE = "price";
    public static final String ORDER_ID = "order_id";
    public static final String ITEM_ID = "item_id";
    public static final String PARENT_ID = "parent_id";
    public static final String CREATED = "created";

    @Id
    @Column(name = ID_COLUMN)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = ORDER_ID)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = ITEM_ID)
    private Item item;

    @Column(name = PAID)
    private boolean paid;

    @Column(name = PRICE)
    private Long price;

    @ManyToOne
    @JoinColumn(name = PARENT_ID)
    private OrderItem parentOrderItem;

    @OneToMany(mappedBy = "parentOrderItem", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Collection<OrderItem> childOrderItems;

    @JoinColumn(name = CREATED)
    private Date created;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public OrderItem getParentOrderItem() {
        return parentOrderItem;
    }

    public void setParentOrderItem(OrderItem parentOrderItems) {
        this.parentOrderItem = parentOrderItems;
    }

    public Collection<OrderItem> getChildOrderItems() {
        return childOrderItems;
    }

    public void setChildOrderItems(Collection<OrderItem> childOrderItems) {
        this.childOrderItems = childOrderItems;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (paid != orderItem.paid) return false;
        if (price != orderItem.price) return false;
        if (!id.equals(orderItem.id)) return false;
        if (!order.equals(orderItem.order)) return false;
        return item.equals(orderItem.item);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (parentOrderItem != null ? parentOrderItem.hashCode() : 0);
        result = 31 * result + (childOrderItems != null ? childOrderItems.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
}
