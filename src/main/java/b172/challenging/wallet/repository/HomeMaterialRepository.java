package b172.challenging.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.myhome.domain.HomeMaterial;
import b172.challenging.myhome.domain.MyHome;

public interface HomeMaterialRepository extends JpaRepository<HomeMaterial, Long> {
	List<HomeMaterial> findByMyHome(MyHome myHome);
}
