package b172.challenging.wallet.repository;

import b172.challenging.myhome.domain.HomeMaterial;
import b172.challenging.myhome.domain.MyHome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeMaterialRepository extends JpaRepository<HomeMaterial, Long> {
    List<HomeMaterial> findByMyHome(MyHome myHome);
}
