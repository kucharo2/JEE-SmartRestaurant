package cz.kucharo2.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public static final String IMAGE = "image";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY_ID = "category_id";
    // item combination table
    public static final String ITEM_COMBINATION_TABLE = "item_combination";
    public static final String ITEM_1_COMBINATION = "item_1";
    public static final String ITEM_2_COMBINATION = "item_2";

    @Id
    @Column(name = ITEM_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = PRICE)
    private Long price;

    @Column(name = NAME)
    private String name;

    @Column(name = CODE)
    private String code;

    @Column(name = IMAGE)
    private String image;

    @Column(name = DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private Collection<Review> reviews;

    @ManyToOne
    @JoinColumn(name = CATEGORY_ID)
    private Category category;

    @ManyToMany
    @JoinTable(name = ITEM_COMBINATION_TABLE,
            joinColumns = @JoinColumn(name = ITEM_1_COMBINATION),
            inverseJoinColumns = @JoinColumn(name = ITEM_2_COMBINATION))
    @JsonIgnore
    private Set<Item> itemCombination;

    @ManyToMany
    @JoinTable(name = ITEM_COMBINATION_TABLE,
            joinColumns = @JoinColumn(name = ITEM_2_COMBINATION),
            inverseJoinColumns = @JoinColumn(name = ITEM_1_COMBINATION))
    @JsonIgnore
    private Set<Item> itemCombinationTo;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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


    public Set<Item> getItemCombination() {
        return itemCombination;
    }

    public void setItemCombination(Set<Item> itemCombination) {
        this.itemCombination = itemCombination;
    }

    public Set<Item> getItemCombinationTo() {
        return itemCombinationTo;
    }

    public void setItemCombinationTo(Set<Item> itemCombinationTo) {
        this.itemCombinationTo = itemCombinationTo;
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
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
