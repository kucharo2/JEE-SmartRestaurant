package cz.kucharo2.data.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

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

    @Id
    @Column(name = ID_COLUMN)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = BILL_ID)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = ITEM_ID)
    private Item item;

    @Column(name = PAID)
    private boolean paid;

    @Column(name = PRICE)
    private int price;

    @Column(name = PARENT_ID)
    private Integer parent_id;

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

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
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
        if (!parent_id.equals(billItem.parent_id)) return false;
        return item.equals(billItem.item);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + bill.hashCode();
        result = 31 * result + item.hashCode();
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + price;
        if (parent_id != null) {
            result = 31 * result + parent_id.hashCode();
        }
        return result;
    }
}
