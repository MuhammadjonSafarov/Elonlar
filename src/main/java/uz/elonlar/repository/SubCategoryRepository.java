package uz.elonlar.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.elonlar.domain.SubCategory;

/**
 * Spring Data JPA repository for the SubCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {}
