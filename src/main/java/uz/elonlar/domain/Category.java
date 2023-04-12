package uz.elonlar.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_uz_lat")
    private String nameUzLat;

    @Column(name = "name_uz_kr")
    private String nameUzKr;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "icon")
    private String icon;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUzLat() {
        return this.nameUzLat;
    }

    public Category nameUzLat(String nameUzLat) {
        this.setNameUzLat(nameUzLat);
        return this;
    }

    public void setNameUzLat(String nameUzLat) {
        this.nameUzLat = nameUzLat;
    }

    public String getNameUzKr() {
        return this.nameUzKr;
    }

    public Category nameUzKr(String nameUzKr) {
        this.setNameUzKr(nameUzKr);
        return this;
    }

    public void setNameUzKr(String nameUzKr) {
        this.nameUzKr = nameUzKr;
    }

    public String getNameRu() {
        return this.nameRu;
    }

    public Category nameRu(String nameRu) {
        this.setNameRu(nameRu);
        return this;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Category nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getIcon() {
        return this.icon;
    }

    public Category icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", nameUzLat='" + getNameUzLat() + "'" +
            ", nameUzKr='" + getNameUzKr() + "'" +
            ", nameRu='" + getNameRu() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
