package cz.kucharo2.data.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = RestaurantTable.TABLE_NAME)
@Proxy(lazy=false)
public class RestaurantTable extends DtoEntity {

	public static final String TABLE_NAME = "Restaurant_table";
	public static final String TABLE_ID = "table_id";
	public static final String NAME = "name";
	public static final String SIZE = "size";

	@Id
	@Column(name = TABLE_ID)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

	@Column(name = NAME)
    private String name;

	@Column(name = SIZE)
    private int size;

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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RestaurantTable)) return false;

		RestaurantTable that = (RestaurantTable) o;

		if (size != that.size) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + size;
		return result;
	}

	@Override
	public String toString() {
		return "RestaurantTable{" +
				"id=" + id +
				", name='" + name + '\'' +
				", size=" + size +
				'}';
	}
}
