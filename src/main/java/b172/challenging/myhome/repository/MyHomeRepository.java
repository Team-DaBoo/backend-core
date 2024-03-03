package b172.challenging.myhome.repository;

import b172.challenging.myhome.domain.MyHome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyHomeRepository extends JpaRepository<MyHome, Long> {
    Optional<MyHome> findByLevel(Long level);
}
