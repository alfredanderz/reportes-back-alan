package utez.edu.mx.communitycommitteesystem.model.report;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.communitycommitteesystem.model.area.AreaBean;
import utez.edu.mx.communitycommitteesystem.model.colony.ColonyBean;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.state.StateBean;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportBean, Long> {


    List<ReportBean> findByColonyBean(ColonyBean colony);

    Optional<ReportBean> findByUuid(String uuid);


    List<ReportBean> findByMunicipalityBean(MunicipalityBean municipalityBean);

    List<ReportBean> findByMunicipalityBeanAndStatusBean_Id(MunicipalityBean municipalityBean, Long statusBeanId);

    List<ReportBean> findByColonyBeanAndStatusBean_Id(ColonyBean colonyBean, Long statusBeanId);

    List<ReportBean> findByAreaBeanAndStatusBean_Id(AreaBean areaBean, Long statusBeanId);

    List<ReportBean> findByColonyBeanAndStatusBean_IdAndId(ColonyBean colonyBean, Long statusBeanId, Long id);

    ReportBean findByMunicipalityBeanAndUuid(MunicipalityBean municipalityBean, String uuid);

    ReportBean findByAreaBeanAndUuid(AreaBean areaBean, String uuid);

    List<ReportBean> findByColonyBeanAndStatusBean_IdNotAndStatusBean_IdNot(ColonyBean colony, long status1, long status2);


    List<ReportBean> findByStateBean(StateBean stateBean);

    List<ReportBean> findByAreaBean(AreaBean areaBean);
}

