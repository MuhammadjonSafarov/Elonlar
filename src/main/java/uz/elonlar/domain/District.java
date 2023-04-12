package uz.elonlar.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A District.
 */
@Entity
@Table(name = "district")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class District implements Serializable {

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

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @ManyToOne
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public District id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUzLat() {
        return this.nameUzLat;
    }

    public District nameUzLat(String nameUzLat) {
        this.setNameUzLat(nameUzLat);
        return this;
    }

    public void setNameUzLat(String nameUzLat) {
        this.nameUzLat = nameUzLat;
    }

    public String getNameUzKr() {
        return this.nameUzKr;
    }

    public District nameUzKr(String nameUzKr) {
        this.setNameUzKr(nameUzKr);
        return this;
    }

    public void setNameUzKr(String nameUzKr) {
        this.nameUzKr = nameUzKr;
    }

    public String getNameRu() {
        return this.nameRu;
    }

    public District nameRu(String nameRu) {
        this.setNameRu(nameRu);
        return this;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public District nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public District longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public District latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public District region(Region region) {
        this.setRegion(region);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return id != null && id.equals(((District) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", nameUzLat='" + getNameUzLat() + "'" +
            ", nameUzKr='" + getNameUzKr() + "'" +
            ", nameRu='" + getNameRu() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}
