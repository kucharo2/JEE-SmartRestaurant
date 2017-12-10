package cz.kucharo2.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.kucharo2.data.enums.CategoryType;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Collection;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = Category.TABLE_NAME)
@Proxy(lazy = false)
public class Category extends DtoEntity {

    public static final String TABLE_NAME = "Category";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CODE = "code";
    public static final String PARENT_ID = "parent_id";
    public static final String PRIORITY = "priority";

    @Id
    @Column(name = CATEGORY_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = CODE)
    @Enumerated(EnumType.STRING)
    private CategoryType code;

    @Column(name = CATEGORY_NAME)
    private String name;

    @Column(name = PRIORITY)
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = PARENT_ID)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @OrderBy("priority ASC")
    @JsonIgnore
    private Collection<Category> childCategories;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoryType getCode() {
        return code;
    }

    public void setCode(CategoryType code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Collection<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Collection<Category> childCategories) {
        this.childCategories = childCategories;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setOrder(Integer order) {
        this.priority = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (code != null ? !code.equals(category.code) : category.code != null) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (parentCategory != null ? !parentCategory.equals(category.parentCategory) : category.parentCategory != null)
            return false;
        return childCategories != null ? childCategories.equals(category.childCategories) : category.childCategories == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentCategory != null ? parentCategory.hashCode() : 0);
        result = 31 * result + (childCategories != null ? childCategories.hashCode() : 0);
        return result;
    }

}
