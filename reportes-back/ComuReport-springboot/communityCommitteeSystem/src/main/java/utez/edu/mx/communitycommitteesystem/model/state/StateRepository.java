package utez.edu.mx.communitycommitteesystem.model.state;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<StateBean, Long> {

    // agregar el findbyuuid
    Optional<StateBean> findByUuid(String uuid);

    // Si quieres encontrar un estado por el nombre
    List<StateBean> findByNameState(String nameState);

    // Si deseas obtener los municipios asociados a un estado en particular
    List<StateBean> findByMunicipalityBeanList(MunicipalityBean municipalityBean);



}
