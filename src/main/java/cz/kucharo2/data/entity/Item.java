package cz.kucharo2.data.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = Item.TABLE_NAME)
@Proxy(lazy = false)
public class Item extends DtoEntity {

    public static final String TABLE_NAME = "Item";
    public static final String ITEM_ID = "item_id";
    public static final String PRICE = "price";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY_ID = "category_id";

    @Id
    @Column(name = ITEM_ID)
    private Integer id;

    @Column(name = PRICE)
    private int price;

    @Column(name = NAME)
    private String name;

    @Column(name = CODE)
    private String code;

    @Column(name = DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = "item")
    private Collection<Review> reviews;

    @ManyToOne
    @JoinColumn(name = CATEGORY_ID)
    private Category category;

    @ManyToMany
    @JoinTable(name = "item_combination",
            joinColumns = @JoinColumn(name = "item_1"),
            inverseJoinColumns = @JoinColumn(name = "item_2"))
    private Collection<Item> itemCombination;

    @ManyToMany
    @JoinTable(name = "item_combination",
            joinColumns = @JoinColumn(name = "item_2"),
            inverseJoinColumns = @JoinColumn(name = "item_1"))
    private Collection<Item> itemCombinationTo;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection<Item> getItemCombination() {
        return itemCombination;
    }

    public void setItemCombination(Collection<Item> itemCombination) {
        this.itemCombination = itemCombination;
    }

    public Collection<Item> getItemCombinationTo() {
        return itemCombinationTo;
    }

    public void setItemCombinationTo(Collection<Item> itemCombinationTo) {
        this.itemCombinationTo = itemCombinationTo;
    }

    public Set<Item> getCombinations() {
        Set<Item> combinations = new HashSet<Item>(getItemCombination());
        for (Item dish : getItemCombinationTo()) {
            combinations.add(dish);
        }
        return combinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item dish = (Item) o;

        if (price != dish.price) return false;
        if (category != null ? !category.equals(dish.category) : dish.category != null) return false;
        if (code != null ? !code.equals(dish.code) : dish.code != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        if (itemCombination != null ? !itemCombination.equals(dish.itemCombination) : dish.itemCombination != null)
            return false;
        if (itemCombinationTo != null ? !itemCombinationTo.equals(dish.itemCombinationTo) : dish.itemCombinationTo != null)
            return false;
        if (id != null ? !id.equals(dish.id) : dish.id != null) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (reviews != null ? !reviews.equals(dish.reviews) : dish.reviews != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + price;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (itemCombination != null ? itemCombination.hashCode() : 0);
        result = 31 * result + (itemCombinationTo != null ? itemCombinationTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", reviews=" + reviews +
                ", category=" + category +
                ", itemCombination=" + itemCombination +
                ", itemCombinationTo=" + itemCombinationTo +
                '}';
    }
}
