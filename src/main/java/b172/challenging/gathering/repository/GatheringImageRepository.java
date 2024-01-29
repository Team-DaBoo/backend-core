package b172.challenging.gathering.repository;

import b172.challenging.gathering.domain.GatheringImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatheringImageRepository extends JpaRepository<GatheringImage, Long> {

    List<GatheringImage> findAll();

}
