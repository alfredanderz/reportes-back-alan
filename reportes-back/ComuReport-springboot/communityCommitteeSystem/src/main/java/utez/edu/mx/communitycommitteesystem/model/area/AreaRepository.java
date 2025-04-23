package utez.edu.mx.communitycommitteesystem.model.area;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;

import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<AreaBean, Long> {

    //Buscar las areas de los municipios
    List<AreaBean> findByMunicipalityBean(MunicipalityBean municipalityBean);

    Optional<AreaBean> findByUuidAndMunicipalityBean(String uuid, MunicipalityBean municipalityBean);
    Optional<AreaBean> findByUuid(String uuid);
}
