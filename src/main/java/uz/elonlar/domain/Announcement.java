package uz.elonlar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import uz.elonlar.domain.enumeration.AnnouncementStatus;

/**
 * The Employee entity.
 */
@Schema(description = "The Employee entity.")
@Entity
@Table(name = "announcement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @Column(name = "salary")
    private Long salary;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AnnouncementStatus status;

    @ManyToOne
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties(value = { "region" }, allowSetters = true)
    private District district;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private SubCategory subcategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Announcement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Announcement title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Announcement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return this.email;
    }

    public Announcement email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Announcement phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return this.hireDate;
    }

    public Announcement hireDate(Instant hireDate) {
        this.setHireDate(hireDate);
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return this.salary;
    }

    public Announcement salary(Long salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Announcement longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Announcement latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public AnnouncementStatus getStatus() {
        return this.status;
    }

    public Announcement status(AnnouncementStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AnnouncementStatus status) {
        this.status = status;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Announcement region(Region region) {
        this.setRegion(region);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Announcement district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Announcement category(Category category) {
        this.setCategory(category);
        return this;
    }

    public SubCategory getSubcategory() {
        return this.subcategory;
    }

    public void setSubcategory(SubCategory subCategory) {
        this.subcategory = subCategory;
    }

    public Announcement subcategory(SubCategory subCategory) {
        this.setSubcategory(subCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Announcement)) {
            return false;
        }
        return id != null && id.equals(((Announcement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Announcement{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
