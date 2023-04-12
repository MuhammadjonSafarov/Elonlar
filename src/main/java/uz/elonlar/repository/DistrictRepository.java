package uz.elonlar.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.elonlar.domain.District;

/**
 * Spring Data JPA repository for the District entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

}
