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
@Table(name = BillItem.TABLE_NAME)
@Proxy(lazy = false)
public class BillItem extends DtoEntity {

    public static final String TABLE_NAME = "Bill_item";
    public static final String ID_COLUMN = "bill_item_id";
    public static final String PAID = "paid";
    public static final String PRICE = "price";
    public static final String BILL_ID = "bill_id";
    public static final String ITEM_ID = "item_id";
    public static final String PARENT_ID = "parent_id";
    public static final String CREATED = "created";

    @Id
    @Column(name = ID_COLUMN)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = BILL_ID)
    @JsonIgnore
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = ITEM_ID)
    private Item item;

    @Column(name = PAID)
    private boolean paid;

    @Column(name = PRICE)
    private int price;

    @ManyToOne
    @JoinColumn(name = PARENT_ID)
    private BillItem parentBillItem;

    @OneToMany(mappedBy = "parentBillItem")
    @JsonIgnore
    private Collection<BillItem> childBillItems;

    @JoinColumn(name = CREATED)
    private Date created;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BillItem getParentBillItem() {
        return parentBillItem;
    }

    public void setParentBillItem(BillItem parentBillItems) {
        this.parentBillItem = parentBillItems;
    }

    public Collection<BillItem> getChildBillItems() {
        return childBillItems;
    }

    public void setChildBillItems(Collection<BillItem> childBillItems) {
        this.childBillItems = childBillItems;
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

        BillItem billItem = (BillItem) o;

        if (paid != billItem.paid) return false;
        if (price != billItem.price) return false;
        if (!id.equals(billItem.id)) return false;
        if (!bill.equals(billItem.bill)) return false;
        return item.equals(billItem.item);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + bill.hashCode();
        result = 31 * result + item.hashCode();
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + price;
        return result;
    }
}
