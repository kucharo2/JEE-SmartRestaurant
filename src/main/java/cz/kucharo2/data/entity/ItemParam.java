package cz.kucharo2.data.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = ItemParam.TABLE_NAME)
@Proxy(lazy = false)
public class ItemParam extends DtoEntity {

    public static final String TABLE_NAME = "Item_param";
    public static final String ITEM_PARAM_ID = "item_param_id";
    public static final String PARAM_VALUE = "param_value";
    public static final String PARAM_NAME = "param_name";

    @Id
    @Column(name = ITEM_PARAM_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = PARAM_NAME)
    private String name;

    @Column(name = PARAM_VALUE)
    private String value;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
