package cz.kucharo2.data.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Entity
@Table(name = Review.TABLE_NAME)
@Proxy(lazy = false)
public class Review extends DtoEntity {

    public static final String TABLE_NAME = "Review";
    public static final String REVIEW_ID = "review_id";
    public static final String STARS = "stars";
    public static final String TEXT = "text";
    public static final String NICKNAME = "nickname";
    public static final String ITEM_ID = "item_id";

    @Id
    @Column(name = REVIEW_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = STARS)
    private int stars;

    @Column(name = TEXT)
    private String review;

    @Column(name = NICKNAME)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = ITEM_ID)
    private Item item;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;

        Review that = (Review) o;

        if (stars != that.stars) return false;
        if (item != null ? !item.equals(that.item) : that.item != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (review != null ? !review.equals(that.review) : that.review != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + stars;
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", stars=" + stars +
                ", review='" + review + '\'' +
                ", nickname='" + nickname + '\'' +
                ", item=" + item +
                '}';
    }
}
