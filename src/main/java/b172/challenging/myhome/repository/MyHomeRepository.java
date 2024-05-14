package b172.challenging.myhome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.myhome.domain.MyHome;

public interface MyHomeRepository extends JpaRepository<MyHome, Long> {
	Optional<MyHome> findByLevel(Long level);
}
