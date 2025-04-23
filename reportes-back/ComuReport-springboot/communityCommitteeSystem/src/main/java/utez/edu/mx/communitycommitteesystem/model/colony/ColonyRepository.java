package utez.edu.mx.communitycommitteesystem.model.colony;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;

import java.util.List;
import java.util.Optional;

public interface ColonyRepository extends JpaRepository<ColonyBean, Long> {
    Optional<ColonyBean> findById(Long id);

    Optional<ColonyBean> findByUuid(String uuid);

    //findbyMunicipality
    List<ColonyBean> findByMunicipalityBean(MunicipalityBean municipalityBean);


    Optional<ColonyBean> findByUuidAndMunicipalityBean(String uuid, MunicipalityBean municipalityBean);
}
