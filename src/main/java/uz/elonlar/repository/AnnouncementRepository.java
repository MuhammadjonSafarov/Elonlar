package uz.elonlar.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.elonlar.domain.Announcement;

/**
 * Spring Data JPA repository for the Announcement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {}
